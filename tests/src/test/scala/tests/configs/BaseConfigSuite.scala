package tests.configs

import java.io.ByteArrayOutputStream
import java.io.PrintStream

import moped.json.ErrorResult
import moped.json.ValueResult
import moped.reporters.ConsoleReporter
import moped.reporters.Input
import multiversion.configs.ThirdpartyConfig
import munit.TestOptions

abstract class BaseConfigSuite extends munit.FunSuite {
  private val out = new ByteArrayOutputStream()
  private val reporter: ConsoleReporter = ConsoleReporter(new PrintStream(out))
  def parseConfig(
      name: TestOptions,
      text: String,
      onSuccess: ThirdpartyConfig => Unit,
      onError: String => Unit
  ): Unit = {
    out.reset()
    reporter.reset()
    ThirdpartyConfig.parseYaml(
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
      expected: ThirdpartyConfig
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

}
