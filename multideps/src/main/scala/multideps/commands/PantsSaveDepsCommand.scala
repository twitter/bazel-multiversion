package multideps.commands

import moped.annotations.PositionalArguments
import moped.annotations.CommandName
import java.nio.file.Paths
import java.nio.file.Path
import moped.cli.Command
import moped.annotations.Inline
import moped.cli.CommandParser
import os.Shellable

@CommandName("pants-save")
final case class PantsSaveDepsCommand(
    export: Boolean = true,
    exportPath: Path = Paths.get("tools", "maven", "multideps.json"),
    @PositionalArguments()
    pantsTargets: List[String] = List("3rdparty/jvm::"),
    @Inline
    save: SaveDepsCommand = SaveDepsCommand.default
) extends Command {
  def app = save.app
  def run(): Int = {
    runPantsExport()
    if (app.reporter.hasErrors()) 1
    else save.run()
  }
  def runPantsExport(): Unit = {
    if (export) {
      val binary = app.env.workingDirectory.resolve("pants")
      val command = List[String](
        binary.toString(),
        "bazel-multideps"
      ) ++ pantsTargets
      val proc =
        app.process(command.map(Shellable.StringShellable(_)): _*).call()
      if (proc.exitCode != 0) {
        val repro = command.mkString(" ")
        app.reporter.error(
          s"Pants exited with code '${proc.exitCode}'. " +
            s"To reproduce this error, run the command:\n\t$repro"
        )
      }
    }
  }
}

object PantsSaveDepsCommand {
  val default = PantsSaveDepsCommand()
  implicit val parser = CommandParser.derive(default)
}
