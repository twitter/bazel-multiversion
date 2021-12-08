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
import moped.reporters.ErrorSeverity
import moped.reporters.Input
import moped.reporters.NoPosition
import moped.reporters.WarningSeverity
import multiversion.configs.DependencyConfig
import multiversion.configs.ModuleConfig
import multiversion.configs.ThirdpartyConfig
import multiversion.diagnostics.ConflictingTransitiveDependencyDiagnostic
import multiversion.diagnostics.EvictedDeclaredDependencyDiagnostic
import multiversion.diagnostics.ForbiddenUrlAttributeDiagnostic
import multiversion.diagnostics.IntraTargetConflictDiagnostic
import multiversion.diagnostics.MultidepsEnrichments._
import multiversion.diagnostics.TransitiveUrlDiagnostic
import multiversion.loggers._
import multiversion.outputs.ArtifactOutput
import multiversion.outputs.DependencyResolution
import multiversion.outputs.DepsOutput
import multiversion.outputs.Docs
import multiversion.outputs.ResolutionIndex
import multiversion.resolvers.CoursierThreadPools
import multiversion.resolvers.DependencyId
import multiversion.resolvers.Sha256

@CommandName("export")
case class ExportCommand(
    lint: Boolean = true,
    outputPath: Path = Paths.get("/tmp", "jvm_deps.bzl"),
    cache: Option[Path] = None,
    @Inline
    lintCommand: LintCommand = LintCommand(),
    @Description("Retry limit when fetching a file.")
    @ParseAsNumber
    retryCount: Int = 5,
    @Description("Number of parallel resolves and downloads.")
    @ParseAsNumber
    parallel: Int = 4,
    @Description("Number of parallel resolves and downloads.")
    @ParseAsNumber
    parallelDownload: Option[Int] = None,
    @Description("Report an error if a declared dependency is evicted")
    failOnEvictedDeclared: Boolean = true,
    @Description("Whether to allow `url` attribute in dependency configurations")
    allowUrl: Boolean = true,
) extends Command {
  def app = lintCommand.app
  def run(): Int = {
    app.complete(runResult())
  }
  def runResult(): Result[Unit] = {
    parseThirdpartyConfig().flatMap(t => runResult(t))
  }

  private def downloadPar: Int =
    parallelDownload.getOrElse(parallel)

  def runResult(thirdparty: ThirdpartyConfig): Result[Unit] =
    withThreadPool[Result[Unit]](parallel, downloadPar) { threads =>
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
        .withRetry(retryCount)

      for {
        withUrls <- lintUrls(thirdparty, allowUrl)
        initialResolutions <- runResolutions(withUrls, None, withUrls.coursierDeps, coursierCache)
        initialIndex = ResolutionIndex.fromResolutions(
          withUrls,
          initialResolutions,
          resolveSources = false
        )
        withSelectedVersions = selectVersionsFromIndex(withUrls, initialIndex)
        withOverriddenTargets = overrideTargets(withSelectedVersions, initialIndex)
        intermediateResolutions <- runResolutions(
          withOverriddenTargets,
          None,
          withOverriddenTargets.coursierDeps,
          coursierCache
        )
        intermediateIndex =
          ResolutionIndex.fromResolutions(
            withOverriddenTargets,
            intermediateResolutions,
            resolveSources = false
          )
        resolutions <- runResolutions(
          withOverriddenTargets,
          Some(intermediateIndex),
          withOverriddenTargets.coursierDeps,
          coursierCache
        )
        index = ResolutionIndex.fromResolutions(
          withOverriddenTargets,
          resolutions,
          resolveSources = true
        )
        _ <- lintEvictedDeclaredDependencies(
          withUrls,
          initialIndex,
          index,
          failOnEvictedDeclared
        )
        _ <- lintIntraTargetConflicts(index)
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

  /**
   * Report errors for dependencies that set the URL of the JAR to resolve when `allowUrl` is not
   * set, and report warnings for dependencies that set the URL of the JAR to resolve, but do not
   * set the intransitive flag.
   *
   * @param thirdparty
   *   The third party configuration
   * @param allowUrl
   *   Whether setting the URL of the JAR in a dependency is allowed
   * @return
   *   The new third party configuration where dependencies that set the URl of the JAR to resolve
   *   are all marked `intransitive`.
   */
  private def lintUrls(
      thirdparty: ThirdpartyConfig,
      allowUrl: Boolean
  ): Result[ThirdpartyConfig] = {
    val diagnostics = thirdparty.dependencies.flatMap { dep =>
      val forbiddenUrl =
        if (dep.url.isDefined && !allowUrl)
          List(new ForbiddenUrlAttributeDiagnostic(dep.targets, dep.organization.position))
        else Nil
      val transitiveUrl =
        if (dep.url.isDefined && dep.transitive)
          List(new TransitiveUrlDiagnostic(dep.targets, dep.organization.position))
        else Nil
      forbiddenUrl ++ transitiveUrl
    }

    val newDependencies = thirdparty.dependencies.map {
      case dep if dep.url.isDefined && dep.transitive => dep.copy(transitive = false)
      case dep                                        => dep
    }

    app.reportOrElse(diagnostics, thirdparty.copy(dependencies = newDependencies))
  }

  /**
   * Re-configure the dependencies whose resolution include overridden targets (artifacts that are
   * published but that are available as source dependency), so that the published artifacts are
   * excluded, and add the dependency on the overridding target.
   *
   * This is required to support Pants' round-trip dependencies:
   * https://v1.pantsbuild.org/3rdparty_jvm.html#round-trip-dependencies
   */
  private def overrideTargets(
      thirdparty: ThirdpartyConfig,
      index: ResolutionIndex
  ): ThirdpartyConfig = {
    val updatedDependencies = thirdparty.dependencies
      .map { config =>
        val overridden = for {
          dep <- index.dependencies.getOrElse(config.id, Nil)
          target <- thirdparty.overrideTargetsMap.getOrElse(dep.module, Nil)
          // Target overrides can be used to replace a module by a given target. If the
          // replacing target is also a 3rdparty dependency D, then we don't apply the override
          // when resolving D.
          if !config.targets.contains(target)
        } yield ModuleConfig(dep.module) -> target
        val depsToExclude = overridden.map(_._1)
        val targetsToAdd = overridden.map(_._2)

        config.copy(
          exclusions = config.exclusions ++ depsToExclude,
          dependencies = (config.dependencies ++ targetsToAdd).distinct
        )
      }
    thirdparty.copy(dependencies = updatedDependencies)
  }

  /**
   * Update the thirdparty configuration so that the versions of the declared dependencies matches
   * those that were selected in `index`.
   */
  private def selectVersionsFromIndex(
      thirdparty: ThirdpartyConfig,
      index: ResolutionIndex
  ): ThirdpartyConfig = {
    val updatedDependencies = thirdparty.dependencies
      .foldLeft(Map.empty[DependencyId, DependencyConfig]) { case (deps, originalDependency) =>
        val dep = originalDependency.toCoursierDependency(thirdparty.scala)
        val reconciledVersion = index.reconciledVersion(dep)
        val reconciledDependency = originalDependency.copy(version = reconciledVersion)
        val reconciledId = reconciledDependency.id
        // Different dependencies may reconcile to the same version. In this case, make sure
        // we don't lose the targets they originate from.
        deps.get(reconciledId) match {
          case None => deps + (reconciledId -> reconciledDependency)
          case Some(existing) =>
            val allTargets = (existing.targets ++ reconciledDependency.targets).distinct
            deps + (reconciledId -> existing.copy(targets = allTargets))
        }
      }
      .values
      .toList
    thirdparty.copy(dependencies = updatedDependencies)
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
        type Fetch[T] = Task[Either[ArtifactError, T]]
        def tryFetch(artifact: Artifact, policy: CachePolicy): Fetch[File] =
          cache
            .withCachePolicies(List(policy))
            .withLogger(logger)
            .file(artifact)
            .run
        def foldShas(attempts: List[Fetch[File]]): Fetch[File] =
          if (attempts.isEmpty) Task.point(Left(new ArtifactError.NotFound("")))
          else
            attempts.tail.foldLeft(attempts.head) { case (task, nextAttempt) =>
              task.flatMap {
                case Left(_) =>
                  // Fetch failed, try next (Url, CachePolicy) combination
                  nextAttempt
                case success => Task.point(success)
              }
            }
        val shas = foldShas(for {
          // Attempt 1: Fetch "*.jar.sha256" URL locally
          // Attempt 2: Fetch "*.jar" URL locally
          // Attempt 3: Fetch "*.jar.sha256" URL remotely
          // Attempt 4: Fetch "*.jar" URL remotely
          url <- List(
            r.artifact.checksumUrls.get("SHA-256"),
            Some(r.artifact.url)
          ).flatten
          policy <- List(CachePolicy.LocalOnly, CachePolicy.Update)
        } yield tryFetch(r.artifact.withUrl(url), policy))
        val sourcesShas = foldShas(for {
          url <- List(
            r.sourcesArtifact.flatMap(_.checksumUrls.get("SHA-256")),
            r.sourcesArtifact.map(_.url),
          ).flatten
          policy <- List(CachePolicy.LocalOnly, CachePolicy.Update)
        } yield tryFetch(r.artifact.withUrl(url), policy))
        shas.flatMap {
          case Right(file) =>
            (sourcesShas
              .map {
                case Right(sourceFile) => Some(sourceFile)
                case Left(_)           => None
              })
              .map { sourceSha =>
                List(Try {
                  val output = ArtifactOutput(
                    dependency = r.dependency,
                    artifact = r.artifact,
                    artifactSha256 = Sha256.compute(file),
                    sourcesArtifact = r.sourcesArtifact,
                    sourcesArtifactSha256 = sourceSha.map(Sha256.compute)
                  )
                  outputIndex.put(r.dependency.bazelLabel, output)
                  output
                }.toEither)
              }
          case Left(value) =>
            // Ignore download failures. It's common that some dependencies have
            // pom files but no jar files. For example,
            // https://repo1.maven.org/maven2/io/monix/monix_2.12/2.3.2/ There
            // exists `Artifact.optional` and `Dependency.optional`, which seem
            // helpful to distinguish these kinds of dependencies but they are
            // true by default so I'm not sure if they're intended to be used
            // for that purpose.
            Task.point(Nil)
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

  /**
   * Report dependencies that are declared in `originalThirdParty` but were evicted. If
   * `failOnEvictedDeclared` is set, then an error will be returned when such dependencies are
   * found.
   *
   * When a declared dependency is evicted and sets the URL of the JAR to resolve, then the message
   * will always be considered an error, regardless of `failOnEvictedDeclared`.
   *
   * @param originalThirdParty
   *   The original third party configuration, as configured in the input file.
   * @param originalIndex
   *   The resolution index built after the first resolution.
   * @param index
   *   The final resolution index, representing the dependencies that are selected for the
   *   dependency graph.
   * @param failOnEvictedDeclared
   *   If set, the eviction of a declared dependency will trigger and error, otherwise a warning.
   * @return
   *   The linting report.
   */
  private def lintEvictedDeclaredDependencies(
      originalThirdParty: ThirdpartyConfig,
      originalIndex: ResolutionIndex,
      index: ResolutionIndex,
      failOnEvictedDeclared: Boolean
  ): Result[Unit] = {
    def selectedDependencyOf(config: DependencyConfig): Dependency = {
      val originalDependency = config.toCoursierDependency(originalThirdParty.scala)
      // The original dependency could have been evicted after the first resolution already,
      // and not appear in `index` at all.
      val selectedDependency =
        if (index.dependencies.contains(config.id)) originalDependency
        else originalIndex.reconciledDependency(originalDependency)
      index.reconciledDependency(selectedDependency)
    }

    def targetsDependingOn(dep: Dependency): Set[String] = {
      val needleRepr = dep.repr
      val ids = index.dependencies.collect {
        case (id, dependencies) if dependencies.exists(_.repr == needleRepr) => id
      }.toSet
      originalThirdParty.dependencies
        .collect {
          case cfg if ids.contains(cfg.id) =>
            cfg.targets
        }
        .flatten
        .toSet
    }

    val diagnostics =
      for {
        dependency <- originalThirdParty.dependencies
        declaringTargets = dependency.targets
        declaredVersion = dependency.version
        declaredDependency = dependency.toCoursierDependency(originalThirdParty.scala)
        selectedDependency = selectedDependencyOf(dependency)
        if declaredDependency.version != selectedDependency.version && dependency.force
        breakingTargets = targetsDependingOn(selectedDependency) -- declaringTargets
        severity =
          if (dependency.url.isDefined || failOnEvictedDeclared) ErrorSeverity else WarningSeverity
      } yield new EvictedDeclaredDependencyDiagnostic(
        declaredDependency,
        declaringTargets,
        selectedDependency,
        breakingTargets,
        hasUrl = dependency.url.isDefined,
        severity,
        dependency.organization.position
      )

    app.reportOrElse(diagnostics, ())
  }

  /**
   * Report the conflicts that happen in a single target.
   *
   * Such conflicts arise when several dependencies share the same `target`, and these dependencies
   * have conflicting transitive dependencies.
   *
   * @param index
   *   The resolution index
   * @return
   *   The linting report
   */
  private def lintIntraTargetConflicts(index: ResolutionIndex): Result[Unit] = {
    val errors = index.unevictedArtifacts
      .flatMap { rdep =>
        rdep.config.targets.map(t => (t, rdep.dependency.module) -> rdep.dependency.version)
      }
      .groupBy(_._1)
      .toList
      .sortBy { case ((target, module), _) => target + module.repr }
      .collect {
        case ((target, module), conflicts) if conflicts.distinct.length > 1 =>
          val pos = index.thirdparty.depsByTargets
            .getOrElse(target, Nil)
            .headOption
            .map(_.organization.position)
            .getOrElse(NoPosition)
          val versions = conflicts.map(_._2).distinct.sorted
          new IntraTargetConflictDiagnostic(target, module, versions, pos)
      }
    Diagnostic.fromDiagnostics(errors) match {
      case Some(diagnostic) => ErrorResult(diagnostic)
      case None             => ValueResult(())
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

  private def withThreadPool[A](taskPar: Int, downloadPar: Int)(fn: CoursierThreadPools => A): A = {
    val threads = new CoursierThreadPools(taskPar, downloadPar)
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

  private def runResolutions(
      thirdparty: ThirdpartyConfig,
      previousIndex: Option[ResolutionIndex],
      dependencies: List[(DependencyConfig, Dependency)],
      cache: FileCache[Task]
  ): Result[List[DependencyResolution]] = {
    val progressBar = new ResolveProgressRenderer(
      dependencies.length,
      app.env.clock,
      isTesting = app.isTesting
    )

    val toResolve = for {
      (dep, cdep) <- dependencies
    } yield thirdparty.toResolve(
      dep,
      previousIndex,
      cache,
      progressBar,
      cdep,
      retryCount
    )

    for {
      resolves <- Result.fromResults(toResolve)
      resolutions <- Result.fromResults(
        runParallelTasks(resolves, progressBar, cache.ec)
      )
    } yield resolutions
  }

}

object ExportCommand {
  val default = new ExportCommand()
  implicit val parser: CommandParser[ExportCommand] =
    CommandParser.derive[ExportCommand](default)
}
