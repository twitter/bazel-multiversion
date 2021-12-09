package tests

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

import moped.internal.console.Utils
import moped.testkit.DeleteVisitor
import moped.testkit.FileLayout
import moped.testkit.MopedSuite
import multiversion.MultiVersion
import munit.TestOptions
import os.Shellable

abstract class BaseSuite extends MopedSuite(MultiVersion.app) {
  override def environmentVariables: Map[String, String] =
    // Leak the user's environment variable because Bazel needs access to PATH
    // to compile
    sys.env
      .updated(
        "NO_COLOR", // Disable progress bars.
        "true"
      )
      .updated(
        "MULTIDEPS_TESTING",
        "true"
      )
  override val temporaryDirectory: DirectoryFixture = new DirectoryFixture {
    private val tmp = Paths.get(System.getProperty("java.io.tmpdir"))
    private var path: Path = _
    override def apply(): Path = path
    override def beforeAll(): Unit = {
      path = tmp.resolve("bazel-multiversion")
    }
    override def afterEach(context: AfterEach): Unit = {
      Files.walkFileTree(
        path,
        new SimpleFileVisitor[Path] {
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

          override def postVisitDirectory(
              dir: Path,
              exc: IOException
          ): FileVisitResult = {
            val ls = Files.list(dir)
            try {
              if (!ls.iterator().hasNext()) {
                Files.deleteIfExists(dir)
              }
            } finally {
              ls.close()
            }
            FileVisitResult.CONTINUE
          }
        }
      )

      DeleteVisitor.deleteRecursively(path)
    }
  }

  val defaultExpectedOutput: String = """|✔ Generated '/workingDirectory/3rdparty/jvm_deps.bzl'
                                         |""".stripMargin
  val allScalaImports: List[String] = List("kind(scala_import, @maven//:all)")
  val allScalaImportsGraph: List[String] =
    List("kind(scala_import, @maven//:all)", "--output", "graph")
  val allGenrules: List[String] = List("kind(genrule, @maven//:all)")
  val nonSourceGenrules: List[String] =
    List(s"""filter(".+(\\d+\\.)*(\\d+)(\\.Final)?(_test)?$$", kind(genrule, @maven//:all))""")
  def allScalaImportDeps(target: String): List[String] =
    List(s"kind(scala_import, allpaths($target, @maven//:all))")
  def allJars(target: String): List[String] =
    List(s"kind(file, allpaths($target, @maven//:all))")
  def nonSourceJars(target: String): List[String] =
    List(
      s"""filter(".+/.+-(\\d+\\.)*(\\d+)(\\.Final)?(\\.jar)", kind(file, allpaths($target, @maven//:all)))"""
    )
  def nonEmptySrcjar(target: String): List[String] =
    List(s"""attr("srcjar", ".+", deps($target))""")
  def allScalaLibDeps(target: String): List[String] =
    List(s"kind(scala_library, deps($target))")

  def exportCommand: List[String] =
    List("export", "--output-path", "3rdparty/jvm_deps.bzl")

  def checkMultipleDeps(
      name: TestOptions,
      deps: String,
      arguments: List[String] = exportCommand,
      extraBuild: String = "",
      buildQuery: String = "",
      queries: List[(List[String], String)] = Nil,
      expectedExit: Int = 0,
      expectedOutput: String = defaultExpectedOutput
  )(implicit
      loc: munit.Location
  ): Unit = {
    test(name) {
      checkCommand(
        arguments = arguments,
        expectedExit = expectedExit,
        expectedOutput = expectedOutput,
        workingDirectoryLayout = s"""|/3rdparty.yaml
                                     |scala: 2.12.12
                                     |dependencies:
                                     |$deps
                                     |${bazelWorkspace(extraBuild)}
                                     |""".stripMargin
      )
      queries.foreach { case (queryArgs, expectedQuery) =>
        val obtainedQuery =
          app()
            .process(
              ("bazel" :: "query" :: "--notool_deps" :: "--noimplicit_deps" :: queryArgs).map(x =>
                (x: Shellable)
              ): _*
            )
            .call()
            .out
            .text()
            .linesIterator
            .toList
            .sorted
            .mkString("\n")
        assertNoDiff(obtainedQuery, expectedQuery)
      }
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

  def checkDeps(
      name: TestOptions,
      deps: String,
      arguments: List[String] = exportCommand,
      extraBuild: String = "",
      buildQuery: String = "",
      queryArgs: List[String] = Nil,
      expectedQuery: String = "",
      expectedExit: Int = 0,
      expectedOutput: String = defaultExpectedOutput
  )(implicit
      loc: munit.Location
  ): Unit = {
    val queries =
      if (queryArgs.nonEmpty) List((queryArgs, expectedQuery))
      else Nil
    checkMultipleDeps(
      name,
      deps,
      arguments,
      extraBuild,
      buildQuery,
      queries,
      expectedExit,
      expectedOutput
    )
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

  def bazelWorkspace(extraBuild: String): String = {
    new StringBuilder()
      .append("/WORKSPACE\n")
      .append(Utils.readFile(Paths.get("multiversion-example/WORKSPACE")))
      .append("/.bazeliskrc\n")
      .append(Utils.readFile(Paths.get("multiversion-example/.bazeliskrc")))
      .append("/BUILD\n")
      .append("# Empty package\n")
      .append("/3rdparty/BUILD\n")
      .append("# Empty package\n")
      .append(extraBuild)
      .toString()
  }

  def checkCommand(
      arguments: => List[String],
      expectedOutput: String,
      expectedExit: Int = 0,
      workingDirectoryLayout: String = "",
      lintArgs: List[String] = Nil,
  )(implicit loc: munit.Location): Unit = {
    if (workingDirectoryLayout.nonEmpty) {
      FileLayout.fromString(workingDirectoryLayout, workingDirectory)
    }
    app().reporter.reset()
    val exit = app().run(arguments)
    assertEquals(exit, expectedExit, clues(app.capturedOutput))
    assertNoDiff(app.capturedOutput, expectedOutput)
    if (lintArgs.nonEmpty) {
      val exit = app().run(lintArgs)
      assertEquals(exit, 0, clues(app.capturedOutput))
    }
  }
}
