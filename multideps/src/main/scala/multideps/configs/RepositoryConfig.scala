package multideps.configs

import coursier.core.Repository
import coursier.maven.MavenRepository
import moped.json.JsonCodec

final case class RepositoryConfig(
    name: String = "",
    url: String = ""
) {
  def coursierRepository: Option[Repository] =
    if (name.nonEmpty) Some(MavenRepository(url))
    else None
}

object RepositoryConfig {
  val default: RepositoryConfig = RepositoryConfig()
  implicit val codec: JsonCodec[RepositoryConfig] =
    moped.macros.deriveCodec(default)
}
