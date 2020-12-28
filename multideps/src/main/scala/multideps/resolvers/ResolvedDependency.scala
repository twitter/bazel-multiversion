package multideps.resolvers

import multideps.configs.DependencyConfig

import coursier.core.Dependency
import coursier.core.Publication
import coursier.util.Artifact

final case class ResolvedDependency(
    config: DependencyConfig,
    dependency: Dependency,
    publication: Publication,
    artifact: Artifact
) {
  override def toString: String =
    s"""ResolvedDependency(
       |  config = $config,
       |  dependncy = $dependency,
       |  publication = $publication,
       |  artifact = $artifact
       |)""".stripMargin
}
