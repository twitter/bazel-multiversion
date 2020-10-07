package multideps.commands

import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import multideps.config.WorkspaceConfig
import moped.json.DecodingResult
import coursier.core.Resolution

case class SaveDepsCommand(
    bm: MultidepsApplication
) extends Command {
  def run(): Int = {
    bm.run {
      for {
        workspace <- bm.workspaceConfig
        resolution <- resolve(workspace)
      } yield 0
    }
  }

  def resolve(workspace: WorkspaceConfig): DecodingResult[Resolution] = {
    ???
  }
}

object SaveDepsCommand {
  val default = new SaveDepsCommand(MultidepsApplication.default)
  implicit val parser: CommandParser[SaveDepsCommand] =
    CommandParser.derive[SaveDepsCommand](default)
}
