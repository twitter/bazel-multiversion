package tests

import multideps.Multideps

import moped.testkit.MopedSuite
import moped.testkit.FileLayout
import java.nio.file.Paths
import moped.internal.console.Utils

abstract class BaseSuite extends MopedSuite(Multideps.app) {

  def bazelWorkspace: String = {
    new StringBuilder()
      .append("/WORKSPACE\n")
      .append(Utils.readFile(Paths.get("WORKSPACE")))
      .append("/.bazeliskrc\n")
      .append(Utils.readFile(Paths.get(".bazeliskrc")))
      .append("/BUILD\n")
      .append("# Empty package")
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
