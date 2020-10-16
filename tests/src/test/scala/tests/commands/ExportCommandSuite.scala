package tests.commands

import munit.TestOptions
import moped.testkit.FileLayout
import moped.internal.console.Utils

class ExportCommandSuite extends tests.BaseSuite {

  def checkDeps(
      name: TestOptions,
      deps: String,
      buildQuery: String = "",
      expectedQuery: String = ""
  )(implicit
      loc: munit.Location
  ): Unit = {
    test(name) {
      checkCommand(
        arguments = List("export", "--no-use-ansi-output"),
        expectedExit = 0,
        expectedOutput =
          """|✔ Generated '/workingDirectory/3rdparty/jvm_deps.bzl'
             |""".stripMargin,
        workingDirectoryLayout = s"""|/3rdparty.yaml
                                     |scala: 2.12.12
                                     |dependencies:
                                     |$deps
                                     |$bazelWorkspace
                                     |""".stripMargin
      )
      if (expectedQuery.nonEmpty) {
        val obtainedQuery =
          app()
            .process("bazel", "query", "kind(scala_import, @maven//:all)")
            .call()
            .out
            .text()
            .linesIterator
            .toList
            .sorted
            .mkString("\n")
        assertNoDiff(obtainedQuery, expectedQuery)
      }
      pprint.log(FileLayout.asString(workingDirectory.resolve("src")))
      if (buildQuery.nonEmpty) {
        app().process("bazel", "build", buildQuery).call()
      }
    }

  }

  checkDeps(
    "basic",
    """|  - dependency: com.google.guava:guava:29.0-jre
       |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
       |""".stripMargin
  )

  checkDeps(
    "libthrift",
    """|  - dependency: org.apache.thrift:libthrift:0.10.0
       |""".stripMargin
  )

  def scalaLibrary(name: String, code: String): String =
    s"""|/src/$name
        |${code.stripSuffix("\n") + "\n"}
        |/src/BUILD
        |load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library")
        |scala_library(
        |  name = "src",
        |  srcs = ["$name"],
        |)
        |""".stripMargin

  checkDeps(
    "version_scheme".only,
    s"""|  - dependency: com.lihaoyi:fansi_2.12:0.2.8
        |    versionScheme: pvp
        |  - dependency: com.lihaoyi:pprint_2.12:0.5.6
        |${scalaLibrary("MyApp.scala", "object MyApp")}
        |""".stripMargin,
    buildQuery = "//src/..."
  )
}
