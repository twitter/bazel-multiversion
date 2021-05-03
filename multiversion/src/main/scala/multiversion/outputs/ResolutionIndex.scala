package multiversion.outputs

import java.util.Locale

import scala.collection.mutable

import coursier.core.ArtifactSource
import coursier.core.Dependency
import coursier.core.Module
import coursier.core.Project
import coursier.core.Resolution
import coursier.core.Version
import coursier.version.VersionCompatibility
import coursier.version.VersionInterval
import coursier.version.VersionParse
import multiversion.configs.DependencyConfig
import multiversion.configs.ThirdpartyConfig
import multiversion.diagnostics.MultidepsEnrichments.XtensionDependency
import multiversion.resolvers.DependencyId
import multiversion.resolvers.ResolvedDependency

final case class ResolutionIndex(
    thirdparty: ThirdpartyConfig,
    resolutions: List[DependencyResolution],
    roots: collection.Map[Dependency, collection.Set[Dependency]]
) {
  import ResolutionIndex._

  // list of all artifacts including transitive JARs
  val rawArtifacts: List[ResolvedDependency] = for {
    r <- resolutions
    (d, p, a) <- r.res.dependencyArtifacts() if a.url.endsWith(".jar")
    dependency = ResolutionIndex.actualDependency(d, r.res.projectCache)
  } yield ResolvedDependency(r.dep, dependency, p, a)

  val resolvedArtifacts: List[ResolvedDependency] = (rawArtifacts
    .groupBy(_.dependency.bazelLabel)
    .map {
      case (_, List(rd)) => rd
      case (lbl, rds0)   =>
        // when multiple resolutions are found for this artifact,
        // prioritize the direct resolution that would contain the dependencies
        val rds1 = rds0.filter { p =>
          p.config.toCoursierDependency(thirdparty.scala).module == p.dependency.module &&
          p.config.classifier ==
            (if (p.publication.classifier.isEmpty) None
             else Some(p.publication.classifier.value))
        }
        rds1.headOption.getOrElse(rds0.head)
    })
    .toList

  def unevictedArtifacts: List[ResolvedDependency] =
    resolvedArtifacts.filter(r => !reconciledVersions.contains(r.dependency.withoutConfig))

  lazy val dependencies: Map[DependencyId, Seq[Dependency]] = {
    val isVisited = mutable.Set.empty[DependencyId]
    val res = for {
      r <- resolutions
      transitive0 = r.res.dependencyArtifacts().map(_._1).distinct.toSeq
      transitive = transitive0.map { actualDependency(_, r.res.projectCache) }
      depId = r.dep.id
      if !isVisited(depId)
    } yield {
      isVisited += depId
      depId -> transitive
    }
    res.toMap
  }

  val allDependencies: collection.Map[Module, collection.Set[(Dependency, Boolean)]] = {
    val result =
      mutable.LinkedHashMap.empty[Module, mutable.LinkedHashSet[(Dependency, Boolean)]]
    rawArtifacts.foreach {
      case ResolvedDependency(config, d, _, _) =>
        val buf = result.getOrElseUpdate(
          d.module,
          mutable.LinkedHashSet.empty
        )
        buf += ((d, config.force))
    }
    result
  }

  def reconciledDependency(dep: Dependency): Dependency =
    dep.withVersion(reconciledVersion(dep))
  def reconciledVersion(dep: Dependency): String =
    reconciledVersions.getOrElse(dep.withoutConfig, dep.version)
  def evictionPairs: Seq[(Dependency, String)] =
    resolvedArtifacts.collect {
      case r if reconciledVersions.contains(r.dependency.withoutConfig) =>
        (r.dependency, reconciledVersion(r.dependency.withoutConfig))
    }

  def configsOf(dep: Dependency): collection.Set[DependencyConfig] =
    owningConfigs.getOrElse(dep.bazelLabel, Set.empty)

  // the map between evicted dependencies and their resolved versions
  private lazy val reconciledVersions: Map[Dependency, String] = {
    for {
      (module, deps) <- allDependencies
      if deps.size > 1
      compat =
        thirdparty.depsByModule
          .getOrElse(module, Nil)
          .flatMap(_.versionScheme)
          .headOption
          .getOrElse {
            /*
            val m = module.name.value
            if (
              m.endsWith("_2.11") || m
                .endsWith("_2.12") || m.endsWith("_2.13") || m.endsWith("_3")
            )
              VersionCompatibility.PackVer
            else
             */
            VersionCompatibility.EarlySemVer
          }
      versions = reconcileVersions(deps, compat)
      (dep, force) <- deps
      reconciledVersion <- versions.get(dep)
      if dep.version != reconciledVersion
    } yield dep.withoutConfig -> reconciledVersion
  }.toMap

  private lazy val owningConfigs: collection.Map[String, collection.Set[DependencyConfig]] = {
    val res = mutable.LinkedHashMap.empty[String, mutable.Set[DependencyConfig]]
    resolutions.foreach { r =>
      val dep = r.dep.toCoursierDependency(thirdparty.scala)
      val reconciled = reconciledDependency(dep)
      val buffer = res.getOrElseUpdate(reconciled.bazelLabel, mutable.LinkedHashSet.empty)
      buffer += r.dep
    }
    res
  }

}

object ResolutionIndex {
  def fromResolutions(
      thirdparty: ThirdpartyConfig,
      resolutions: List[DependencyResolution]
  ): ResolutionIndex = {
    val roots =
      mutable.LinkedHashMap.empty[Dependency, mutable.LinkedHashSet[Dependency]]
    for {
      resolution <- resolutions
      (d, _, _) <- resolution.res.dependencyArtifacts()
      dependency = actualDependency(d, resolution.res.projectCache)
    } {
      val rootsBuffer =
        roots.getOrElseUpdate(dependency, mutable.LinkedHashSet.empty)
      rootsBuffer += resolution.dep.toCoursierDependency(thirdparty.scala)
    }
    ResolutionIndex(
      thirdparty,
      resolutions,
      roots
    )
  }

  type ProjectCache = Map[Resolution.ModuleVersion, (ArtifactSource, Project)]

  def actualDependency(d0: Dependency, projectCache: ProjectCache): Dependency =
    projectCache.get(d0.moduleVersion) match {
      case Some((_, p)) => d0.withVersion(p.actualVersion)
      case _            => d0
    }

  private val overrideTags = Set("-tw")
  def resolveVersions(
      verForces: Set[(String, Boolean)],
      compat: VersionCompatibility
  ): Set[String] = {
    def isUnstable(v: Version): Boolean = {
      val s = v.repr
      s.contains("-M") || s.contains("-alpha") || s.contains("-beta")
    }
    def hasOverride(v: Version): Boolean = {
      val lower = v.repr.toLowerCase(Locale.ENGLISH)
      overrideTags.exists(t => lower.contains(t)) && !lower.contains("shaded")
    }
    def lessThan(v1: Version, v2: Version): Boolean =
      (!hasOverride(v1) && hasOverride(v2)) || (v1 < v2 && hasOverride(v1) == hasOverride(v2))
    // The "winners" are the highest selected versions
    val winners = mutable.Set.empty[(Version, Boolean)]
    verForces.foreach {
      case (v, force) =>
        val version = Version(v)
        val isCompatible = winners.exists {
          case (winner, wforce) =>
            if (isCompat(version.repr, winner.repr, compat)) {
              if (
                (
                  (lessThan(winner, version) && force == wforce)
                  || (force && !wforce)
                )
                && !isUnstable(version)
              ) {
                winners.remove((winner, wforce))
                winners.add(version -> force)
              }
              true
            } else {
              false
            }
        }
        if (!isCompatible) {
          winners.add(version -> force)
        }
    }
    winners.map(_._1.repr).toSet
  }

  def reconcileVersions(
      deps: collection.Set[(Dependency, Boolean)],
      compat: VersionCompatibility
  ): Map[Dependency, String] = {
    val winners = resolveVersions(
      deps.map {
        case (d, fc) =>
          (d.version, fc)
      }.toSet,
      compat
    )
    val result = (for {
      (dep, force) <- deps
      winner <- winners
      if (dep.version != winner) && isCompat(dep.version, winner, compat)
    } yield dep -> winner).toMap
    result
  }

  def isCompat(version1: String, version2: String, compat: VersionCompatibility): Boolean = {
    val min1 = minimumVersion(version1, compat)
    val min2 = minimumVersion(version2, compat)
    (min1 == min2) || (min1 + ".0" == min2) || (min1 == min2 + ".0") || (min1 + ".0.0" == min2) || (min1 == min2 + ".0.0")
  }

  def minimumVersion(v0: String, compat: VersionCompatibility): String = {
    var v = v0
    overrideTags.foreach { t =>
      val idx = v.indexOf(t)
      if (idx >= 1) {
        v = v.slice(0, idx)
      }
    }
    val c = VersionParse.versionConstraint(v)
    if (c.interval != VersionInterval.zero && c.interval.from.isDefined)
      compat.minimumCompatibleVersion(c.interval.from.get.repr)
    else compat.minimumCompatibleVersion(v)
  }
}
