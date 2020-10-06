package multideps.config

import moped.json.JsonDecoder
import moped.json.JsonString
import moped.json.ValueResult
import moped.json.JsonArray
import moped.json.JsonObject
import moped.json.ErrorResult
import moped.reporters.Diagnostic
import moped.json.DecodingContext
import moped.json.DecodingResult
import moped.json.SelectMemberCursor

final case class VersionsConfig(
    default: String = "",
    extras: Map[String, String] = Map.empty
)

object VersionsConfig {
  implicit lazy val default = VersionsConfig()
  implicit lazy val encoder = moped.macros.deriveEncoder[VersionsConfig]
  implicit lazy val decoder = new JsonDecoder[VersionsConfig] {
    def decode(context: DecodingContext): DecodingResult[VersionsConfig] = {
      context.json match {
        case JsonString(value) => ValueResult(VersionsConfig(value))
        case obj @ JsonObject(members) =>
          obj.value.get("default") match {
            case Some(JsonString(default)) =>
              val results = (obj.value - "default").map {
                case (label, JsonString(version)) =>
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
