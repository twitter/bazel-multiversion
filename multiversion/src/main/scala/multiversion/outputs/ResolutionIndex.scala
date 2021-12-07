package multiversion.outputs

import java.util.Locale

import scala.collection.mutable

import coursier.core.ArtifactSource
import coursier.core.Classifier
import coursier.core.Dependency
import coursier.core.Module
import coursier.core.Project
import coursier.core.Publication
import coursier.core.Resolution
import coursier.core.Version
import coursier.util.Artifact
import coursier.version.VersionCompatibility
import coursier.version.VersionInterval
import coursier.version.VersionParse
import multiversion.configs.DependencyConfig
import multiversion.configs.ThirdpartyConfig
import multiversion.configs.VersionConfig
import multiversion.diagnostics.MultidepsEnrichments.XtensionDependency
import multiversion.resolvers.DependencyId
import multiversion.resolvers.ResolvedDependency

final case class ResolutionIndex(
    thirdparty: ThirdpartyConfig,
    resolutions: List[DependencyResolution],
    roots: collection.Map[Dependency, collection.Set[Dependency]],
    resolveSources: Boolean,
) {
  import ResolutionIndex._

  // list of all artifacts including transitive JARs
  val rawArtifacts: List[ResolvedDependency] = for {
    r <- resolutions
    resolutionModule = r.dep.coursierModule(thirdparty.scala)
    DependencyArtifact(d, p, a, sa) <- dependencyArtifactsWithSources(r.res)
    if a.url.endsWith(".jar")
    dependency = ResolutionIndex.actualDependency(d, r.res.projectCache)
    artifact = r.dep.url match {
      case Some(url) if dependency.module == resolutionModule =>
        a.withUrl(url).withChecksumUrls(Map.empty)
      case _ => a
    }
  } yield ResolvedDependency(r.dep, dependency, p, artifact, sa)

  /**
   * Optionally resolve the source JARs.
   */
  private def dependencyArtifactsWithSources(
      res: Resolution
  ): Seq[DependencyArtifact] = {
    // grab source JARs and turn them into an immutable Map, so we can look them up
    val sourceArtifacts =
      if (resolveSources)
        Map(res.dependencyArtifacts(Some(List(Classifier.sources))).map { case (d, p, a) =>
          (d, a)
        }: _*)
      else Map.empty[Dependency, Artifact]
    for {
      (d, p, a) <- res.dependencyArtifacts()
    } yield DependencyArtifact(d, p, a, sourceArtifacts.get(d))
  }

  private case class DependencyArtifact(
      dependency: Dependency,
      classfilePublication: Publication,
      classfileArtifact: Artifact,
      sourcesArtifact: Option[Artifact],
  )

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

  val allDependencies: collection.Map[Module, collection.Set[(Dependency, VersionConfig)]] = {
    val result =
      mutable.LinkedHashMap.empty[Module, mutable.LinkedHashSet[(Dependency, VersionConfig)]]
    rawArtifacts.foreach { case ResolvedDependency(config, d, _, _, _) =>
      val buf = result.getOrElseUpdate(
        d.module,
        mutable.LinkedHashSet.empty
      )
      val extractedVersion = thirdparty.versionExtractorByModule(d.module)(d.version)
      val direct = config.coursierModule(thirdparty.scala) == d.module
      // weaken the transitive dependencies
      val version = VersionConfig(d.version, Version(extractedVersion), config.force && direct)
      buf += d -> version
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
      (dep, _) <- deps
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
      resolutions: List[DependencyResolution],
      resolveSources: Boolean,
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
      roots,
      resolveSources,
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
      verForces: Set[VersionConfig],
      compat: VersionCompatibility
  ): Set[VersionConfig] = {
    def hasOverride(v: Version): Boolean = {
      val lower = v.repr.toLowerCase(Locale.ENGLISH)
      overrideTags.exists(t => lower.contains(t)) && !lower.contains("shaded")
    }
    def lessThan(v1: Version, v2: Version): Boolean =
      (!hasOverride(v1) && hasOverride(v2)) || (v1 < v2 && hasOverride(v1) == hasOverride(v2))
    // The "winners" are the highest or forced selected versions
    val winners = mutable.Set.empty[VersionConfig]
    verForces.foreach { case challenger @ VersionConfig(_, version, force) =>
      val isCompatible = winners.exists { case w @ VersionConfig(_, wversion, wforce) =>
        if (isCompat(version.repr, wversion.repr, compat)) {
          if (
            (lessThan(wversion, version) && force == wforce)
            || (force && !wforce)
          ) {
            winners.remove(w)
            winners.add(challenger)
          }
          true
        } else {
          false
        }
      }
      if (!isCompatible) {
        winners.add(challenger)
      }
    }
    winners.toSet
  }

  def reconcileVersions(
      deps: collection.Set[(Dependency, VersionConfig)],
      compat: VersionCompatibility
  ): Map[Dependency, String] = {
    val winners = resolveVersions(
      deps.map(_._2).toSet,
      compat
    )
    val result = (for {
      (dep, config) <- deps
      VersionConfig(original, winner, _) <- winners
      if (config.original != original) && isCompat(config.extracted.repr, winner.repr, compat)
    } yield dep -> original).toMap
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
