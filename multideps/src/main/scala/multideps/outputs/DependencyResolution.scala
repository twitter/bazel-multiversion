package multideps.outputs

import multideps.configs.DependencyConfig

import coursier.core.Resolution

final case class DependencyResolution(
    dep: DependencyConfig,
    res: Resolution
)
