package multideps.configs

import coursier.core.Module
import coursier.core.Organization
import coursier.core.ModuleName

final case class ModuleConfig(
    organization: String = "",
    moduleName: String = ""
) {
  def coursierModule =
    Module(Organization(organization), ModuleName(moduleName), Map.empty)
}

object ModuleConfig {
  val default = ModuleConfig()
  implicit val codec = moped.macros.deriveCodec(default)
}
