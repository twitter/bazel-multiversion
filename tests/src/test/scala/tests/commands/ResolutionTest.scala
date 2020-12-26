package tests.commands

import multideps.outputs.ResolutionIndex

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import coursier.version.VersionCompatibility

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
    println(vs map { case (k, v) => (k.version, v)  })
    assertEquals(vs.values.head, "2.10.8")
    assert(vs.values.toList.distinct.size == 1)
  }

  def jodaTime(v: String): Dependency =
    Dependency(
      Module(Organization("joda-time"), ModuleName("joda-time"), Map.empty),
      v
    )
}
