package tests.commands

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import coursier.core.Version
import coursier.version.VersionCompatibility
import multiversion.configs.VersionConfig
import multiversion.outputs.ResolutionIndex

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
      Set(
        vc("2.11.2", true),
        vc("2.6.7.1", true),
        vc("2.9.9", true),
        vc("2.10.0", true),
        vc("2.10.2", true),
        vc("2.8.4", true)
      ),
      VersionCompatibility.EarlySemVer
    )
    assertEquals(vs.head, vc("2.11.2", true))
  }

  test("resolveVersions with and force = True and False") {
    val vs = ResolutionIndex.resolveVersions(
      Set(vc("2.11.2", false), vc("2.6.7.1", true), vc("2.11.3", false)),
      VersionCompatibility.EarlySemVer
    )
    assertEquals(vs.head, vc("2.6.7.1", true))
  }

  test("resolveVersions with force = False") {
    val vs = ResolutionIndex.resolveVersions(
      Set(vc("2.11.2", false), vc("2.6.7.1", false)),
      VersionCompatibility.EarlySemVer
    )
    assertEquals(vs.head, vc("2.11.2", false))
  }

  test("semver compat") {
    assert(ResolutionIndex.isCompat("2.0.0", "2.4.3", VersionCompatibility.EarlySemVer))
    assert(ResolutionIndex.isCompat("2.0.0", "2.4.3", VersionCompatibility.SemVerSpec))
    assert(ResolutionIndex.isCompat("2.0.0.Final", "2.4.3", VersionCompatibility.SemVerSpec))
  }

  def jodaTime(v: String): (Dependency, VersionConfig) =
    Dependency(
      Module(Organization("joda-time"), ModuleName("joda-time"), Map.empty),
      v
    ) -> vc(v, true)

  def vc(v: String, force: Boolean): VersionConfig = VersionConfig(v, Version(v), force)
}
