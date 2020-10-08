package multideps.configs

import coursier.core.Dependency
import coursier.core.Module
import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.JsonCodec
import moped.parsers.YamlParser
import moped.reporters.Input

final case class ThirdpartyConfig(
    repositories: List[RepositoryConfig] = List(),
    dependencies: List[DependencyConfig] = List(),
    scala: VersionsConfig = VersionsConfig()
) {
  val depsByModule: Map[Module, DependencyConfig] =
    dependencies.groupBy(_.coursierModule(scala)).collect {
      case (m, d :: _) => m -> d
    }
  def coursierDependencies: List[Dependency] =
    dependencies.flatMap(_.coursierDependencies(scala))
}

object ThirdpartyConfig {
  def parse(input: Input): DecodingResult[ThirdpartyConfig] = {
    YamlParser
      .parse(input)
      .flatMap(json =>
        codec.decode(DecodingContext(json).withFatalUnknownFields(true))
      )
  }
  val default: ThirdpartyConfig = ThirdpartyConfig()
  implicit val codec: JsonCodec[ThirdpartyConfig] =
    moped.macros.deriveCodec(default)
}
