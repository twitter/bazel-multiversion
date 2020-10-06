package multideps.commands

import moped.commands.NestedCommand
import moped.annotations.Subcommand

@Subcommand(SaveDepsCommand.parser)
class DepsCommand extends NestedCommand {
  override def run(): Int = 0
}
