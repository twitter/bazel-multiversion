package multideps.configs

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import moped.json.JsonCodec

final case class DependencyConfig(
    organization: String = "",
    artifact: String = "",
    name: String = "",
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    exports: List[String] = Nil,
    version: VersionsConfig = VersionsConfig()
) {
  val crossBuildName: String = if (name.isEmpty()) artifact else name
  val coursierModule: Module =
    Module(Organization(organization), ModuleName(artifact), Map.empty)
  val coursierDependencies: List[Dependency] =
    version.allVersions.map { version =>
      Dependency(coursierModule, version)
    }
}

object DependencyConfig {
  val default: DependencyConfig = DependencyConfig()
  implicit val codec: JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(default)
}
