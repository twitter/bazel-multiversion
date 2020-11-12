package multideps.outputs

import scala.collection.mutable

import multideps.resolvers.DependencyId
import multideps.configs.ThirdpartyConfig
import multideps.diagnostics.MultidepsEnrichments.XtensionDependency
import multideps.diagnostics.MultidepsEnrichments.XtensionList
import multideps.resolvers.ResolvedDependency

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.Version
import coursier.version.VersionCompatibility

final case class ResolutionIndex(
    thirdparty: ThirdpartyConfig,
    resolutions: List[DependencyResolution],
    artifacts: collection.Map[Module, collection.Set[Dependency]],
    roots: collection.Map[Dependency, collection.Set[Dependency]]
) {
  private lazy val reconciledVersions: Map[Dependency, String] = {
    for {
      (module, deps) <- artifacts
      if deps.size > 1
      compat =
        thirdparty.depsByModule
          .getOrElse(module, Nil)
          .headOption
          .flatMap(_.versionScheme)
          .getOrElse(VersionCompatibility.Strict)
      versions = reconcileVersions(deps, compat)
      dep <- deps
      reconciledVersion <- versions.get(dep)
      if dep.version != reconciledVersion
    } yield dep -> reconciledVersion
  }.toMap
  lazy val dependencies: Map[DependencyId, Seq[Dependency]] = {
    (for {
      r <- resolutions
      dep <- r.res.dependencies
    } yield {
      val transitive = r.res
        .dependenciesOf(
          dep,
          withRetainedVersions = true,
          withFallbackConfig = true
        )
        .map(reconciledDependency)
      dep.toId -> transitive
    }).toMap
  }
  def reconciledDependency(dep: Dependency): Dependency =
    dep.withVersion(reconciledVersion(dep))
  def reconciledVersion(dep: Dependency): String =
    reconciledVersions.getOrElse(dep, dep.version)
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
    val artifacts =
      mutable.LinkedHashMap.empty[Module, mutable.LinkedHashSet[Dependency]]
    val roots =
      mutable.LinkedHashMap.empty[Dependency, mutable.LinkedHashSet[Dependency]]
    for {
      resolution <- resolutions
      (dependency, publication, artifact) <-
        resolution.res.dependencyArtifacts()
    } {
      val artifactsBuffer = artifacts.getOrElseUpdate(
        dependency.module,
        mutable.LinkedHashSet.empty
      )
      artifactsBuffer += dependency
      val rootsBuffer =
        roots.getOrElseUpdate(dependency, mutable.LinkedHashSet.empty)
      rootsBuffer ++= resolution.res.rootDependencies
    }
    ResolutionIndex(
      thirdparty,
      resolutions,
      artifacts,
      roots
    )
  }
}
