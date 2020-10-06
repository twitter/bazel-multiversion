package multideps.config

import moped.json.JsonCodec

final case class DependenciesConfig(
    value: Seq[DependencyConfig] = List()
)

object DependenciesConfig {
  val default = DependenciesConfig()
  implicit val codec: JsonCodec[DependenciesConfig] =
    moped.macros.deriveCodec(default)
}
