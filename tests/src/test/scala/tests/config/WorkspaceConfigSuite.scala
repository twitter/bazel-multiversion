package tests.config

import munit.TestOptions
import multideps.config.WorkspaceConfig
import moped.parsers.YamlParser
import moped.reporters.Input
import moped.json.DecodingContext
import multideps.config._

class WorkspaceConfigSuite extends BaseSuite {
  def check(
      name: TestOptions,
      original: String,
      expected: WorkspaceConfig
  ): Unit = {
    test(name) {
      val json =
        YamlParser.parse(Input.filename(name.name + ".yaml", original)).get
      val obtained = WorkspaceConfig.codec.decode(DecodingContext(json)).get
      assertEquals(obtained, expected)
    }
  }

  check(
    "basic",
    """|dependencies:
       |  - organization: org.scalameta
       |    artifact: munit
       |    version: "0.7.13"
       |    lang: scala
       |""".stripMargin,
    WorkspaceConfig(
      List(
        DependencyConfig(
          organization = "org.scalameta",
          artifact = "munit",
          version = VersionsConfig("0.7.13"),
          lang = ScalaLanguagesConfig
        )
      )
    )
  )

  check(
    "custom-version",
    """|dependencies:
       |  - organization: org.scalameta
       |    artifact: munit
       |    version:
       |      default: "0.7.13"
       |      old: "0.6.9"
       |    lang: scala
       |""".stripMargin,
    WorkspaceConfig(
      List(
        DependencyConfig(
          organization = "org.scalameta",
          artifact = "munit",
          version = VersionsConfig("0.7.13", Map("old" -> "0.6.9")),
          lang = ScalaLanguagesConfig
        )
      )
    )
  )
}
