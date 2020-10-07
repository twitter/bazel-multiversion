package multideps.configs

import coursier.core.Module
import moped.json.JsonDecoder
import moped.json.{DecodingContext, DecodingResult}
import moped.json.JsonPrimitive
import moped.json.JsonArray
import moped.json.JsonObject
import moped.json.JsonMember
import moped.json.JsonString
import scala.util.matching.Regex
import moped.json.ValueResult
import os.copy.over
import coursier.core.Organization
import coursier.core.ModuleName
import moped.json.JsonEncoder
import moped.json.JsonElement

final case class ForceVersionsConfig(
    overrides: Map[Module, String] = Map.empty
)

object ForceVersionsConfig {
  val default = ModuleConfig()
  implicit val encoder =
    JsonEncoder
      .iterableJsonEncoder[JsonString, List]
      .contramap[ForceVersionsConfig](_.overrides.toList.map {
        case (m, v) => JsonString(m.repr + ":" + v)
      })
  private val OrgArtifact: Regex = "([^:]):([^:]+)".r
  implicit val decoder = JsonDecoder.fromJson[ForceVersionsConfig]("Object") {
    case obj: JsonObject =>
      val overrides = obj.members.map {
        case JsonMember(
              JsonString(OrgArtifact(org, artifact)),
              JsonString(version)
            ) =>
          Module(Organization(org), ModuleName(artifact), Map.empty) -> version
      }
      ValueResult(ForceVersionsConfig(overrides.toMap))
  }

}
