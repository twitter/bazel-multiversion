package multideps.commands

import multideps.diagnostics.MultidepsEnrichments._
import moped.annotations.PositionalArguments
import moped.annotations.CommandName
import java.nio.file.Paths
import java.nio.file.Path
import moped.cli.Command
import moped.annotations.Inline
import moped.cli.CommandParser
import os.Shellable
import os.Inherit
import moped.json.ValueResult
import moped.json.ErrorResult
import moped.reporters.Diagnostic
import moped.json.DecodingResult
import java.nio.file.Files
import moped.reporters.Input
import multideps.configs.ThirdpartyConfig

@CommandName("pants-save")
final case class PantsSaveDepsCommand(
    export: Boolean = true,
    @PositionalArguments()
    pantsTargets: List[String] = List("3rdparty/jvm::"),
    cwd: Option[Path] = None,
    @Inline
    save: SaveDepsCommand = SaveDepsCommand.default
) extends Command {
  def app = save.app

  // NOTE(olafur): I tried to use the --output-path flag but it didn't work for
  // some reason. Hardcoding this flag for now.
  def outputPath: Path = Paths.get(".pants.d", "bazel-multideps.json")

  def run(): Int = app.complete(runResult())
  def runResult(): DecodingResult[Unit] = {
    for {
      _ <- runPantsExport()
      _ <- runPantsImport()
      save <- save.runResult()
    } yield save
  }

  def runPantsImport(): DecodingResult[Unit] = {
    if (!Files.isRegularFile(outputPath)) {
      ErrorResult(
        Diagnostic.error(
          s"no such file: $outputPath\n" +
            s"\tTo fix this problem, run Pants bazel-multideps again."
        )
      )
    } else {
      val input = Input.path(outputPath)
      for {
        thirdparty <- ThirdpartyConfig.parseJson(input)
      } yield {
        pprint.log(thirdparty)
        ()
      }
    }
  }
  def runPantsExport(): DecodingResult[Unit] = {
    if (export) {
      val workingDirectory = cwd.getOrElse(app.env.workingDirectory)
      val binary = workingDirectory.resolve("pants")
      val command = List[String](
        binary.toString(),
        "bazel-multideps"
      ) ++ pantsTargets
      val commandString = command.mkString(" ")
      app.reporter.info(commandString)
      val proc = app
        .process(command.map(Shellable.StringShellable(_)): _*)
        .call(cwd = workingDirectory, stdout = Inherit)
      if (proc.exitCode != 0) {
        ErrorResult(
          Diagnostic.error(
            s"Pants exited with code '${proc.exitCode}'. " +
              s"To reproduce this error, run the command:\n\t$commandString"
          )
        )
      } else {
        ValueResult(())
      }
    } else {
      ValueResult(())
    }
  }
}

object PantsSaveDepsCommand {
  val default = PantsSaveDepsCommand()
  implicit val parser = CommandParser.derive(default)
}
