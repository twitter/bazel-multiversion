package multideps.config

import moped.json.JsonCodec

final case class WorkspaceConfig(
    dependencies: List[DependencyConfig] = List()
)

object WorkspaceConfig {
  val default = WorkspaceConfig()
  implicit val codec: JsonCodec[WorkspaceConfig] =
    moped.macros.deriveCodec(default)
}
