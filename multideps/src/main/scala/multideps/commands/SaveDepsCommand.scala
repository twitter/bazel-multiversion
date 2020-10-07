package multideps.commands

import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser

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
  implicit val parser: CommandParser[SaveDepsCommand] =
    CommandParser.derive[SaveDepsCommand](default)
}
