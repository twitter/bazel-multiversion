package multideps.commands

import moped.annotations.PositionalArguments
import moped.annotations.CommandName
import java.nio.file.Paths
import java.nio.file.Path
import moped.cli.Command
import moped.annotations.Inline
import moped.cli.CommandParser
import os.Shellable
import os.Inherit

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
  def exportPath: Path = Paths.get("tools", "maven", "multideps.json")

  def run(): Int = {
    runPantsExport()
    if (app.reporter.hasErrors()) 1
    else save.run()
  }

  def runPantsImport(): Unit = {}
  def runPantsExport(): Unit = {
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
        app.reporter.error(
          s"Pants exited with code '${proc.exitCode}'. " +
            s"To reproduce this error, run the command:\n\t$commandString"
        )
      }
    }
  }
}

object PantsSaveDepsCommand {
  val default = PantsSaveDepsCommand()
  implicit val parser = CommandParser.derive(default)
}
