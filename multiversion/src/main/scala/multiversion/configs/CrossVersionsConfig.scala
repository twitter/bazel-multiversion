package multiversion.configs

import moped.json.JsonCodec
import moped.json.JsonString
import multiversion.configs.MultidepsJsonDecoders.jsonStringDecoder

final case class CrossVersionsConfig(
    name: JsonString = JsonString(""),
    version: JsonString = JsonString(""),
    forceVersions: ForceVersionsConfig = ForceVersionsConfig()
)
object CrossVersionsConfig {
  val default: CrossVersionsConfig = CrossVersionsConfig()
  implicit val codec: JsonCodec[CrossVersionsConfig] =
    moped.macros.deriveCodec(default)
}
