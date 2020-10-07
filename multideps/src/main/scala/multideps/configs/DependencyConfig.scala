package multideps.configs

import scala.util.matching.Regex

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.JsonCodec
import moped.json.JsonElement
import moped.json.JsonString
import moped.json.ValueResult
import moped.macros.ClassShape

final case class DependencyConfig(
    organization: String = "",
    artifact: String = "",
    version: VersionsConfig = VersionsConfig(),
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    exports: List[String] = Nil,
    name: String = ""
) {
  val crossBuildName: String = if (name.isEmpty()) artifact else name
  def coursierModule(
      scalaBinaryVersion: String,
      scalaFullVersion: String
  ): Module = {
    val suffix = lang match {
      case JavaLanguagesConfig => ""
      case ScalaLanguagesConfig => "_" + scalaBinaryVersion
      case ScalaCompilerLanguagesConfig => "_" + scalaFullVersion
    }
    Module(Organization(organization), ModuleName(artifact + suffix), Map.empty)
  }
  def coursierDependencies(
      scalaBinaryVersion: String,
      scalaFullVersion: String
  ): List[Dependency] =
    version.allVersions.map { version =>
      Dependency(coursierModule(scalaBinaryVersion, scalaFullVersion), version)
    }
}

object DependencyConfig {
  val default: DependencyConfig = DependencyConfig()
  val automaticCodec: JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(default)
  implicit val codec: JsonCodec[DependencyConfig] =
    new JsonCodec[DependencyConfig] {
      val Full: Regex = "(.+):::(.+):(.+)".r
      val Half: Regex = "(.+)::(.+):(.+)".r
      val Java: Regex = "(.+):(.+):(.+)".r
      def decode(context: DecodingContext): DecodingResult[DependencyConfig] = {
        context.json match {
          case JsonString(Full(org, artifact, version)) =>
            ValueResult(
              DependencyConfig(
                org,
                artifact,
                version = VersionsConfig(version),
                lang = ScalaCompilerLanguagesConfig
              )
            )
          case JsonString(Half(org, artifact, version)) =>
            ValueResult(
              DependencyConfig(
                org,
                artifact,
                version = VersionsConfig(version),
                lang = ScalaLanguagesConfig
              )
            )
          case JsonString(Java(org, artifact, version)) =>
            ValueResult(
              DependencyConfig(org, artifact, version = VersionsConfig(version))
            )
          case _ => automaticCodec.decode(context)
        }
      }
      def encode(value: DependencyConfig): JsonElement =
        automaticCodec.encode(value)
      def shape: ClassShape = automaticCodec.shape
    }

}
