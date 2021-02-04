package tests.commands

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import coursier.version.VersionCompatibility
import multiversion.configs.ModuleConfig
import multiversion.outputs.ResolutionIndex
import multiversion.resolvers.ContextualDependency

class ResolutionTest extends tests.BaseSuite {
  test("reconciliation") {
    val vs = ResolutionIndex.reconcileVersions(
      Set(
        jodaTime("2.10.4"),
        jodaTime("2.8.1"),
        jodaTime("2.9.9"),
        jodaTime("2.8.2"),
        jodaTime("[2.0,)"),
        jodaTime("2.1"),
        jodaTime("1.6.2"),
        jodaTime("2.9.4"),
        jodaTime("2.3"),
        jodaTime("2.10.1"),
        jodaTime("2.10.7"),
        jodaTime("2.9.2"),
        jodaTime("2.10.6"),
        jodaTime("2.9.3"),
        jodaTime("2.10.8"),
        jodaTime("2.10.5"),
        jodaTime("2.9.5")
      ),
      VersionCompatibility.EarlySemVer
    )
    assertEquals(vs.values.head, "2.10.8")
    assert(vs.values.toList.distinct.size == 1)
  }

  test("resolveVersions") {
    val vs = ResolutionIndex.resolveVersions(
      Set("2.11.2", "2.6.7.1", "2.9.9", "2.10.0", "2.10.2", "2.8.4"),
      VersionCompatibility.EarlySemVer
    )
    assertEquals(vs.head, "2.11.2")
  }

  test("reconciliation with different exclusions") {
    val joda2104 = jodaTime("2.10.4", exclusions = Set("dependency0"))
    val joda281 = jodaTime("2.8.1", exclusions = Set("dependency0"))
    val joda299 = jodaTime("2.9.9", exclusions = Set.empty)

    val vs = ResolutionIndex.reconcileVersions(
      Set(joda2104, joda281, joda299),
      VersionCompatibility.EarlySemVer
    )
    assertEquals(vs.size, 1)
    assertEquals(vs, Map(joda281 -> "2.10.4"))
  }

  test("reconciliation with different dependencies") {
    val joda2104 = jodaTime("2.10.4", dependencies = List("dependency0"))
    val joda281 = jodaTime("2.8.1", dependencies = List("dependency0"))
    val joda299 = jodaTime("2.9.9", dependencies = Nil)

    val vs = ResolutionIndex.reconcileVersions(
      Set(joda2104, joda281, joda299),
      VersionCompatibility.EarlySemVer
    )
    assertEquals(vs.size, 1)
    assertEquals(vs, Map(joda281 -> "2.10.4"))
  }

  def jodaTime(
      v: String,
      exclusions: Set[String] = Set.empty,
      dependencies: List[String] = Nil
  ): ContextualDependency =
    ContextualDependency(
      Dependency(
        Module(Organization("joda-time"), ModuleName("joda-time"), Map.empty),
        v
      ),
      exclusions.map(x => ModuleConfig(x, x)),
      dependencies
    )
}
