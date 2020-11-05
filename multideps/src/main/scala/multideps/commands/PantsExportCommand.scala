package multideps.commands

import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

import multideps.configs.ThirdpartyConfig
import multideps.diagnostics.MultidepsEnrichments._
import moped.progressbars.ProcessRenderer
import multideps.loggers.ProgressBars

import moped.annotations.CommandName
import moped.annotations.Inline
import moped.annotations.PositionalArguments
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.ErrorResult
import moped.json.Result
import moped.json.ValueResult
import moped.parsers.JsonParser
import moped.progressbars.InteractiveProgressBar
import moped.reporters.Diagnostic
import moped.reporters.Input
import os.Shellable

@CommandName("pants-export")
final case class PantsExportCommand(
    useCachedExport: Boolean = false,
    @PositionalArguments()
    pantsTargets: List[String] = List("3rdparty/jvm::"),
    cwd: Option[Path] = None,
    lint: Boolean = false,
    @Inline
    save: ExportCommand = ExportCommand.default
) extends Command {
  def app = save.app
  def workingDirectory: Path = cwd.getOrElse(app.env.workingDirectory)

  // NOTE(olafur): I tried to use the --output-path flag but it didn't work for
  // some reason. Hardcoding this flag for now.
  def outputPath: Path =
    workingDirectory.resolve(".pants.d").resolve("bazel-multideps.json")
  def inputPath: Path =
    workingDirectory.resolve("3rdparty.yaml")

  def run(): Int = app.complete(runResult())
  def runResult(): Result[Unit] = {
    for {
      _ <- runPantsExport()
      thirdparty <- runPantsImport()
      save <-
        save
          .copy(
            lint = lint,
            lintCommand = save.lintCommand.copy(
              app = app
                .copy(env = app.env.copy(workingDirectory = workingDirectory))
            )
          )
          .runResult(thirdparty)
    } yield save
  }

  def runPantsImport(): Result[ThirdpartyConfig] = {
    if (!Files.isRegularFile(outputPath)) {
      ErrorResult(
        Diagnostic.error(
          s"no such file: $outputPath\n" +
            s"\tTo fix this problem, run Pants bazel-multideps again."
        )
      )
    } else {
      for {
        json <- JsonParser.parse(Input.path(outputPath))
        _ = Files.write(
          outputPath,
          json.toDoc.render(120).getBytes(StandardCharsets.UTF_8)
        )
        thirdparty <- ThirdpartyConfig.parseJson(Input.path(outputPath))
      } yield thirdparty
    }
  }
  def runPantsExport(): Result[Unit] = {
    if (!useCachedExport) {
      val binary = workingDirectory.resolve("pants")
      val command = List[String](
        binary.toString(),
        "bazel-multideps"
      ) ++ pantsTargets
      val commandString = command.mkString(" ")
      val pr = new ProcessRenderer(command, command, clock = app.env.clock)
      val p = new InteractiveProgressBar(
        out = new PrintWriter(app.env.standardError),
        renderer = pr
      )
      val proc = ProgressBars.run(p) {
        app
          .process(command.map(Shellable.StringShellable(_)): _*)
          .call(
            cwd = workingDirectory,
            stdout = pr.output,
            stderr = pr.output,
            check = false
          )
      }
      if (proc.exitCode != 0) {
        pr.asErrorResult(proc.exitCode)
      } else {
        ValueResult(())
      }
    } else {
      ValueResult(())
    }
  }
}

object PantsExportCommand {
  val default: PantsExportCommand = PantsExportCommand()
  implicit val parser: CommandParser[PantsExportCommand] =
    CommandParser.derive(default)
}
