package multideps.config

import moped.json.JsonDecoder
import moped.json.JsonString
import moped.json.ValueResult
import moped.json.JsonEncoder

sealed abstract class LanguagesConfig(val value: String)
    extends Product
    with Serializable
case object JavaLanguagesConfig extends LanguagesConfig("java")
case object ScalaLanguagesConfig extends LanguagesConfig("scala")

object LanguagesConfig {
  implicit val decoder = JsonDecoder.fromJson("java | scala") {
    case JsonString("java") => ValueResult(JavaLanguagesConfig)
    case JsonString("scala") => ValueResult(ScalaLanguagesConfig)
  }
  implicit val encoder =
    JsonEncoder.stringJsonEncoder.contramap[LanguagesConfig](_.value)
}
