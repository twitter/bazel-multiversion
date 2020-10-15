package multideps.configs

import java.{util => ju}

import scala.collection.JavaConverters._
import scala.collection.mutable

import multideps.diagnostics.MultidepsEnrichments.XtensionList
import multideps.loggers.ResolveProgressRenderer

import coursier.Resolve
import coursier.cache.FileCache
import coursier.core.Dependency
import coursier.core.Module
import coursier.params.ResolutionParams
import coursier.util.Task
import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.ErrorResult
import moped.json.JsonCodec
import moped.json.ValueResult
import moped.parsers.ConfigurationParser
import moped.parsers.JsonParser
import moped.parsers.YamlParser
import moped.reporters.Diagnostic
import moped.reporters.Input
import coursier.Repositories

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
  ): DecodingResult[Resolve[Task]] =
    DecodingResult.fromResults(decodeForceVersions(dep)).map { forceVersions =>
      val repos = repositories.flatMap(_.coursierRepository)
      Resolve(cache.withLogger(progressBar.loggers.newCacheLogger(cdep)))
        .addDependencies(cdep)
        .withResolutionParams(
          ResolutionParams().addForceVersion(forceVersions: _*)
        )
        .withRepositories(
          if (repos.isEmpty) Resolve.defaultRepositories else repos
        )

    }

  private type ForceVersionResult =
    mutable.Buffer[DecodingResult[(Module, String)]]
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
                  // TODO: report "did you mean?"
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
      DecodingResult[(Module, String)]
    ]]().asScala
  private def decodeForceVersions(
      dep: DependencyConfig
  ): List[DecodingResult[(Module, String)]] = {
    decodeVersionsCache.getOrElseUpdate(
      dep, {
        val buf = mutable.ListBuffer.empty[DecodingResult[(Module, String)]]
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
  def parseYaml(input: Input): DecodingResult[ThirdpartyConfig] = {
    parse(input, YamlParser)
  }
  def parseJson(input: Input): DecodingResult[ThirdpartyConfig] = {
    parse(input, JsonParser)
  }
  private def parse(
      input: Input,
      parser: ConfigurationParser
  ): DecodingResult[ThirdpartyConfig] = {
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
