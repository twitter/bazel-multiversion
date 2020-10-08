package multideps.outputs

import scala.collection.mutable

import multideps.configs.ThirdpartyConfig

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.Resolution

final case class ResolutionIndex(
    thirdparty: ThirdpartyConfig,
    resolutions: List[Resolution],
    artifacts: collection.Map[Module, collection.Set[Dependency]],
    roots: collection.Map[Dependency, collection.Set[Dependency]]
)

object ResolutionIndex {
  def fromResolutions(
      thirdparty: ThirdpartyConfig,
      resolutions: List[Resolution]
  ): ResolutionIndex = {
    val artifacts =
      mutable.LinkedHashMap.empty[Module, mutable.LinkedHashSet[Dependency]]
    val roots =
      mutable.LinkedHashMap.empty[Dependency, mutable.LinkedHashSet[Dependency]]
    for {
      resolution <- resolutions
      (dependency, publication, artifact) <- resolution.dependencyArtifacts()
    } {
      val artifactsBuffer = artifacts.getOrElseUpdate(
        dependency.module,
        mutable.LinkedHashSet.empty
      )
      artifactsBuffer += dependency
      val rootsBuffer =
        roots.getOrElseUpdate(dependency, mutable.LinkedHashSet.empty)
      rootsBuffer ++= resolution.rootDependencies
    }
    ResolutionIndex(
      thirdparty,
      resolutions,
      artifacts,
      roots
    )
  }
}
