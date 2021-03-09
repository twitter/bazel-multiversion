package multiversion.configs

import coursier.core.Dependency
import coursier.core.Module

case class ResolutionConfig(
    dependency: DependencyConfig,
    coursierDep: Dependency,
    exclusions: List[Module],
    additions: List[DependencyConfig]
)
