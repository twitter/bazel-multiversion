package multideps.configs

import coursier.core.Repository
import coursier.maven.MavenRepository
import moped.json.JsonCodec
import moped.json.{DecodingContext, DecodingResult}
import moped.json.JsonElement
import moped.macros.ClassShape
import moped.json.JsonString
import moped.json.ValueResult

final case class RepositoryConfig(
    name: String = "",
    url: String = ""
) {
  def coursierRepository: Option[Repository] =
    if (url.nonEmpty) Some(MavenRepository(url))
    else None
}

object RepositoryConfig {
  val default: RepositoryConfig = RepositoryConfig()
  val automaticCodec: JsonCodec[RepositoryConfig] =
    moped.macros.deriveCodec(default)
  implicit val codec = new JsonCodec[RepositoryConfig] {
    def decode(context: DecodingContext): DecodingResult[RepositoryConfig] =
      context.json match {
        case JsonString(value) => ValueResult(RepositoryConfig(url = value))
        case _ => automaticCodec.decode(context)
      }
    def encode(value: RepositoryConfig): JsonElement =
      automaticCodec.encode(value)
    def shape: ClassShape = automaticCodec.shape
  }
}
