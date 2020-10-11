package multideps.commands

import java.io.PrintWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicInteger
import java.{util => ju}

import scala.util.Try

import multideps.configs.ThirdpartyConfig
import multideps.diagnostics.ConflictingTransitiveDependencyDiagnostic
import multideps.diagnostics.MultidepsEnrichments._
import multideps.loggers.FancyDownloadArtifactLogger
import multideps.loggers.FancyResolveLogger
import multideps.loggers.ProgressLogger
import multideps.outputs.ArtifactOutput
import multideps.outputs.DepsOutput
import multideps.outputs.ResolutionIndex
import multideps.resolvers.CoursierResolver
import multideps.resolvers.ResolvedDependency
import multideps.resolvers.Sha256

import coursier.Resolve
import coursier.cache.CachePolicy
import coursier.cache.FileCache
import coursier.cache.loggers.RefreshLogger
import coursier.core.Resolution
import coursier.params.ResolutionParams
import coursier.paths.Util
import coursier.util.Task
import moped.annotations.CommandName
import moped.annotations._
import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.DecodingResult
import moped.json.ErrorResult
import moped.json.ValueResult
import moped.reporters.Diagnostic
import moped.reporters.Input
import moped.reporters.NoPosition

@CommandName("save")
case class SaveDepsCommand(
    useAnsiOutput: Boolean = Util.useAnsiOutput(),
    app: Application = Application.default
) extends Command {
  def runResult(thirdparty: ThirdpartyConfig): DecodingResult[Unit] = {
    for {
      index <- resolveDependencies(thirdparty)
      _ <- lintPostResolution(index)
      output <- unifyDependencies(index)
      _ = app.info(s"generated: $output")
      lint <- LintCommand()
        .copy(
          queryExpressions = List("@maven//:all"),
          app = app
        )
        .runResult()
    } yield lint
  }
  def runResult(): DecodingResult[Unit] = {
    parseThirdpartyConfig().flatMap(t => runResult(t))
  }
  def run(): Int = {
    app.complete(runResult())
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
      ThirdpartyConfig.parseYaml(Input.path(configPath))
    }
  }
  def newLogger: RefreshLogger = RefreshLogger.create()
  val cache: FileCache[Task] = FileCache().noCredentials
    .withCachePolicies(List(CachePolicy.FetchMissing))
    .withPool(CoursierResolver.downloadPool)
    .withChecksums(Nil)
    // .withRetry(4)
    .withLocation(
      app.env.workingDirectory.resolve("target").resolve("10cache").toFile
    )

  def resolveDependencies(
      thirdparty: ThirdpartyConfig
  ): DecodingResult[ResolutionIndex] = {
    val counter = new AtomicInteger(0)
    val N = thirdparty.dependencies.size
    val coursierDeps = (for {
      dep <- thirdparty.dependencies
      cdep <- dep.coursierDependencies(thirdparty.scala)
    } yield dep -> cdep).distinctBy(_._2)
    val p = new FancyResolveLogger(
      app.env.standardError,
      useAnsiOutput,
      coursierDeps.size
    )
    val results: List[DecodingResult[Resolution]] =
      try {
        p.start()
        val maxWidth =
          if (coursierDeps.isEmpty) 0
          else coursierDeps.map(_._2.repr.length()).max
        val total = coursierDeps.length
        coursierDeps.zipWithIndex.par.map {
          case ((dep, cdep), i) =>
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
                            module.name.position
                          )
                        )
                    }
                  case None =>
                    ErrorResult(
                      Diagnostic.error(
                        s"module '${module.repr}' not found",
                        module.name.position
                      )
                    )
                }
            }
            for {
              forceVersions <- DecodingResult.fromResults(forceVersions)
              result <- {
                val logger = p.newLogger()
                val resolve = Resolve(cache.withLogger(logger))
                  .addDependencies(cdep)
                  .withResolutionParams(
                    ResolutionParams().addForceVersion(forceVersions: _*)
                  )
                  .addRepositories(
                    thirdparty.repositories.flatMap(_.coursierRepository): _*
                  )
                // app.info(f"[${counter.incrementAndGet()}%4s/$N] ${cdep.repr}")
                val result = resolve.either() match {
                  case Left(error) =>
                    ErrorResult(Diagnostic.error(error.getMessage()))
                  case Right(value) => ValueResult(value)
                }
                result
              }
            } yield result
        }.toList
      } finally {
        p.stop()
      }
    DecodingResult
      .fromResults(results.toList)
      .map(resolutions =>
        ResolutionIndex.fromResolutions(thirdparty, resolutions)
      )
  }

  def unifyDependencies(
      index: ResolutionIndex
  ): DecodingResult[Path] = {
    // Step 1: download artifacts
    val artifacts = index.resolutions
      .flatMap(_.dependencyArtifacts().map {
        case (d, p, a) => ResolvedDependency(d, p, a)
      })
      .distinctBy(_.dependency.repr)
    val outputs = new ju.IdentityHashMap[String, ArtifactOutput]
    val counter = new AtomicInteger(0)
    val N = artifacts.size
    val p =
      new FancyDownloadArtifactLogger(app.err, artifacts.size, useAnsiOutput)
    new ProgressLogger[String](
      "Downloaded",
      "jars",
      new PrintWriter(app.env.standardError)
    )
    val files = artifacts.map { r =>
      cache.withLogger(p.newLogger()).file(r.artifact).run.map {
        case Right(file) =>
          Try {
            import scala.collection.JavaConverters._

            val output = ArtifactOutput(
              index = index,
              outputs = outputs.asScala,
              dependency = r.dependency,
              artifact = r.artifact,
              artifactSha256 = Sha256.compute(file)
            )
            outputs.put(r.dependency.repr.intern(), output)
            output
          }.toEither

        case Left(value) => Left(value)
      }
    }
    val all = Task.gather.gather(files).unsafeRun()(CoursierResolver.ec)
    p.stop()
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

  def lintPostGeneration(index: ResolutionIndex): Unit = {}
  def lintPostResolution(index: ResolutionIndex): DecodingResult[Unit] = {
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
      if diagnostic.declaredVersions.nonEmpty
    } yield diagnostic
    Diagnostic.fromDiagnostics(errors) match {
      case Some(diagnostic) => ErrorResult(diagnostic)
      case None => ValueResult(())
    }
  }

}

object SaveDepsCommand {
  val default = new SaveDepsCommand()
  implicit val parser: CommandParser[SaveDepsCommand] =
    CommandParser.derive[SaveDepsCommand](default)
}
