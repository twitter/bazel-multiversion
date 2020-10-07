package multideps.commands

import moped.cli.Command
import moped.cli.CommandParser
import multideps.config.WorkspaceConfig
import moped.json.DecodingResult
import coursier.core.Resolution
import coursier.Resolve
import coursier.util.Task
import moped.reporters.Diagnostic
import moped.json.ValueResult
import moped.json.ErrorResult

case class SaveDepsCommand(
    bm: MultidepsApplication
) extends Command {
  def run(): Int = {
    bm.run {
      for {
        workspace <- bm.workspaceConfig
        resolutions <- resolve(workspace)
      } yield 0
    }
  }

  def unify(
      workspace: WorkspaceConfig,
      resolutions: List[Resolution]
  ): DecodingResult[List[Resolution]] = {
    ???
  }
  def resolve(workspace: WorkspaceConfig): DecodingResult[List[Resolution]] = {
    DecodingResult.fromResults {
      for {
        dep <- workspace.dependencies
        cdep <- dep.coursierDependencies
      } yield {
        val resolve = Resolve()
          .addDependencies(cdep)
          .addRepositories(
            workspace.repositories.flatMap(_.coursierRepository): _*
          )
        resolve.either() match {
          case Left(error) => ErrorResult(Diagnostic.error(error.getMessage()))
          case Right(value) => ValueResult(value)
        }
      }
    }
  }
}

object SaveDepsCommand {
  val default = new SaveDepsCommand(MultidepsApplication.default)
  implicit val parser: CommandParser[SaveDepsCommand] =
    CommandParser.derive[SaveDepsCommand](default)
}
