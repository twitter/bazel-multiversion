package multiversion.resolvers

import coursier.core.Dependency
import multiversion.configs.ModuleConfig

final case class ContextualDependency(
    dependency: Dependency,
    exclusions: Set[ModuleConfig],
    dependencies: List[String]
)
