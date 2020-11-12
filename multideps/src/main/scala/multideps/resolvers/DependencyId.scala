package multideps.resolvers

final case class DependencyId(
    organization: String,
    name: String,
    version: String
)
