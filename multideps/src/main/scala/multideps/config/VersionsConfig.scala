package multideps.config

import moped.json.JsonDecoder
import moped.json.JsonString
import moped.json.ValueResult
import moped.json.JsonObject
import moped.json.ErrorResult
import moped.reporters.Diagnostic
import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.SelectMemberCursor

final case class VersionsConfig(
    default: JsonString = JsonString(""),
    extras: Map[String, JsonString] = Map.empty
)

object VersionsConfig {
  def apply(default: String): VersionsConfig =
    VersionsConfig(JsonString(default))
  def apply(default: String, extras: Map[String, String]): VersionsConfig =
    VersionsConfig(
      JsonString(default),
      extras.mapValues(JsonString(_)).toMap
    )
  implicit lazy val default = VersionsConfig()
  implicit lazy val encoder = moped.macros.deriveEncoder[VersionsConfig]
  implicit lazy val decoder = new JsonDecoder[VersionsConfig] {
    def decode(context: DecodingContext): DecodingResult[VersionsConfig] = {
      context.json match {
        case s: JsonString => ValueResult(VersionsConfig(s))
        case obj @ JsonObject(members) =>
          obj.value.get("default") match {
            case Some(default: JsonString) =>
              val results = (obj.value - "default").map {
                case (label, version: JsonString) =>
                  ValueResult(label -> version)
                case (label, other) =>
                  ErrorResult(Diagnostic.typeMismatch("JsonString", context))
              }
              DecodingResult.fromResults(results).map { extras =>
                VersionsConfig(default, extras.toMap)
              }
            case Some(other) =>
              ErrorResult(
                Diagnostic.typeMismatch(
                  "JsonString",
                  context.withCursor(
                    SelectMemberCursor("default").withParent(context.cursor)
                  )
                )
              )
            case None =>
              ErrorResult(
                Diagnostic.error(
                  "missing 'default' version",
                  context.json.position
                )
              )
          }
        case other =>
          ErrorResult(Diagnostic.typeMismatch("JsonString", context))
      }
    }
  }
}
