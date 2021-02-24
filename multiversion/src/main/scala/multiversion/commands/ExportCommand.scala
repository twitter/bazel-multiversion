package multiversion.commands

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Duration

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import scala.util.Try

import coursier.cache.ArtifactError
import coursier.cache.CacheDefaults
import coursier.cache.CachePolicy
import coursier.cache.FileCache
import coursier.core.Dependency
import coursier.core.Version
import coursier.util.Artifact
import coursier.util.Task
import coursier.version.VersionCompatibility
import moped.annotations.CommandName
import moped.annotations.Inline
import moped.annotations._
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.ErrorResult
import moped.json.Result
import moped.json.ValueResult
import moped.progressbars.ProgressRenderer
import moped.reporters.Diagnostic
import moped.reporters.Input
import moped.reporters.NoPosition
import multiversion.configs.ThirdpartyConfig
import multiversion.configs.Transformation
import multiversion.diagnostics.ConflictingTransitiveDependencyDiagnostic
import multiversion.diagnostics.MultidepsEnrichments._
import multiversion.loggers._
import multiversion.outputs.ArtifactOutput
import multiversion.outputs.DepsOutput
import multiversion.outputs.Docs
import multiversion.outputs.ResolutionIndex
import multiversion.resolvers.CoursierThreadPools
import multiversion.resolvers.Sha256

@CommandName("export")
case class ExportCommand(
    lint: Boolean = true,
    outputPath: Path = Paths.get("/tmp", "jvm_deps.bzl"),
    cache: Option[Path] = None,
    @Inline
    lintCommand: LintCommand = LintCommand()
) extends Command {
  def app = lintCommand.app
  def run(): Int = {
    app.complete(runResult())
  }
  def runResult(): Result[Unit] = {
    parseThirdpartyConfig().flatMap(t => runResult(t))
  }
  def runResult(thirdparty: ThirdpartyConfig): Result[Unit] = {
    withThreadPool[Result[Unit]] { threads =>
      val coursierCache: FileCache[Task] = FileCache().noCredentials
        .withCachePolicies(
          List(
            // first, use what's available locally
            CachePolicy.LocalOnly,
            // then, try to download what's missing
            CachePolicy.Update
          )
        )
        .withLocation(cache match {
          case Some(c) => c.toFile
          case _       => CacheDefaults.location
        })
        .withTtl(scala.concurrent.duration.Duration.Inf)
        .withPool(threads.downloadPool)
        .withChecksums(Nil)
      for {
        // transformations <- thirdparty.transformations.map(reportTransformations)
        index <- resolveDependencies(thirdparty, coursierCache)
        _ <- {
          if (lint) lintPostResolution(index)
          else ValueResult(())
        }
        output <- generateBzlFile(index, coursierCache)
        _ = app.err.println(Docs.successMessage(s"Generated '$output'"))
        lint <-
          if (lint)
            lintCommand
              .copy(
                queryExpressions = List("@maven//:all"),
                app = app
              )
              .runResult()
          else ValueResult(())
      } yield lint
    }
  }

  private def parseThirdpartyConfig(): Result[ThirdpartyConfig] = {
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

  private val schemes = Map[VersionCompatibility, String](
    VersionCompatibility.Always -> "always",
    VersionCompatibility.Default -> "default",
    VersionCompatibility.EarlySemVer -> "early-semver",
    VersionCompatibility.SemVerSpec -> "semver-spec",
    VersionCompatibility.PackVer -> "pvp",
    VersionCompatibility.Strict -> "strict"
  )

  private def resolveDependencies(
      thirdparty: ThirdpartyConfig,
      cache: FileCache[Task]
  ): Result[ResolutionIndex] = {
    val deps = thirdparty.coursierDeps
    val progressBar = new ResolveProgressRenderer(
      deps.length,
      app.env.clock,
      isTesting = app.isTesting
    )
    val resolveResults = deps.map {
      case (dep, cdep) =>
        thirdparty.toResolve(dep, cache, progressBar, cdep)
    }
    for {
      resolves <- Result.fromResults(resolveResults)
      resolutions <- Result.fromResults(
        runParallelTasks(resolves, progressBar, cache.ec)
      )
    } yield ResolutionIndex.fromResolutions(thirdparty, resolutions)
  }

  // This also downloads SHA files
  def generateBzlFile(
      index: ResolutionIndex,
      cache: FileCache[Task]
  ): Result[Path] = {
    val resolvedArtifacts = index.unevictedArtifacts
    val outputIndex: mutable.Map[String, ArtifactOutput] =
      collection.concurrent.TrieMap.empty[String, ArtifactOutput]
    val progressBar =
      new DownloadProgressRenderer(resolvedArtifacts.length, app.env.clock)
    val files: List[Task[List[Either[Throwable, ArtifactOutput]]]] =
      resolvedArtifacts.map { r =>
        val logger = progressBar.loggers.newCacheLogger(r.dependency)
        val url = r.artifact.checksumUrls.getOrElse("SHA-256", r.artifact.url)
        type Fetch[T] = Task[Either[ArtifactError, T]]
        def tryFetch(artifact: Artifact, policy: CachePolicy): Fetch[File] =
          cache
            .withCachePolicies(List(policy))
            .withLogger(logger)
            .file(artifact)
            .run
        val shaAttempts: List[Fetch[File]] = for {
          // Attempt 1: Fetch "*.jar.sha256" URL locally
          // Attempt 2: Fetch "*.jar" URL locally
          // Attempt 3: Fetch "*.jar.sha256" URL remotely
          // Attempt 4: Fetch "*.jar" URL remotely
          url <- List(
            r.artifact.checksumUrls.get("SHA-256"),
            Some(r.artifact.url)
          ).flatten
          policy <- List(CachePolicy.LocalOnly, CachePolicy.Update)
        } yield tryFetch(r.artifact.withUrl(url), policy)
        val shas = shaAttempts.tail.foldLeft(shaAttempts.head) {
          case (task, nextAttempt) =>
            task.flatMap {
              case Left(_) =>
                // Fetch failed, try next (Url, CachePolicy) combination
                nextAttempt
              case success => Task.point(success)
            }
        }
        shas.map {
          case Right(file) =>
            List(Try {
              val output = ArtifactOutput(
                dependency = r.dependency,
                config = r.config,
                artifact = r.artifact,
                artifactSha256 = Sha256.compute(file)
              )
              outputIndex.put(r.dependency.bazelLabel, output)
              output
            }.toEither)

          case Left(value) =>
            // Ignore download failures. It's common that some dependencies have
            // pom files but no jar files. For example,
            // https://repo1.maven.org/maven2/io/monix/monix_2.12/2.3.2/ There
            // exists `Artifact.optional` and `Dependency.optional`, which seem
            // helpful to distinguish these kinds of dependencies but they are
            // true by default so I'm not sure if they're intended to be used
            // for that purpose.
            Nil
        }
      }
    val all = runParallelTasks(files, progressBar, cache.ec).flatten
    val errors = all.collect { case Left(error) => Diagnostic.exception(error) }
    Diagnostic.fromDiagnostics(errors.toList) match {
      case Some(error) =>
        ErrorResult(error)
      case None =>
        val artifacts: Seq[ArtifactOutput] = all
          .collect({ case Right(a) => a })
          .toList
          .distinctBy(_.label)
        if (artifacts.isEmpty) {
          ErrorResult(
            Diagnostic.error(
              "no resolved artifacts." +
                "To fix this problem, make sure your configuration declares a non-empty list of 'dependencies'."
            )
          )
        } else {
          val rendered =
            DepsOutput(
              artifacts.sortBy(_.dependency.repr),
              index,
              outputIndex
            ).render
          val out =
            if (outputPath.isAbsolute()) outputPath
            else app.env.workingDirectory.resolve(outputPath)
          if (!Files.exists(out.getParent())) {
            Files.createDirectories(out.getParent())
          }
          Files.write(out, rendered.getBytes(StandardCharsets.UTF_8))
          ValueResult(out)
        }
    }
  }

  def lintPostResolution(index: ResolutionIndex): Result[Unit] = {
    val errors = for {
      (module, deps0) <- index.allDependencies.toList
      deps = deps0.map(_._1)
      allVersions = deps.map { d => index.reconciledVersion(d) }
      if allVersions.size > 1
      diagnostic <- index.thirdparty.depsByModule.get(module) match {
        case Some(declaredDeps) =>
          val allDeclaredVersions = declaredDeps.flatMap(_.allVersions)
          val unspecified = (allVersions -- allDeclaredVersions).toList
          unspecified match {
            case Nil =>
              Nil
            case _ =>
              val pos = declaredDeps
                .collectFirst {
                  case d if !d.organization.position.isNone =>
                    d.organization.position
                }
                .getOrElse(NoPosition)
              val rootsForVersions: Map[String, Set[Dependency]] =
                Map(
                  allVersions.toList.map(v =>
                    v ->
                      deps.filter(_.version == v).flatMap(index.roots).toSet
                  ): _*
                )
              val sortedVersions = allVersions.toVector
                .sortBy(v => Version(v))
                .sortBy(v => rootsForVersions(v).size)
                .reverse
              val popularVersion = sortedVersions.head
              val okRoots = rootsForVersions(popularVersion)
              val okDepsConfig =
                okRoots.flatMap(d => index.thirdparty.depsByModule(d.module))
              val unpopularRoots =
                allVersions
                  .filter(_ != popularVersion)
                  .flatMap(rootsForVersions)
                  .toList
                  .sortBy(_.toString)
              val unpopularDepsConfig =
                unpopularRoots.flatMap(d => index.thirdparty.depsByModule(d.module))
              List(
                new ConflictingTransitiveDependencyDiagnostic(
                  module,
                  unspecified.toList,
                  declaredDeps,
                  popularVersion,
                  okRoots.toList,
                  okDepsConfig.toList,
                  unpopularRoots,
                  unpopularDepsConfig,
                  pos
                )
              )
          }
        case None =>
          Nil
      }
      if diagnostic.declaredVersions.nonEmpty
    } yield diagnostic
    Diagnostic.fromDiagnostics(errors) match {
      case Some(diagnostic) => ErrorResult(diagnostic)
      case None             => ValueResult(())
    }
  }

  /*
  private def reconcileVersions(
      versions: collection.Set[Dependency],
      compat: VersionCompatibility
  ): List[Dependency] = {
    return versions.toList
    val parsed = versions.map(d => d -> Version(d.version)).toMap
    val retained = mutable.Map.empty[Dependency, Version]
    parsed.foreach {
      case (dep, version) =>
        retained.find {
          case (_, other) =>
            compat.isCompatible(other.repr, version.repr)
        } match {
          case Some((compatibleDep, compatibleVersion)) =>
            if (compatibleVersion < version) {
              retained.remove(compatibleDep)
              retained(dep) = version
            }
          case None =>
            retained(dep) = version
        }
    }
    retained.keys.toList
  }
   */

  private def withThreadPool[T](fn: CoursierThreadPools => T): T = {
    val threads = new CoursierThreadPools()
    try fn(threads)
    finally threads.close()
  }

  private def runParallelTasks[T](
      tasks: List[Task[T]],
      r: ProgressRenderer,
      ec: ExecutionContext
  ): Seq[T] = {
    val p = ProgressBars.create(app, r, intervalDuration = Duration.ofMillis(100))
    ProgressBars.run(p) {
      Task.gather.gather(tasks).unsafeRun()(ec)
    }
  }

  private def reportTransformations(transformations: List[Transformation]): List[Transformation] = {
    def count(transformations: List[Transformation]): (Int, Int, Int) =
      transformations.foldLeft((0, 0, 0)) {
        case ((add, exc, frc), _: Transformation.Addition)  => (add + 1, exc, frc)
        case ((add, exc, frc), _: Transformation.Exclusion) => (add, exc + 1, frc)
        case ((add, exc, frc), _: Transformation.Force)     => (add, exc, frc + 1)
      }
    val (canon, local) = transformations.partition(_.canonical)
    val (canonAdd, canonExc, canonFrc) = count(canon)
    val (localAdd, localExc, localFrc) = count(local)
    val msg =
      s"Inferred ${canon.length} canonical transformations ($canonAdd additions, $canonExc exclusions, $canonFrc forces) and ${local.length} local transformations ($localAdd, $localExc, $localFrc)."
    app.err.println(Docs.successMessage(msg))
    transformations
  }

}

object ExportCommand {
  val default = new ExportCommand()
  implicit val parser: CommandParser[ExportCommand] =
    CommandParser.derive[ExportCommand](default)
}
