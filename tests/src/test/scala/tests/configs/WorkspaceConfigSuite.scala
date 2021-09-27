package tests.configs

import moped.json.JsonString
import multiversion.configs._

class WorkspaceConfigSuite extends BaseConfigSuite {
  check(
    "basic",
    """|scala: 2.12.12
       |dependencies:
       |  - organization: org.scalameta
       |    name: munit
       |    version: 0.7.13
       |    lang: scala
       |""".stripMargin,
    ThirdpartyConfig(
      scala = VersionsConfig("2.12.12"),
      dependencies = List(
        DependencyConfig(
          organization = JsonString("org.scalameta"),
          name = "munit",
          version = "0.7.13",
          lang = ScalaLanguagesConfig
        )
      )
    )
  )

  check(
    "custom-version",
    """|scala: 2.12.12
       |dependencies:
       |  - organization: org.scalameta
       |    name: munit
       |    version: "0.7.13"
       |    lang: scala
       |""".stripMargin,
    ThirdpartyConfig(
      scala = VersionsConfig("2.12.12"),
      dependencies = List(
        DependencyConfig(
          organization = JsonString("org.scalameta"),
          name = "munit",
          version = "0.7.13",
          lang = ScalaLanguagesConfig
        )
      )
    )
  )

  checkError(
    "typo",
    """|deps:
       |  - organization: org.scalameta
       |    name: munit
       |    version: "0.7.13"
       |    lang: scala
       |""".stripMargin,
    """|typo.yaml:1 error: unknown field name 'deps' with value [{"organization": "org.scalameta", "name": "munit", "version": "0.7.13", "lang": "scala"}]
       |deps:
       |^
       |""".stripMargin
  )

  check(
    "cross-library",
    """|dependencies:
       |  - dependency: com.google.guava:guava:29.0-jre
       |    crossVersions:
       |      - name: old
       |        version: 25.0-jre
       |        forceVersions:
       |          - com.google.guava:guava:old
       |""".stripMargin,
    ThirdpartyConfig(
      dependencies = List(
        DependencyConfig(
          organization = JsonString("com.google.guava"),
          name = "guava",
          version = "29.0-jre",
          crossVersions = List(
            CrossVersionsConfig(
              name = JsonString("old"),
              version = JsonString("25.0-jre"),
              forceVersions = ForceVersionsConfig(
                Map(ModuleConfig("com.google.guava", "guava") -> "old")
              )
            )
          )
        )
      )
    )
  )
}
