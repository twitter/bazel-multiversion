package multideps.config

import moped.json.JsonCodec

final case class DependencyConfig(
    organization: String = "",
    artifact: String = "",
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    exports: List[String] = Nil,
    version: VersionsConfig = VersionsConfig()
)

object DependencyConfig {
  val default = DependencyConfig()
  implicit val codec: JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(default)
}
