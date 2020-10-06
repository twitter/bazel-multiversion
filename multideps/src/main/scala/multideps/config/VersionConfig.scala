package multideps.config

import moped.json.JsonCodec

final case class VersionConfig(
    label: String = "",
    version: String = ""
)

object VersionConfig {
  val default = VersionConfig()
  implicit val codec: JsonCodec[VersionConfig] =
    moped.macros.deriveCodec(default)
}
