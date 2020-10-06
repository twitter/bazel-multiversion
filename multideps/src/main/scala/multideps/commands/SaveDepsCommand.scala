package multideps.commands

import moped.cli.Command
import moped.cli.CommandParser
import moped.cli.Application

case class SaveDepsCommand(
    app: Application
) extends Command {
  def run(): Int = {
    val config = app.env.workingDirectory.resolve("deps.yaml")
    0
  }
}

object SaveDepsCommand {
  val default = new SaveDepsCommand(Application.default)
  implicit val parser = CommandParser.derive[SaveDepsCommand](default)
}
