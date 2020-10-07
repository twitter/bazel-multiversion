package multideps.commands

import java.nio.file.Files

import scala.collection.mutable

import coursier.Resolve
import coursier.core.Module
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
import moped.annotations.CommandName
import coursier.params.ResolutionParams

@CommandName("save")
case class SaveDepsCommand(
    app: Application
) extends Command {
  def run(): Int = {
    val result = for {
      workspace <- parseWorkspaceConfig()
      input <- resolveDependencies(workspace)
      output <- unifyDependencies(workspace, input)
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
    val configPath =
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
    pprint.log(workspace)
    val results = workspace.coursierDependencies.map { dep =>
      val resolve = Resolve()
        .addDependencies(dep)
        .withResolutionParams(
          ResolutionParams().addForceVersion(
          )
        )
        .addRepositories(
          workspace.repositories.flatMap(_.coursierRepository): _*
        )
      resolve.either() match {
        case Left(error) => ErrorResult(Diagnostic.error(error.getMessage()))
        case Right(value) => ValueResult(value)
      }
    }
    coursier.core.Version("1.0.0")
    DecodingResult.fromResults(results)
  }

  def unifyDependencies(
      workspace: WorkspaceConfig,
      resolutions: List[Resolution]
  ): DecodingResult[ResolutionOutput] = {
    val artifacts = mutable.Map.empty[Module, mutable.LinkedHashSet[String]]
    for {
      resolution <- resolutions
      (dependency, publication, artifact) <- resolution.dependencyArtifacts()
    } {
      val versions = artifacts.getOrElseUpdate(
        dependency.module,
        mutable.LinkedHashSet.empty
      )
      versions += dependency.version
    }
    pprint.log(artifacts)
    ErrorResult(Diagnostic.error("not implemented yet"))
  }
}

object SaveDepsCommand {
  val default = new SaveDepsCommand(Application.default)
  implicit val parser: CommandParser[SaveDepsCommand] =
    CommandParser.derive[SaveDepsCommand](default)
}
