package multiversion.configs

import java.{util => ju}

import scala.collection.JavaConverters._
import scala.collection.mutable

import coursier.Resolve
import coursier.cache.FileCache
import coursier.core.Dependency
import coursier.core.Module
import coursier.params.ResolutionParams
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

final case class ThirdpartyConfig(
    repositories: List[RepositoryConfig] = List(),
    overrideTargets: List[OverrideTargetConfig] = List(),
    dependencies: List[DependencyConfig] = List(),
    scala: VersionsConfig = VersionsConfig()
) {
  val depsByModule: Map[Module, List[DependencyConfig]] =
    dependencies.groupBy(_.coursierModule(scala))
  val depsByTargets: Map[String, List[DependencyConfig]] = {
    val map = mutable.Map.empty[String, mutable.ListBuffer[DependencyConfig]]
    for {
      dep <- dependencies
      target <- dep.targets
    } {
      val buf = map.getOrElseUpdate(target, mutable.ListBuffer.empty)
      buf += dep
    }
    map.mapValues(_.toList).toMap
  }
  def coursierDeps: List[(DependencyConfig, Dependency)] =
    dependencies
      .flatMap(d => d.coursierDependencies(scala).map(cd => d -> cd))
      .distinctBy(_._2)
  def toResolve(
      dep: DependencyConfig,
      cache: FileCache[Task],
      progressBar: ResolveProgressRenderer,
      cdep: Dependency
  ): Result[Task[Result[DependencyResolution]]] =
    Result.fromResults(decodeForceVersions(dep)).map { forceVersions =>
      val repos = repositories.flatMap(_.coursierRepository)
      val resolve =
        Resolve(cache.withLogger(progressBar.loggers.newCacheLogger(cdep)))
          .addDependencies(cdep)
          .withResolutionParams(
            ResolutionParams().addForceVersion(forceVersions: _*)
          )
          .withRepositories(
            if (repos.isEmpty) Resolve.defaultRepositories else repos
          )
      resolve.io.map(r => DependencyResolution(dep, r)).toResult
    }

  private type ForceVersionResult =
    mutable.Buffer[Result[(Module, String)]]
  private val forceVersionsByTarget
      : collection.Map[String, ForceVersionResult] = {
    val map = new ju.HashMap[String, ForceVersionResult]().asScala
    for {
      dep <- dependencies.iterator
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
    dep.forceVersions.overrides.iterator.flatMap {
      case (module, version) =>
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
          dep <- dependencies
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
      .flatMap(json =>
        codec.decode(DecodingContext(json).withFatalUnknownFields(true))
      )
  }
  val default: ThirdpartyConfig = ThirdpartyConfig()
  implicit val codec: JsonCodec[ThirdpartyConfig] =
    moped.macros.deriveCodec(default)
}
