package multideps.config

import moped.json.JsonCodec
import moped.reporters.Input
import moped.json.DecodingResult
import moped.parsers.YamlParser
import moped.json.DecodingContext

final case class WorkspaceConfig(
    dependencies: List[DependencyConfig] = List(),
    scala: VersionsConfig = VersionsConfig()
)

object WorkspaceConfig {
  def parse(input: Input): DecodingResult[WorkspaceConfig] = {
    YamlParser.parse(input).flatMap(json => codec.decode(DecodingContext(json)))
  }
  val default = WorkspaceConfig()
  implicit val codec: JsonCodec[WorkspaceConfig] =
    moped.macros.deriveCodec(default)
}
