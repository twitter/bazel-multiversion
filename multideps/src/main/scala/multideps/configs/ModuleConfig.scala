package multideps.configs

import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import moped.json.JsonCodec
import moped.json.JsonDecoder
import moped.json.JsonString
import moped.json.ValueResult

final case class ModuleConfig(
    organization: JsonString = JsonString(""),
    moduleName: JsonString = JsonString("")
) {
  def repr = coursierModule.repr
  def coursierModule: Module =
    Module(
      Organization(organization.value),
      ModuleName(moduleName.value),
      Map.empty
    )
}

object ModuleConfig {
  val default: ModuleConfig = ModuleConfig()
  implicit private val jsonStringDecoder: JsonDecoder[JsonString] =
    JsonDecoder.fromJson[JsonString]("String") {
      case j: JsonString => ValueResult(j)
    }
  implicit val codec: JsonCodec[ModuleConfig] =
    moped.macros.deriveCodec(default)
}
