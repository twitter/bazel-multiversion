package multideps.outputs

import scala.collection.mutable

import multideps.configs.ThirdpartyConfig
import multideps.diagnostics.MultidepsEnrichments.XtensionDependency
import multideps.resolvers.DependencyId
import multideps.resolvers.ResolvedDependency

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.Version
import coursier.version.VersionCompatibility

final case class ResolutionIndex(
    thirdparty: ThirdpartyConfig,
    resolutions: List[DependencyResolution],
    roots: collection.Map[Dependency, collection.Set[Dependency]]
) {
  // list of all artifacts including transitive JARs
  val rawArtifacts: List[ResolvedDependency] = for {
    r <- resolutions
    (d, p, a) <- r.res.dependencyArtifacts()
  } yield ResolvedDependency(r.dep, d, p, a)

  val resolvedArtifacts: List[ResolvedDependency] = (rawArtifacts
    .groupBy(_.dependency.bazelLabel)
    .collect {
      case (_, List(rd)) => rd
      case (_, rds) =>
        // when multiple resolutions found for this artifact,
        // prioritize the direct resolution that would contain the dependencies
        (rds
          .find { p =>
            p.config.toCoursierDependency.module == p.dependency.module
          })
          .getOrElse(rds.head)
    })
    .toList

  lazy val dependencies: Map[DependencyId, Seq[Dependency]] = {
    val isVisited = mutable.Set.empty[String]
    val res = for {
      r <- resolutions
      transitive = r.res.dependencyArtifacts().map(_._1).distinct.toSeq
      dep <- r.res.rootDependencies
      if !isVisited(dep.repr)
    } yield {
      isVisited += dep.repr
      r.dep.toId -> transitive
    }
    res.toMap
  }
  val artifacts: collection.Map[Module, collection.Set[Dependency]] = {
    val result =
      mutable.LinkedHashMap.empty[Module, mutable.LinkedHashSet[Dependency]]
    rawArtifacts.foreach {
      case ResolvedDependency(_, d, _, _) =>
        val buf = result.getOrElseUpdate(
          d.module,
          mutable.LinkedHashSet.empty
        )
        buf += d
    }
    result
  }

  def reconciledDependency(dep: Dependency): Dependency =
    dep.withVersion(reconciledVersion(dep))
  def reconciledVersion(dep: Dependency): String =
    reconciledVersions.getOrElse(dep, dep.version)

  private lazy val reconciledVersions: Map[Dependency, String] = {
    for {
      (module, deps) <- artifacts
      if deps.size > 1
      compat =
        thirdparty.depsByModule
          .getOrElse(module, Nil)
          .headOption
          .flatMap(_.versionScheme)
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
      dep <- deps
      reconciledVersion <- versions.get(dep)
      if dep.version != reconciledVersion
    } yield dep -> reconciledVersion
  }.toMap

  private def reconcileVersions(
      deps: collection.Set[Dependency],
      compat: VersionCompatibility
  ): Map[Dependency, String] = {
    // The "winners" are the highest selected versions
    val winners = mutable.Set.empty[Version]
    val versions = deps.map(d => Version(d.version))
    versions.foreach { version =>
      val isCompatible = winners.exists { winner =>
        if (compat.isCompatible(version.repr, winner.repr)) {
          if (winner < version) {
            winners.remove(winner)
            winners.add(version)
          }
          true
        } else {
          false
        }
      }
      if (!isCompatible) {
        winners.add(version)
      }
    }
    (for {
      dep <- deps
      winner <- winners
      if dep.version != winner.repr &&
        compat.isCompatible(dep.version, winner.repr)
    } yield dep -> winner.repr).toMap
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
      (dependency, publication, artifact) <-
        resolution.res.dependencyArtifacts()
    } {
      val rootsBuffer =
        roots.getOrElseUpdate(dependency, mutable.LinkedHashSet.empty)
      rootsBuffer ++= resolution.res.rootDependencies
    }
    ResolutionIndex(
      thirdparty,
      resolutions,
      roots
    )
  }
}
