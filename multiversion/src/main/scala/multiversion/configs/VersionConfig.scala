package multiversion.configs

import coursier.core.Version

final case class VersionConfig(
    original: String,
    extracted: Version,
    force: Boolean
)
