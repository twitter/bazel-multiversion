package multideps.configs

import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.JsonCodec
import moped.parsers.YamlParser
import moped.reporters.Input

final case class WorkspaceConfig(
    repositories: List[RepositoryConfig] = List(),
    dependencies: List[DependencyConfig] = List(),
    scala: VersionsConfig = VersionsConfig()
) {
  def scalaFullVersion = scala.default.value
  def scalaBinaryVersion = scala.default.value.split('.').take(2).mkString(".")
  def coursierDependencies =
    dependencies.flatMap(
      _.coursierDependencies(scalaBinaryVersion, scalaFullVersion)
    )
}

object WorkspaceConfig {
  def parse(input: Input): DecodingResult[WorkspaceConfig] = {
    YamlParser
      .parse(input)
      .flatMap(json =>
        codec.decode(DecodingContext(json).withFatalUnknownFields(true))
      )
  }
  val default: WorkspaceConfig = WorkspaceConfig()
  implicit val codec: JsonCodec[WorkspaceConfig] =
    moped.macros.deriveCodec(default)
}
