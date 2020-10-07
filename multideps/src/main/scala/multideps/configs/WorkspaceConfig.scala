package multideps.configs

import coursier.core.Dependency
import coursier.core.Module
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
  val depsByModule: Map[Module, DependencyConfig] =
    dependencies.groupBy(_.coursierModule(scala)).collect {
      case (m, d :: _) => m -> d
    }
  def coursierDependencies: List[Dependency] =
    dependencies.flatMap(_.coursierDependencies(scala))
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
