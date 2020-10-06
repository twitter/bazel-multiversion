package multideps.config

import moped.json.JsonCodec

final case class DependencyConfig(
    organization: String = "",
    artifact: String = "",
    modules: List[String] = Nil,
    lang: String = "",
    exports: List[String] = Nil,
    version: List[VersionConfig] = Nil
) {
  def defaultVersion: String = version.find(_.label == "default").get.version
}

object DependencyConfig {
  val default = DependencyConfig()
  implicit val codec: JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(default)
}
