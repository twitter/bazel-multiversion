package multideps.commands

import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser

case class SmoketestCommand(app: Application = Application.default)
    extends Command {
  def run(): Int = {
    app.process("tools/scripts/bazel_smoketest.sh", "ALL").call().exitCode
  }
}

object SmoketestCommand {
  implicit val parser: CommandParser[SmoketestCommand] = CommandParser.derive(SmoketestCommand())
}
