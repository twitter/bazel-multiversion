package multideps.configs

import moped.json.JsonCodec
import coursier.core.Dependency
import coursier.core.Module
import coursier.core.Organization
import coursier.core.ModuleName

final case class DependencyConfig(
    organization: String = "",
    artifact: String = "",
    name: String = "",
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    exports: List[String] = Nil,
    version: VersionsConfig = VersionsConfig()
) {
  val crossBuildName = if (name.isEmpty()) artifact else name
  val coursierModule =
    Module(Organization(organization), ModuleName(artifact), Map.empty)
  val coursierDependencies =
    version.allVersions.map { version =>
      Dependency(coursierModule, version)
    }
}

object DependencyConfig {
  val default: DependencyConfig = DependencyConfig()
  implicit val codec: JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(default)
}
