package multiversion.resolvers

final case class DependencyId(
    organization: String,
    name: String,
    version: String,
    classifier: Option[String]
)
