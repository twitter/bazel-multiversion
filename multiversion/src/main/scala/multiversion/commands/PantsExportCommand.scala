package multiversion.commands

import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

import moped.annotations.CommandName
import moped.annotations.Inline
import moped.annotations.PositionalArguments
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.ErrorResult
import moped.json.Result
import moped.json.ValueResult
import moped.parsers.JsonParser
import moped.progressbars.ProcessRenderer
import moped.reporters.Diagnostic
import moped.reporters.Input
import multiversion.configs.ThirdpartyConfig
import multiversion.diagnostics.MultidepsEnrichments._
import multiversion.loggers.ProgressBars
import os.Shellable

@CommandName("pants-export")
final case class PantsExportCommand(
    useCachedExport: Boolean = false,
    @PositionalArguments()
    pantsTargets: List[String] = List("3rdparty/jvm::"),
    cwd: Option[Path] = None,
    @Inline
    save: ExportCommand = ExportCommand.default
) extends Command {
  def app = save.app
  def workingDirectory: Path = cwd.getOrElse(app.env.workingDirectory)

  def outputPath: Path =
    workingDirectory.resolve(".pants.d").resolve("pants-export.json")
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
            lintCommand = save.lintCommand.copy(
              app = app
                .withEnv(app.env.withWorkingDirectory(workingDirectory))
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
    val binary = workingDirectory.resolve("pants")
    if (useCachedExport) {
      ValueResult(())
    } else if (!Files.isRegularFile(binary)) {
      ErrorResult(
        Diagnostic.error(
          s"no Pants script detected at '$binary'. " +
            s"To fix this problem change the working directory to the root of a Pants build."
        )
      )
    } else {
      val outputOption =
        s"--bazel-multideps-generate-multideps-output-file=$outputPath"
      val command = List[String](
        binary.toString(),
        "bazel-multideps"
      ) ++ pantsTargets ++ List(outputOption)
      val commandString = command.mkString(" ")
      val pr = new ProcessRenderer(command, command, clock = app.env.clock)
      val p = ProgressBars.create(app, pr)
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
    }
  }
}

object PantsExportCommand {
  val default: PantsExportCommand = PantsExportCommand()
  implicit val parser: CommandParser[PantsExportCommand] =
    CommandParser.derive(default)
}
