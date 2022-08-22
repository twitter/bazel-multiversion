package tests.commands

import multiversion.commands.JarsCommand
import munit.TestOptions
import tests.BaseSuite
import tests.ConfigSyntax

class JarsCommandTest extends BaseSuite with ConfigSyntax {
  testJarsResults(
    "basic",
    deps(
      dep("junit:junit:4.12")
        .target("junit"),
    ),
    List(),
    query = "junit:junit",
    expectedOutput = List(
      "@maven//:junit/junit/4.12/junit-4.12.jar",
      "@maven//:junit/junit/4.12/junit-4.12-sources.jar",
    )
  )

  testJarsResults(
    "org:name:ver",
    deps(
      dep("junit:junit:4.12")
        .target("junit"),
    ),
    List(),
    query = "junit:junit:4.12",
    expectedOutput = List("@maven//:junit/junit/4.12/junit-4.12.jar")
  )

  private def testJarsResults(
      name: TestOptions,
      deps: String,
      combine: List[String],
      query: String,
      extraBuild: String = "",
      expectedExitCode: Int = 0,
      expectedOutput: List[String] = Nil,
      tags: List[String] = Nil,
  ): Unit = {
    val workingDirectoryLayout = s"""|/3rdparty.yaml
                                     |scala: 2.12.1403772
                                     |dependencies:
                                     |$deps
                                     |${bazelWorkspace("")}
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
      val obtainedResult =
        JarsCommand(
          modules = List(query),
          app = app()
        ).runCustomQuery()
      assertEquals(obtainedResult.get.toSet, expectedOutput.toSet)
    }
  }
}
