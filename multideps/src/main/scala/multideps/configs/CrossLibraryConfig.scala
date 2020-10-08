package multideps.configs

import multideps.configs.MultidepsJsonDecoders.jsonStringDecoder

import moped.json.JsonCodec
import moped.json.JsonString

final case class CrossLibraryConfig(
    name: JsonString = JsonString(""),
    version: JsonString = JsonString(""),
    forceVersions: ForceVersionsConfig = ForceVersionsConfig()
)
object CrossLibraryConfig {
  val default: CrossLibraryConfig = CrossLibraryConfig()
  implicit val codec: JsonCodec[CrossLibraryConfig] =
    moped.macros.deriveCodec(default)
}
