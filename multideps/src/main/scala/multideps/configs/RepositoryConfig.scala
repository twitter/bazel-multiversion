package multideps.configs

import coursier.core.Repository
import coursier.maven.MavenRepository

final case class RepositoryConfig(
    name: String = "",
    url: String = ""
) {
  def coursierRepository: Option[Repository] =
    if (name.nonEmpty) Some(MavenRepository(url))
    else None
}

object RepositoryConfig {
  val default = RepositoryConfig()
  implicit val codec = moped.macros.deriveCodec(default)
}
