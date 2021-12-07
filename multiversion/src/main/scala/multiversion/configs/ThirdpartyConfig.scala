package multiversion.configs

import java.{ util => ju }

import scala.collection.JavaConverters._
import scala.collection.mutable

import coursier.Resolve
import coursier.cache.FileCache
import coursier.core.Dependency
import coursier.core.Module
import coursier.core.Reconciliation
import coursier.core.Resolution
import coursier.params.ResolutionParams
import coursier.util.ModuleMatchers
import coursier.util.Task
import moped.json.DecodingContext
import moped.json.ErrorResult
import moped.json.JsonCodec
import moped.json.Result
import moped.json.ValueResult
import moped.parsers.ConfigurationParser
import moped.parsers.JsonParser
import moped.parsers.YamlParser
import moped.reporters.Diagnostic
import moped.reporters.Input
import multiversion.diagnostics.MultidepsEnrichments._
import multiversion.loggers.ResolveProgressRenderer
import multiversion.outputs.DependencyResolution
import multiversion.outputs.ResolutionIndex
import multiversion.resolvers.DependencyId

final case class ThirdpartyConfig(
    repositories: List[RepositoryConfig] = List(),
    overrideTargets: List[OverrideTargetConfig] = List(),
    dependencies: List[DependencyConfig] = List(),
    scala: VersionsConfig = VersionsConfig()
) {
  val overrideTargetsMap: Map[Module, List[String]] =
    overrideTargets
      .groupBy(_.from)
      .mapValues(_.map(_.to))
      .toMap
  val overriddingTargets: Set[String] =
    overrideTargets.map(_.to).toSet
  val dependencies2: List[DependencyConfig] = {
    // populate classifiers to all versions to make them eviction proof
    def fillIn(p: (DependencyId, Vector[DependencyConfig])): Vector[DependencyConfig] = {
      val xs = p._2
      val versions = xs.map(_.version).distinct
      val classifiers = xs.map(_.classifier).distinct
      for {
        version <- versions
        classifier <- classifiers
      } yield {
        xs.find(c => c.version == version && c.classifier == classifier)
          .getOrElse {
            xs.find(c => c.version == version).get.copy(classifier = classifier)
          }
      }
    }
    dependencies.toVector
      .groupBy(_.id)
      .flatMap(fillIn)
      .toList
  }

  val depsByModule: Map[Module, List[DependencyConfig]] =
    dependencies2.groupBy(_.coursierModule(scala))
  def versionExtractorByModule(module: Module): String => String =
    depsByModule
      .getOrElse(module, Nil)
      .flatMap(_.versionExtractor)
      .headOption
      .getOrElse(identity)
  val depsByTargets: Map[String, List[DependencyConfig]] = {
    val map = mutable.Map.empty[String, mutable.ListBuffer[DependencyConfig]]
    for {
      dep <- dependencies2
      target <- dep.targets
    } {
      val buf = map.getOrElseUpdate(target, mutable.ListBuffer.empty)
      buf += dep
    }
    map.mapValues(_.toList).toMap
  }
  def coursierDeps: List[(DependencyConfig, Dependency)] =
    dependencies2
      .flatMap(d => d.coursierDependencies(scala).map(cd => d -> cd))
      .distinctBy { case (d, cd) => (d.targets.sorted, cd) }
  def relaxedForAllModules: Seq[(ModuleMatchers, Reconciliation)] =
    Vector((ModuleMatchers.all, Reconciliation.Relaxed))

  /** Collect all the root dependencies to resolve together. */
  def rootDependencies(
      root: DependencyConfig,
      previousIndex: Option[ResolutionIndex]
  ): Seq[Dependency] = {
    val seen = mutable.Set.empty[String]
    val roots = mutable.Buffer.empty[Dependency]
    val queue = mutable.Queue(root)
    while (queue.nonEmpty) {
      val current = queue.dequeue()
      roots += current.toCoursierDependency(scala)
      current.dependencies.foreach { dep =>
        if (seen.add(dep)) {
          depsByTargets
            .getOrElse(dep, Nil)
            .foreach(d => queue.enqueue(d))
        }
      }
    }
    // Use a previous index to explicitly resolve selected versions of transitive
    // dependencies. This is necessary to pick up the transitive dependencies of the selected
    // versions of our dependencies.
    // For instance, if A depends on D v1, but D v1 is evicted in favor of D v2 and D v2 has a
    // dependency on E, which D v1 didn't have, then resolving A along with D v2 will ensure
    // that E will end up on the classpath of A.
    previousIndex.foreach { index =>
      index.dependencies
        .getOrElse(root.id, Nil)
        .foreach { d =>
          val reconciledVersion = index.reconciledVersion(d)
          if (reconciledVersion != d.version) {
            roots += d.withVersion(reconciledVersion)
          }
        }
    }
    roots
  }
  def toResolve(
      dep: DependencyConfig,
      previousIndex: Option[ResolutionIndex],
      cache: FileCache[Task],
      progressBar: ResolveProgressRenderer,
      cdep: Dependency,
      retryCount: Int
  ): Result[Task[Result[DependencyResolution]]] =
    Result.fromResults(decodeForceVersions(dep)).map { decodedForceVersions =>
      val allDependencies = for {
        d <- rootDependencies(dep, previousIndex)
      } yield d.withExclusions(d.exclusions)
      val repos = repositories.flatMap(_.coursierRepository)
      val resolve =
        Resolve(cache.withLogger(progressBar.loggers.newCacheLogger(cdep)))
          .addDependencies(allDependencies: _*)
          .withResolutionParams(
            ResolutionParams()
              .addForceVersion(decodedForceVersions: _*)
              .withReconciliation(relaxedForAllModules)
          )
          .withRepositories(
            if (repos.isEmpty) Resolve.defaultRepositories else repos
          )

      def retry(m: Int) =
        Task.tailRecM[Int, Resolution](m) { (n: Int) =>
          if (n <= 0) resolve.io.map(Right(_))
          else
            resolve.io.attempt map {
              case Right(r) => Right(r)
              case Left(e) if e.getMessage.contains("concurrent download") =>
                Thread.sleep(100)
                Left(n - 1)
              case Left(e) => throw e
            }
        }
      retry(retryCount).map(r => DependencyResolution(dep, r)).toResult
    }

  private type ForceVersionResult =
    mutable.Buffer[Result[(Module, String)]]
  private val forceVersionsByTarget: collection.Map[String, ForceVersionResult] = {
    val map = new ju.HashMap[String, ForceVersionResult]().asScala
    for {
      dep <- dependencies2.iterator
      fv = fromForceVersions(dep)
      target <- dep.targets
    } {
      val buf = map.getOrElseUpdate(target, mutable.ListBuffer.empty)
      buf ++= fv
    }
    map
  }
  private def fromForceVersions(
      dep: DependencyConfig
  ): ForceVersionResult = {
    dep.forceVersions.overrides.iterator.flatMap { case (module, version) =>
      depsByModule.get(module.coursierModule) match {
        case None =>
          List(
            ErrorResult(
              Diagnostic.error(
                s"version '$version' not found",
                module.name.position
              )
            )
          )
        case Some(depsConfigs) =>
          depsConfigs.map { depsConfig =>
            depsConfig.getVersion(version) match {
              case Some(forcedVersion) =>
                ValueResult(
                  depsConfig.coursierModule(scala) -> forcedVersion
                )
              case None =>
                ErrorResult(
                  Diagnostic.error(
                    s"version '$version' not found",
                    module.name.position
                  )
                )
            }
          }
      }
    }.toBuffer
  }

  private val decodeVersionsCache =
    new ju.IdentityHashMap[DependencyConfig, List[
      Result[(Module, String)]
    ]]().asScala
  private def decodeForceVersions(
      dep: DependencyConfig
  ): List[Result[(Module, String)]] = {
    decodeVersionsCache.getOrElseUpdate(
      dep, {
        val buf = mutable.ListBuffer.empty[Result[(Module, String)]]
        if (dep.force) {
          buf += ValueResult(dep.coursierModule(scala) -> dep.version)
        }
        buf ++= fromForceVersions(dep)
        for {
          dep <- dependencies2
          target <- dep.targets
        } {
          buf ++= forceVersionsByTarget(target)
        }
        for {
          transitiveLabel <- dep.dependencies
          transitive <- depsByTargets.getOrElse(transitiveLabel, Nil)
        } {
          buf ++= decodeForceVersions(transitive)
        }
        buf.toList
      }
    )
  }
}

object ThirdpartyConfig {
  def parseYaml(input: Input): Result[ThirdpartyConfig] = {
    parse(input, YamlParser)
  }
  def parseJson(input: Input): Result[ThirdpartyConfig] = {
    parse(input, JsonParser)
  }
  private def parse(
      input: Input,
      parser: ConfigurationParser
  ): Result[ThirdpartyConfig] = {
    parser
      .parse(input)
      .flatMap(json => codec.decode(DecodingContext(json).withFatalUnknownFields(true)))
  }
  val default: ThirdpartyConfig = ThirdpartyConfig()
  implicit val codec: JsonCodec[ThirdpartyConfig] =
    moped.macros.deriveCodec(default)
}
