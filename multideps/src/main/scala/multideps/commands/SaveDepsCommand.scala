package multideps.commands

import java.nio.file.Files

import scala.collection.mutable

import multideps.configs.ResolutionOutput
import multideps.configs.ThirdpartyConfig

import coursier.Resolve
import coursier.core.Module
import coursier.core.Resolution
import coursier.params.ResolutionParams
import moped.annotations.CommandName
import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.DecodingResult
import moped.json.ErrorResult
import moped.json.ValueResult
import moped.reporters.Diagnostic
import moped.reporters.Input

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

  def parseWorkspaceConfig(): DecodingResult[ThirdpartyConfig] = {
    val configPath =
      app.env.workingDirectory.resolve("3rdparty.yaml")
    if (!Files.isRegularFile(configPath)) {
      ErrorResult(
        Diagnostic.error(
          s"no such file: $configPath\n\tTo fix this problem, change your working directory or create this file"
        )
      )
    } else {
      ThirdpartyConfig.parse(Input.path(configPath))
    }
  }

  def resolveDependencies(
      workspace: ThirdpartyConfig
  ): DecodingResult[List[Resolution]] = {
    pprint.log(workspace)
    val results = for {
      dep <- workspace.dependencies
      cdep <- dep.coursierDependencies(workspace.scala)
    } yield {
      val forceVersions = dep.forceVersions.overrides.map {
        case (module, version) =>
          workspace.depsByModule.get(module.coursierModule) match {
            case Some(depsConfig) =>
              depsConfig.version.get(version) match {
                case Some(forcedVersion) =>
                  ValueResult(
                    depsConfig.coursierModule(
                      workspace.scala
                    ) -> forcedVersion
                  )
                case None =>
                  // TODO: report "did you mean?"
                  ErrorResult(
                    Diagnostic.error(
                      s"version '$version' not found",
                      module.moduleName.position
                    )
                  )
              }
            case None =>
              ErrorResult(
                Diagnostic.error(
                  s"module '${module.repr}' not found",
                  module.moduleName.position
                )
              )
          }
      }
      for {
        forceVersions <- DecodingResult.fromResults(forceVersions)
        resolve = Resolve()
          .addDependencies(cdep)
          .withResolutionParams(
            ResolutionParams().addForceVersion(forceVersions: _*)
          )
          .addRepositories(
            workspace.repositories.flatMap(_.coursierRepository): _*
          )
        result <- resolve.either() match {
          case Left(error) => ErrorResult(Diagnostic.error(error.getMessage()))
          case Right(value) => ValueResult(value)
        }
      } yield result
    }
    coursier.core.Version("1.0.0")
    DecodingResult.fromResults(results)
  }

  def unifyDependencies(
      workspace: ThirdpartyConfig,
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
