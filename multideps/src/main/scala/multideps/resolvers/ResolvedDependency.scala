package multideps.resolvers

import coursier.core.Dependency
import coursier.core.Publication
import coursier.util.Artifact
import multideps.configs.DependencyConfig

final case class ResolvedDependency(
    config: DependencyConfig,
    dependency: Dependency,
    publication: Publication,
    artifact: Artifact
    // reconciledDependency: Dependency
) {
  // def isCanonical = dependency == reconciledDependency
}
