package multiversion.configs

import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import moped.json.JsonCodec
import moped.macros.ClassShaper
final case class OverrideTargetConfig(
    from: Module = Module(Organization(""), ModuleName(""), Map.empty),
    to: String = ""
)

object OverrideTargetConfig {
  private implicit val moduleCodec: JsonCodec[Module] = JsonCodec
    .encoderDecoderJsonCodec[String](ClassShaper.empty, implicitly, implicitly)
    .bimap(
      _.repr,
      toModule
    )

  private def toModule(str: String): Module = {
    val colon = str.indexOf(':')
    if (colon >= 0) {
      val org = Organization(str.substring(0, colon))
      val name = ModuleName(str.substring(colon + 1))
      Module(org, name, Map.empty)
    } else {
      throw new IllegalArgumentException(s"Not a module: '$str'")
    }
  }

  val default: OverrideTargetConfig = OverrideTargetConfig()
  implicit val codec: JsonCodec[OverrideTargetConfig] =
    moped.macros.deriveCodec(default)
}
