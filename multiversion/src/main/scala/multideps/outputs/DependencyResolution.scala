package multideps.outputs

import multideps.configs.DependencyConfig

import coursier.core.Resolution

final case class DependencyResolution(
    dep: DependencyConfig,
    res: Resolution
) {
  override def toString(): String =
    (
      s"""DependencyResolution(
         |  dep = $dep,
         |  res = Resolution(
         |    rootDependencies = ${res.rootDependencies
        .map(d => d.module.repr + ":" + d.version)
        .mkString(", ")}
         |    dependencySet = ${res.dependencySet.set
        .map(d => d.module.repr + ":" + d.version)
        .mkString(", ")}""".stripMargin
        + s""
        + """
           |  )
           |)""".stripMargin
    )
}
