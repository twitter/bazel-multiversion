package multiversion.configs

import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import moped.json.JsonCodec
import moped.json.JsonString
import multiversion.configs.MultidepsJsonDecoders.jsonStringDecoder
import net.starlark.java.eval.StarlarkValue

final case class ModuleConfig(
    organization: JsonString = JsonString(""),
    name: JsonString = JsonString("")
) extends StarlarkValue {
  def repr = coursierModule.repr
  def coursierModule: Module =
    Module(
      Organization(organization.value),
      ModuleName(name.value),
      Map.empty
    )
}

object ModuleConfig {
  def apply(organization: String, moduleName: String): ModuleConfig =
    ModuleConfig(JsonString(organization), JsonString(moduleName))
  def apply(module: Module): ModuleConfig =
    ModuleConfig(module.organization.value, module.name.value)
  val default: ModuleConfig = ModuleConfig()
  implicit val codec: JsonCodec[ModuleConfig] =
    moped.macros.deriveCodec(default)
}
