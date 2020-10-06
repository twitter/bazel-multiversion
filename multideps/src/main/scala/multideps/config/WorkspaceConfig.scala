package multideps.config

import moped.json.JsonCodec

final case class WorkspaceConfig(
    dependencies: DependenciesConfig = DependenciesConfig()
)

object WorkspaceConfig {
  val default = WorkspaceConfig()
  implicit val codec: JsonCodec[WorkspaceConfig] =
    moped.macros.deriveCodec(default)
}
