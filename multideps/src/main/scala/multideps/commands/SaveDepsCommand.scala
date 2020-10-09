package multideps.commands

import java.nio.file.Files

import multideps.diagnostics.MultidepsEnrichments.XtensionDependency
import multideps.diagnostics.MultidepsEnrichments.XtensionList
import multideps.resolvers.ResolvedDependency
import multideps.resolvers.CoursierResolver
import multideps.configs.ThirdpartyConfig
import multideps.diagnostics.ConflictingTransitiveDependencyDiagnostic
import multideps.outputs.DepsOutput
import multideps.outputs.ResolutionIndex

import coursier.Resolve
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
import moped.reporters.NoPosition
import coursier.cache.FileCache
import coursier.cache.CachePolicy
import coursier.util.Task
import multideps.resolvers.Sha256
import scala.util.Try
import scala.collection.mutable
import multideps.outputs.ArtifactOutput
import coursier.core.Dependency
import java.nio.charset.StandardCharsets
import java.nio.file.Path

@CommandName("save")
case class SaveDepsCommand(
    app: Application
) extends Command {
  def run(): Int = {
    val result = for {
      thirdparty <- parseThirdpartyConfig()
      index <- resolveDependencies(thirdparty)
      _ <- lintResolutions(index)
      output <- unifyDependencies(index)
    } yield {
      app.info(s"generated: $output")
      0
    }

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
  ): DecodingResult[ResolutionIndex] = {
    val results = for {
      dep <- thirdparty.dependencies
      cdep <- dep.coursierDependencies(thirdparty.scala)
    } yield {
      val forceVersions = dep.forceVersions.overrides.map {
        case (module, version) =>
          thirdparty.depsByModule.get(module.coursierModule) match {
            case Some(depsConfig) =>
              depsConfig.getVersion(version) match {
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
    DecodingResult
      .fromResults(results)
      .map(resolutions =>
        ResolutionIndex.fromResolutions(thirdparty, resolutions)
      )
  }

  def unifyDependencies(
      index: ResolutionIndex
  ): DecodingResult[Path] = {
    // Step 1: download artifacts
    val cache = FileCache()
      .withCachePolicies(List(CachePolicy.FetchMissing))
      .withPool(CoursierResolver.downloadPool)
      .withChecksums(Nil)
    val artifacts = index.resolutions
      .flatMap(_.dependencyArtifacts().map {
        case (d, p, a) => ResolvedDependency(d, p, a)
      })
      .distinctBy(_.dependency)
    val outputs = mutable.LinkedHashMap.empty[Dependency, ArtifactOutput]
    val files = artifacts.map { r =>
      cache.file(r.artifact).run.map {
        case Right(file) =>
          Try {
            val output = ArtifactOutput(
              index = index,
              outputs = outputs,
              dependency = r.dependency,
              artifact = r.artifact,
              artifactSha256 = Sha256.compute(file)
            )
            outputs(r.dependency.withoutMetadata) = output
            output
          }.toEither

        case Left(value) => Left(value)
      }
    }
    val all = Task.gather.gather(files).unsafeRun()(CoursierResolver.ec)
    val errors = all.collect { case Left(error) => Diagnostic.exception(error) }
    Diagnostic.fromDiagnostics(errors.toList) match {
      case Some(error) =>
        ErrorResult(error)
      case None =>
        val artifacts = all.collect { case Right(a) => a }
        val rendered = DepsOutput(artifacts).render
        val out =
          app.env.workingDirectory.resolve("3rdparty").resolve("jvm_deps.bzl")
        Files.createDirectories(out.getParent())
        Files.write(out, rendered.getBytes(StandardCharsets.UTF_8))
        ValueResult(out)
    }
  }

  def lintResolutions(index: ResolutionIndex): DecodingResult[Unit] = {
    return ValueResult(())
    val errors = for {
      (module, versions) <- index.artifacts.toList
      if versions.size > 1
      diagnostic <- index.thirdparty.depsByModule.get(module) match {
        case Some(declared) =>
          val unspecified =
            (versions.map(_.version) -- declared.allVersions).toList
          unspecified match {
            case Nil =>
              Nil
            case _ =>
              List(
                new ConflictingTransitiveDependencyDiagnostic(
                  module,
                  unspecified.toList,
                  declared.allVersions,
                  versions.iterator.flatMap(index.roots.get(_)).flatten.toList,
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
              versions.iterator.flatMap(index.roots.get(_)).flatten.toList,
              NoPosition
            )
          )
      }
    } yield diagnostic
    Diagnostic.fromDiagnostics(errors) match {
      case Some(diagnostic) => ErrorResult(diagnostic)
      case None => ValueResult(())
    }
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
