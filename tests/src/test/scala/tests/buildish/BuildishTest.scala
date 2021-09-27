package tests.buildish

import java.nio.file.Paths

import coursier.version.VersionCompatibility
import moped.json.JsonString
import multiversion.buildish.Buildish
import multiversion.buildish.JarLibraryDef
import multiversion.configs.DependencyConfig
import multiversion.configs.ModuleConfig
import tests.BaseSuite

class BuildishTest extends BaseSuite {
  test("Define jar_library") {
    val (defs, scalaConfigs) = Buildish.eval("""
FOO = "foo"

jar_library(
    name = FOO,
)

jar_library(
    name = "bar",
)
""")
    assert(defs.size == 2)
    assert(defs(0) == JarLibraryDef("foo"), s"${defs(0)}")
    assert(defs(1) == JarLibraryDef("bar"), s"${defs(1)}")
  }

  test("Define jar_library with jar(...)") {
    val (defs, scalaConfigs) = Buildish.eval("""
FOO = "foo"

jar_library(
    name = FOO,
    jars = [
      jar(org = "com.example", name = "foo", rev = "1.0.0"),  
    ],
    version_scheme = "early-semver",
)
""")
    assert(defs.size == 1)
    val expected =
      JarLibraryDef(
        name = "foo",
        jars = List(
          DependencyConfig(
            organization = JsonString("com.example"),
            name = "foo",
            version = "1.0.0",
            classifier = None,
            force = true,
          )
        ),
        version_scheme = Some(VersionCompatibility.EarlySemVer),
      )

    assert(defs(0) == expected, s"${defs(0)}")
  }

  test("Define jar_library with jar(...) with exclude(...)") {
    val (defs, scalaConfigs) = Buildish.eval("""
EXCLUDES = [
    exclude(
      org = "com.example",
      name = "core",
    ),
]

jar_library(
    name = "foo",
    jars = [
      jar(
        org = "com.example",
        name = "foo",
        rev = "1.0.0",
        excludes = EXCLUDES,
      ),  
    ],
)
""")
    assert(defs.size == 1)
    val expected =
      JarLibraryDef(
        name = "foo",
        jars = List(
          DependencyConfig(
            organization = JsonString("com.example"),
            name = "foo",
            version = "1.0.0",
            classifier = None,
            force = true,
            exclusions = Set(ModuleConfig("com.example", "core"))
          )
        ),
      )
    assert(defs(0) == expected, s"${defs(0)}")
  }

  test("Recursive") {
    val dir = Paths.get("multiversion-example/3rdparty")
    val relativeTo = Paths.get("multiversion-example")
    val config = Buildish.evalRecursive(dir, relativeTo)
    assert(config.dependencies.size == 7, s"actual: ${config.dependencies.size}")
    val def0 = config.dependencies(0)
    assert(def0.targets == List("3rdparty/jvm/com/google/guava/guava"), s"$def0")

    val def1 = config.dependencies(1)
    assert(def1.targets == List("3rdparty/jvm/com/google/guava/guava-24"), s"$def1")

  }
}
