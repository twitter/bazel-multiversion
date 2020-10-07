package multideps.config

import moped.json.JsonCodec

final case class DependencyConfig(
    organization: String = "",
    artifact: String = "",
    name: String = "",
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    exports: List[String] = Nil,
    version: VersionsConfig = VersionsConfig()
) {
  def crossBuildName = if (name.isEmpty()) artifact else name
}

object DependencyConfig {
  val default: DependencyConfig = DependencyConfig()
  implicit val codec: JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(default)
}
