package multiversion.buildish

import coursier.version.VersionCompatibility
import multiversion.configs.DependencyConfig

/**
 * Represents jar_library(...) in BUILDish DSL.
 * https://github.com/pantsbuild/pants/blob/1.25.x-twtr/src/python/pants/backend/jvm/targets/jar_library.py
 */
case class JarLibraryDef(
    name: String,
    deps: List[String] = Nil,
    jars: List[DependencyConfig] = Nil,
    version_scheme: Option[VersionCompatibility] = None,
    version_pattern: Option[String] = None,
    targets: List[String] = Nil,
)

object JarLibraryDef {
  def apply(name: String): JarLibraryDef =
    JarLibraryDef(name, Nil, Nil, None, None, Nil)
}
