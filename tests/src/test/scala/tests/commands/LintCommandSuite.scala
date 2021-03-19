package tests.commands

import moped.json.Result
import moped.reporters.Diagnostic
import multiversion.commands.LintCommand
import multiversion.diagnostics.LintDiagnostic
import multiversion.resolvers.SimpleModule
import munit.TestOptions
import tests.BaseSuite
import tests.ConfigSyntax

class LintCommandSuite extends BaseSuite with ConfigSyntax {
  test("basic") {
    checkCommand(
      arguments = exportCommand,
      expectedOutput = """|✔ Generated '/workingDirectory/3rdparty/jvm_deps.bzl'
                          |""".stripMargin,
      workingDirectoryLayout = s"""|/3rdparty.yaml
                                   |scala: 2.12.12
                                   |dependencies:
                                   |  - dependency: com.google.guava:guava:29.0-jre
                                   |  - dependency: com.google.guava:guava:27.1-jre
                                   |    targets:
                                   |      - guava27
                                   |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
                                   |    dependencies:
                                   |      - guava27
                                   |$bazelWorkspace
                                   |""".stripMargin
    )
  }

  test("classifier") {
    checkCommand(
      arguments = exportCommand,
      expectedOutput = """|✔ Generated '/workingDirectory/3rdparty/jvm_deps.bzl'
                          |""".stripMargin,
      workingDirectoryLayout = s"""|/3rdparty.yaml
                                   |scala: 2.12.12
                                   |dependencies:
                                   |  - dependency: org.apache.kafka:kafka_2.12:2.4.1
                                   |    versionScheme: pvp
                                   |    targets: [kafka-2.4.1]
                                   |  - dependency: org.apache.kafka:kafka_2.12:2.4.1
                                   |    versionScheme: pvp
                                   |    classifier: test
                                   |    targets: [kafka-test-2.4.1]
                                   |/foo/BUILD
                                   |load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary")
                                   |
                                   |scala_library(
                                   |  name = "foo",
                                   |  srcs = [],
                                   |  deps = [
                                   |    "@maven//:kafka-2.4.1",
                                   |    "@maven//:kafka-test-2.4.1",
                                   |  ],
                                   |)
                                   |$bazelWorkspace
                                   |""".stripMargin,
      lintArgs = List("lint", "//foo:foo"),
    )
  }

  testLintResults(
    "pending produce warnings",
    deps(
      dep("org.reflections:reflections:0.9.11")
        .target("reflections"),
      dep("com.google.inject:guice:4.0")
        .target("guice")
    ),
    List("@maven//:reflections", "@maven//:guice"),
    expectedErrors = List(
      lintWarn("com.google.guava", "guava", "20.0", "16.0.1")
    ),
    tags = "dupped_3rdparty" :: Nil
  )

  testLintResults(
    "conflicting guavas",
    deps(
      dep("org.reflections:reflections:0.9.11")
        .target("reflections"),
      dep("com.google.inject:guice:4.0")
        .target("guice")
    ),
    List("@maven//:reflections", "@maven//:guice"),
    expectedErrors = List(
      lintError("com.google.guava", "guava", "20.0", "16.0.1")
    )
  )

  testLintResults(
    "follows aliases",
    deps(
      dep("org.reflections:reflections:0.9.11")
        .target("reflections"),
      dep("com.google.inject:guice:4.0")
        .target("guice")
    ),
    List("@maven//:reflections", "@maven//:guice"),
    """alias(name="my-alias", actual=":foo")""",
    List(
      lintError("com.google.guava", "guava", "20.0", "16.0.1"),
      lintError("com.google.guava", "guava", "20.0", "16.0.1").copy(target = "//foo:my-alias")
    )
  )

  private def testLintResults(
      name: TestOptions,
      deps: String,
      combine: List[String],
      extraBuild: String = "",
      expectedErrors: List[LintDiagnostic] = Nil,
      tags: List[String] = Nil,
  ): Unit = {
    val workingDirectoryLayout = s"""|/3rdparty.yaml
                                     |scala: 2.12.1403772
                                     |dependencies:
                                     |$deps
                                     |$bazelWorkspace
                                     |/foo/BUILD
                                     |load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library")
                                     |
                                     |scala_library(
                                     |  name = "foo",
                                     |  srcs = [],
                                     |  deps = ${combine.mkString("[\"", "\", \"", "\"]")},
                                     |  tags = ${tags.mkString("[\"", "\", \"", "\"]")},
                                     |)
                                     |$extraBuild""".stripMargin
    test(name) {
      checkCommand(
        arguments = exportCommand ++ List("--no-lint"),
        expectedExit = 0,
        expectedOutput = defaultExpectedOutput,
        workingDirectoryLayout = workingDirectoryLayout
      )
      val expectedResult = Diagnostic.fromDiagnostics(expectedErrors) match {
        case None      => Result.value(())
        case Some(err) => Result.error(err)
      }
      val obtainedResult =
        LintCommand(queryExpressions = "//foo:all" :: Nil, app = app()).runResult()
      assertEquals(obtainedResult, expectedResult)
    }
  }

  private def lintDiagnostic(
      org: String,
      name: String,
      versions: Seq[String],
      pending: Boolean,
  ): LintDiagnostic =
    LintDiagnostic("//foo:foo", SimpleModule(org, name), None, versions.sorted, pending)
  private def lintError(org: String, name: String, versions: String*): LintDiagnostic =
    lintDiagnostic(org, name, versions, pending = false)
  private def lintWarn(org: String, name: String, versions: String*): LintDiagnostic =
    lintDiagnostic(org, name, versions, pending = true)
}
