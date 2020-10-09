package multideps.configs

final case class OverrideTargetConfig(
    from: String = "",
    to: String = ""
)

object OverrideTargetConfig {
  val default = OverrideTargetConfig()
  implicit val codec = moped.macros.deriveCodec(default)
}
