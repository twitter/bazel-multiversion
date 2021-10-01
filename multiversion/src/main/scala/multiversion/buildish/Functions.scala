package multiversion.buildish

import scala.collection.mutable.ListBuffer

import coursier.version.VersionCompatibility
import moped.json.JsonString
import multiversion.configs.DependencyConfig
import multiversion.configs.ModuleConfig
import multiversion.configs.ScalaLanguagesConfig
import multiversion.configs.VersionsConfig
import net.starlark.java.annot.Param
import net.starlark.java.annot.StarlarkMethod
import net.starlark.java.eval.StarlarkList

class Functions(defs: ListBuffer[JarLibraryDef], scalaConfigs: ListBuffer[VersionsConfig]) {
  // we need to pass the Starlark literal for the default value,
  // so it's "\"\"", not "".
  private final val empty_str = "\"\""

  @StarlarkMethod(
    name = "jar_library",
    parameters = Array(
      new Param(name = "name", named = true),
      new Param(name = "deps", named = true, defaultValue = "[]"),
      new Param(name = "dependencies", named = true, defaultValue = "[]"),
      new Param(name = "jars", named = true, defaultValue = "[]"),
      new Param(name = "version_scheme", named = true, defaultValue = empty_str),
      new Param(name = "version_pattern", named = true, defaultValue = empty_str),
    ),
    doc = "Defines a jar_library target."
  )
  def jarLibrary(
      name: String,
      deps: StarlarkList[String],
      dependencies: StarlarkList[String],
      jars: StarlarkList[DependencyConfig],
      version_scheme: String,
      version_pattern: String,
  ): Unit =
    defs += JarLibraryDef(
      name = name,
      deps = deps.toArray.toList.asInstanceOf[List[String]] ++ dependencies.toArray.toList
        .asInstanceOf[List[String]],
      jars = jars.toArray.toList.asInstanceOf[List[DependencyConfig]],
      version_scheme = if (version_scheme == "") None else VersionCompatibility(version_scheme),
      version_pattern = if (version_pattern == "") None else Some(version_pattern),
      targets = Nil,
    )

  // https://github.com/pantsbuild/pants/blob/1.25.x-twtr/src/python/pants/java/jar/jar_dependency.py

  @StarlarkMethod(
    name = "jar",
    parameters = Array(
      new Param(name = "org", named = true),
      new Param(name = "name", named = true),
      new Param(name = "rev", named = true),
      new Param(name = "force", named = true, defaultValue = "True"),
      new Param(name = "classifier", named = true, defaultValue = empty_str),
      new Param(name = "intransitive", named = true, defaultValue = "False"),
      new Param(name = "excludes", named = true, defaultValue = "[]"),
    ),
    doc = "A pre-built Maven repository dependency."
  )
  def jar(
      org: String,
      name: String,
      rev: String,
      force: Boolean,
      classifier: String,
      intransitive: Boolean,
      excludes: StarlarkList[ModuleConfig],
  ): DependencyConfig =
    DependencyConfig(
      organization = JsonString(org),
      name = name,
      version = rev,
      classifier = if (classifier == "") None else Some(classifier),
      force = force,
      transitive = !intransitive,
      exclusions = excludes.toArray.toSet.asInstanceOf[Set[ModuleConfig]],
    )

  @StarlarkMethod(
    name = "scala_jar",
    parameters = Array(
      new Param(name = "org", named = true),
      new Param(name = "name", named = true),
      new Param(name = "rev", named = true),
      new Param(name = "force", named = true, defaultValue = "True"),
      new Param(name = "classifier", named = true, defaultValue = empty_str),
      new Param(name = "intransitive", named = true, defaultValue = "False"),
      new Param(name = "excludes", named = true, defaultValue = "[]"),
    ),
    doc = "A pre-built Maven repository dependency."
  )
  def scala_jar(
      org: String,
      name: String,
      rev: String,
      force: Boolean,
      classifier: String,
      intransitive: Boolean,
      excludes: StarlarkList[ModuleConfig],
  ): DependencyConfig =
    jar(org, name, rev, force, classifier, intransitive, excludes)
      .copy(lang = ScalaLanguagesConfig)

  @StarlarkMethod(
    name = "multiversion_config",
    parameters = Array(
      new Param(name = "scala_versions", named = true, defaultValue = "[]"),
    ),
    doc = "Configuration for the 3rdparty resolution."
  )
  def multiversion_config(
      scala_versions: StarlarkList[String],
  ): Unit = {
    val sv = scala_versions.toArray.toList.asInstanceOf[List[String]]
    if (sv.size != 1) {
      sys.error("Only 1 Scala version is currently supported")
    }
    scalaConfigs += VersionsConfig(sv.head)
  }

  // https://github.com/pantsbuild/pants/blob/1.25.x-twtr/src/python/pants/java/jar/exclude.py

  @StarlarkMethod(
    name = "exclude",
    parameters = Array(
      new Param(name = "org", named = true),
      new Param(name = "name", named = true, defaultValue = empty_str),
    ),
    doc = "Represents a dependency exclude pattern to filter transitive dependencies against."
  )
  def exclude(
      org: String,
      name: String
  ): ModuleConfig = ModuleConfig(organization = org, moduleName = name)
}
