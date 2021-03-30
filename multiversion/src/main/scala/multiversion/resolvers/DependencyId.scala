package multiversion.resolvers

import multiversion.configs.ModuleConfig

final case class DependencyId(
    organization: String,
    name: String,
    version: String,
    classifier: Option[String],
    dependencies: List[String],
    excludes: List[String]
)

object DependencyId {
  def apply(
      organization: String,
      name: String,
      version: String,
      classifier: Option[String],
      dependencies: List[String],
      excludes: Set[ModuleConfig]
  ): DependencyId = {
    val sortedDependencies = dependencies.distinct.sorted
    val sortedExcludes = excludes.toList.map(_.repr).distinct.sorted
    new DependencyId(organization, name, version, classifier, sortedDependencies, sortedExcludes)
  }
}
