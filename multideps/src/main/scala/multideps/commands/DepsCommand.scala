package multideps.commands

import moped.annotations.Subcommand
import moped.commands.NestedCommand

@Subcommand(SaveDepsCommand.parser)
class DepsCommand extends NestedCommand {
  override def run(): Int = 0
}
