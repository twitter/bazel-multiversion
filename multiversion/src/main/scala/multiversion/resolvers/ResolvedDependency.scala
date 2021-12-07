package multiversion.resolvers

import coursier.core.Dependency
import coursier.core.Publication
import coursier.util.Artifact
import multiversion.configs.DependencyConfig

final case class ResolvedDependency(
    config: DependencyConfig,
    dependency: Dependency,
    publication: Publication,
    artifact: Artifact,
    sourcesArtifact: Option[Artifact],
) {
  override def toString: String =
    s"""ResolvedDependency(
       |  config = $config,
       |  dependncy = $dependency,
       |  publication = $publication,
       |  artifact = $artifact,
       |  sourcesArtifact = $sourcesArtifact,
       |)""".stripMargin
}
