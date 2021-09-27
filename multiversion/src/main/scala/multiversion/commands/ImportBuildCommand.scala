package multiversion.commands

import java.nio.file.Path
import java.nio.file.Paths

import moped.annotations.CommandName
import moped.annotations.Inline
import moped.annotations.PositionalArguments
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.Result
import moped.json.ValueResult
import moped.progressbars.ProcessRenderer
import multiversion.buildish.Buildish
import multiversion.configs.ThirdpartyConfig
import multiversion.diagnostics.MultidepsEnrichments._
import multiversion.loggers.ProgressBars

@CommandName("import-build")
final case class ImportBuildCommand(
    @PositionalArguments()
    buildPath: String = "3rdparty/jvm",
    cwd: Option[Path] = None,
    @Inline
    save: ExportCommand = ExportCommand.default
) extends Command {
  def app = save.app
  def workingDirectory: Path = cwd.getOrElse(app.env.workingDirectory)

  def run(): Int = app.complete(runResult())
  def runResult(): Result[Unit] =
    for {
      thirdparty <- runBuildImport()
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

  def runBuildImport(): Result[ThirdpartyConfig] = {
    val command = List("import BUILD files")
    val pr = new ProcessRenderer(command, command, clock = app.env.clock)
    val p = ProgressBars.create(app, pr)
    val proc = ProgressBars.run(p) {
      Buildish.evalRecursive(Paths.get(buildPath).toAbsolutePath, workingDirectory)
    }
    // if (proc.exitCode != 0) {
    //   pr.asErrorResult(proc.exitCode)
    // } else {
    //   ValueResult(())
    // }
    ValueResult(proc)
  }
}

object ImportBuildCommand {
  val default: ImportBuildCommand = ImportBuildCommand()
  implicit val parser: CommandParser[ImportBuildCommand] =
    CommandParser.derive(default)
}
