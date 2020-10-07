package multideps.commands

import java.nio.file.Files
import java.nio.file.Path

import coursier.Resolve
import coursier.core.Resolution
import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.DecodingResult
import moped.json.ErrorResult
import moped.json.ValueResult
import moped.reporters.Diagnostic
import moped.reporters.Input
import multideps.configs.ResolutionOutput
import multideps.configs.WorkspaceConfig

case class SaveDepsCommand(
    app: Application
) extends Command {
  def run(): Int = {
    val result = for {
      workspace <- parseWorkspaceConfig()
      resolutions <- resolveDependencies(workspace)
      output <- unifyDependencies(workspace, resolutions)
    } yield 0

    result match {
      case ValueResult(exit) =>
        exit
      case ErrorResult(error) =>
        app.reporter.log(error)
        1
    }
  }

  def parseWorkspaceConfig(): DecodingResult[WorkspaceConfig] = {
    val configPath: Path =
      app.env.workingDirectory.resolve("dependencies.yaml")
    if (!Files.isRegularFile(configPath)) {
      ErrorResult(
        Diagnostic.error(
          s"no such file: $configPath\n\tTo fix this problem, change your working directory or create this file"
        )
      )
    } else {
      WorkspaceConfig.parse(Input.path(configPath))
    }
  }

  def resolveDependencies(
      workspace: WorkspaceConfig
  ): DecodingResult[List[Resolution]] = {
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

  def unifyDependencies(
      workspace: WorkspaceConfig,
      resolutions: List[Resolution]
  ): DecodingResult[ResolutionOutput] = {
    ???
  }

}

object SaveDepsCommand {
  val default = new SaveDepsCommand(Application.default)
  implicit val parser: CommandParser[SaveDepsCommand] =
    CommandParser.derive[SaveDepsCommand](default)
}
