package tests.config

import java.io.ByteArrayOutputStream
import java.io.PrintStream

import moped.json.ErrorResult
import moped.json.ValueResult
import moped.reporters.ConsoleReporter
import moped.reporters.Input
import multideps.config.WorkspaceConfig
import multideps.config._
import munit.TestOptions

class WorkspaceConfigSuite extends BaseSuite {
  val out = new ByteArrayOutputStream()
  val reporter: ConsoleReporter = ConsoleReporter(new PrintStream(out))
  private def parseConfig(
      name: TestOptions,
      text: String,
      onSuccess: WorkspaceConfig => Unit,
      onError: String => Unit
  ): Unit = {
    out.reset()
    reporter.reset()
    WorkspaceConfig.parse(
      Input.filename(name.name + ".yaml", text)
    ) match {
      case ValueResult(value) => onSuccess(value)
      case ErrorResult(error) =>
        reporter.log(error)
        onError(out.toString())
    }
  }
  def check(
      name: TestOptions,
      original: String,
      expected: WorkspaceConfig
  )(implicit loc: munit.Location): Unit = {
    test(name) {
      parseConfig(
        name,
        original,
        onSuccess = { obtained => assertEquals(obtained, expected) },
        onError = { error =>
          fail(error)
        }
      )
    }
  }

  def checkError(
      name: TestOptions,
      original: String,
      expected: String
  )(implicit loc: munit.Location): Unit = {
    test(name) {
      parseConfig(
        name,
        original,
        onSuccess = { obtained =>
          fail(s"expected an error but parsed successfully:\n$obtained")
        },
        onError = { obtained =>
          assertNoDiff(obtained, expected)
        }
      )
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
      dependencies = List(
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
      dependencies = List(
        DependencyConfig(
          organization = "org.scalameta",
          artifact = "munit",
          version = VersionsConfig("0.7.13", Map("old" -> "0.6.9")),
          lang = ScalaLanguagesConfig
        )
      )
    )
  )

  checkError(
    "typo",
    """|deps:
       |  - organization: org.scalameta
       |    artifact: munit
       |    version: "0.7.13"
       |    lang: scala
       |""".stripMargin,
    // TODO(olafur): fix position of object keys
    """|error: unknown field name 'deps'
       |""".stripMargin
  )
}
