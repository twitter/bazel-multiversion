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
import moped.json.JsonObject
import moped.json.JsonString
import moped.json.ValueResult
import moped.macros.ClassShape

final case class DependencyConfig(
    organization: String = "",
    artifact: String = "",
    version: VersionsConfig = VersionsConfig(),
    crossLibrary: List[CrossLibraryConfig] = Nil,
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    exports: List[String] = Nil,
    forceVersions: ForceVersionsConfig = ForceVersionsConfig(),
    name: String = ""
) {
  val crossBuildName: String = if (name.isEmpty()) artifact else name
  def coursierModule(scalaVersion: VersionsConfig): Module = {
    val suffix = lang match {
      case JavaLanguagesConfig => ""
      case ScalaLanguagesConfig => "_" + scalaVersion.binaryVersion
      case ScalaCompilerLanguagesConfig => "_" + scalaVersion.default
    }
    Module(Organization(organization), ModuleName(artifact + suffix), Map.empty)
  }
  def coursierDependencies(scalaVersion: VersionsConfig): List[Dependency] =
    version.allVersions.map { version =>
      Dependency(coursierModule(scalaVersion), version)
    }
}

object DependencyConfig {
  private val Full: Regex = "(.+):::(.+):(.+)".r
  private val Half: Regex = "(.+)::(.+):(.+)".r
  private val Java: Regex = "(.+):(.+):(.+)".r
  def unapply(s: String): Option[DependencyConfig] =
    s match {
      case Full(org, artifact, version) =>
        Some(
          DependencyConfig(
            org,
            artifact,
            version = VersionsConfig(version),
            lang = ScalaCompilerLanguagesConfig
          )
        )
      case Half(org, artifact, version) =>
        Some(
          DependencyConfig(
            org,
            artifact,
            version = VersionsConfig(version),
            lang = ScalaLanguagesConfig
          )
        )
      case Java(org, artifact, version) =>
        Some(
          DependencyConfig(org, artifact, version = VersionsConfig(version))
        )
      case _ => None

    }
  val default: DependencyConfig = DependencyConfig()
  def automaticCodec(d: DependencyConfig): JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(d)
  val automaticCodec: JsonCodec[DependencyConfig] =
    automaticCodec(default)
  implicit val codec: JsonCodec[DependencyConfig] =
    new JsonCodec[DependencyConfig] {
      def decode(context: DecodingContext): DecodingResult[DependencyConfig] = {
        context.json match {
          case JsonString(DependencyConfig(dep)) => ValueResult(dep)
          case obj: JsonObject =>
            obj.value.get("dependency") match {
              case Some(JsonString(DependencyConfig(dep))) =>
                val newJson = JsonObject(
                  obj.members.filterNot(_.key.value == "dependency")
                )
                automaticCodec(dep).decode(context.withJson(newJson))
              case _ =>
                automaticCodec.decode(context)
            }
          case _ =>
            automaticCodec.decode(context)
        }
      }
      def encode(value: DependencyConfig): JsonElement =
        automaticCodec.encode(value)
      def shape: ClassShape = automaticCodec.shape
    }

}
