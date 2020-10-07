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
    name: String = "",
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    exports: List[String] = Nil,
    version: VersionsConfig = VersionsConfig()
) {
  val crossBuildName: String = if (name.isEmpty()) artifact else name
  val coursierModule: Module =
    Module(Organization(organization), ModuleName(artifact), Map.empty)
  val coursierDependencies: List[Dependency] =
    version.allVersions.map { version =>
      Dependency(coursierModule, version)
    }
}

object DependencyConfig {
  val default: DependencyConfig = DependencyConfig()
  val automaticCodec: JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(default)
  implicit val codec: JsonCodec[DependencyConfig] = new JsonCodec[DependencyConfig] {
    val Java: Regex = "(.+):(.+):(.+)".r
    val Half: Regex = "(.+)::(.+):(.+)".r
    val Full: Regex = "(.+):::(.+):(.+)".r
    def decode(context: DecodingContext): DecodingResult[DependencyConfig] = {
      context.json match {
        case JsonString(Java(org, artifact, version)) =>
          ValueResult(
            DependencyConfig(org, artifact, version)
          )
        case JsonString(Half(org, artifact, version)) =>
          ValueResult(
            DependencyConfig(
              org,
              artifact,
              version,
              lang = ScalaLanguagesConfig
            )
          )
        case JsonString(Full(org, artifact, version)) =>
          ValueResult(
            DependencyConfig(
              org,
              artifact,
              version,
              lang = ScalaCompilerLanguagesConfig
            )
          )
        case _ => automaticCodec.decode(context)
      }
    }
    def encode(value: DependencyConfig): JsonElement =
      automaticCodec.encode(value)
    def shape: ClassShape = automaticCodec.shape
  }

}
