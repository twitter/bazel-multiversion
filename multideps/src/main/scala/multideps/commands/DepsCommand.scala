package multideps.commands

import moped.annotations.CommandName
import moped.annotations.Subcommand
import moped.cli.CommandParser
import moped.commands.NestedCommand

@Subcommand(SaveDepsCommand.parser)
@Subcommand(PantsSaveDepsCommand.parser)
@CommandName("deps")
case class DepsCommand() extends NestedCommand {
  override def run(): Int = 0
}

object DepsCommand {
  implicit val parser: CommandParser[DepsCommand] =
    CommandParser.derive(new DepsCommand)
}
