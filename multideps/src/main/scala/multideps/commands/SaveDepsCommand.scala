package multideps.commands

import java.nio.file.Files

import scala.collection.mutable

import multideps.diagnostics.ConflictingTransitiveDependencyDiagnostic
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
import moped.reporters.Position
import moped.reporters.NoPosition
import scala.collection.immutable.Nil
import coursier.core.Dependency

@CommandName("save")
case class SaveDepsCommand(
    app: Application
) extends Command {
  def run(): Int = {
    val result = for {
      thirdparty <- parseThirdpartyConfig()
      input <- resolveDependencies(thirdparty)
      output <- unifyDependencies(thirdparty, input)
    } yield 0

    result match {
      case ValueResult(exit) =>
        exit
      case ErrorResult(error) =>
        app.reporter.log(error)
        1
    }
  }

  def parseThirdpartyConfig(): DecodingResult[ThirdpartyConfig] = {
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
      thirdparty: ThirdpartyConfig
  ): DecodingResult[List[Resolution]] = {
    pprint.log(thirdparty)
    val results = for {
      dep <- thirdparty.dependencies
      cdep <- dep.coursierDependencies(thirdparty.scala)
    } yield {
      val forceVersions = dep.forceVersions.overrides.map {
        case (module, version) =>
          thirdparty.depsByModule.get(module.coursierModule) match {
            case Some(depsConfig) =>
              depsConfig.version.get(version) match {
                case Some(forcedVersion) =>
                  ValueResult(
                    depsConfig.coursierModule(
                      thirdparty.scala
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
            thirdparty.repositories.flatMap(_.coursierRepository): _*
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
      thirdparty: ThirdpartyConfig,
      resolutions: List[Resolution]
  ): DecodingResult[ResolutionOutput] = {
    val artifacts =
      mutable.LinkedHashMap.empty[Module, mutable.LinkedHashSet[Dependency]]
    val roots =
      mutable.LinkedHashMap.empty[Dependency, mutable.LinkedHashSet[Dependency]]
    for {
      resolution <- resolutions
      root <- resolution.rootDependencies
      (dependency, _, _) <- resolution.dependencyArtifacts()
    } {
      val buf = roots.getOrElseUpdate(dependency, mutable.LinkedHashSet.empty)
      buf += root
    }
    for {
      resolution <- resolutions
      (dependency, publication, artifact) <- resolution.dependencyArtifacts()
    } {
      val versions = artifacts.getOrElseUpdate(
        dependency.module,
        mutable.LinkedHashSet.empty
      )
      versions += dependency
    }
    def conflictingVersions(
        versions: Iterable[String],
        module: Module,
        pos: Position
    ): Diagnostic =
      Diagnostic.error(
        s"conflicting versions for module '${module.repr}': ${versions.mkString(", ")}",
        pos
      )
    val conflicts: List[Diagnostic] = for {
      (module, versions) <- artifacts.toList
      if versions.size > 1
      diagnostic <- thirdparty.depsByModule.get(module) match {
        case Some(declared) =>
          val unspecified =
            (versions.map(_.version) -- declared.version.all).toList
          pprint.log(declared.version.all)
          pprint.log(unspecified)
          unspecified match {
            case Nil =>
              Nil
            case _ =>
              List(
                new ConflictingTransitiveDependencyDiagnostic(
                  module,
                  unspecified.toList,
                  declared.version.all,
                  versions.iterator.flatMap(roots.get(_)).flatten.toList,
                  declared.organization.position
                )
              )
          }
        case None =>
          List(
            new ConflictingTransitiveDependencyDiagnostic(
              module,
              versions.map(_.version).toList,
              Nil,
              versions.iterator.flatMap(roots.get(_)).flatten.toList,
              NoPosition
            )
          )
      }
    } yield diagnostic
    conflicts.foreach(d => app.reporter.log(d))
    // pprint.log(artifacts)
    ErrorResult(Diagnostic.error("not implemented yet"))
  }

  private implicit class XtensionStrings(xs: Iterable[String]) {
    def commas: String =
      if (xs.isEmpty) ""
      else if (xs.size == 1) xs.head
      else xs.mkString(", ")
  }
}

object SaveDepsCommand {
  val default = new SaveDepsCommand(Application.default)
  implicit val parser: CommandParser[SaveDepsCommand] =
    CommandParser.derive[SaveDepsCommand](default)
}
