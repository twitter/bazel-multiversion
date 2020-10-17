package tests

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes

import multideps.Multideps

import moped.internal.console.Utils
import moped.testkit.DeleteVisitor
import moped.testkit.FileLayout
import moped.testkit.MopedSuite
import munit.TestOptions

abstract class BaseSuite extends MopedSuite(Multideps.app) {
  override val temporaryDirectory: DirectoryFixture = new DirectoryFixture {
    private val tmp = Paths.get(System.getProperty("java.io.tmpdir"))
    private var path: Path = _
    override def apply(): Path = path
    override def beforeAll(): Unit = {
      path = tmp.resolve("bazel-multideps")
    }
    override def afterEach(context: AfterEach): Unit = {
      Files.walkFileTree(
        path,
        new DeleteVisitor {
          override def visitFileFailed(
              file: Path,
              exc: IOException
          ): FileVisitResult = FileVisitResult.CONTINUE
          override def visitFile(
              file: Path,
              attrs: BasicFileAttributes
          ): FileVisitResult = {
            if (!file.getFileName().toString().startsWith("bazel-")) {
              Files.deleteIfExists(file)
            }
            FileVisitResult.CONTINUE
          }
        }
      )

      DeleteVisitor.deleteRecursively(path)
    }
  }

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
        app()
          .process("bazel", "build", buildQuery)
          .call(
            env = Map(
              "PATH" -> sys.env("PATH"),
              "CC" -> "/usr/bin/gcc",
              "CXX" -> "/usr/bin/g++"
            ),
            propagateEnv = true
          )
      }
    }
  }

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

  def bazelWorkspace: String = {
    new StringBuilder()
      .append("/WORKSPACE\n")
      .append(Utils.readFile(Paths.get("WORKSPACE")))
      .append("/.bazeliskrc\n")
      .append(Utils.readFile(Paths.get(".bazeliskrc")))
      .append("/BUILD\n")
      .append("# Empty package\n")
      .append("/3rdparty/BUILD\n")
      .append("# Empty package\n")
      .toString()
  }
  def checkCommand(
      arguments: => List[String],
      expectedOutput: String,
      expectedExit: Int = 0,
      workingDirectoryLayout: String = ""
  )(implicit loc: munit.Location): Unit = {
    if (workingDirectoryLayout.nonEmpty) {
      FileLayout.fromString(workingDirectoryLayout, workingDirectory)
    }
    val exit = app().run(arguments)
    assertEquals(exit, expectedExit, clues(app.capturedOutput))
    assertNoDiff(app.capturedOutput, expectedOutput)
    assertNoDiff(app.capturedOutput, expectedOutput)
  }
}
