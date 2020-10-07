package multideps.commands

import moped.annotations.Subcommand
import moped.commands.NestedCommand
import moped.cli.CommandParser

@Subcommand(SaveDepsCommand.parser)
case class DepsCommand() extends NestedCommand {
  override def run(): Int = 0
}

object DepsCommand {
  implicit val parser = CommandParser.derive(new DepsCommand)
}
