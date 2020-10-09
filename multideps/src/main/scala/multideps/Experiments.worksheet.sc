import coursier.Fetch
import coursier.core.Publication
import coursier.core.Organization
import coursier.core.Configuration
import coursier.core.ModuleName
import coursier.core.Module
import coursier.core.Dependency
import coursier.cache.FileCache
import coursier.core.Version

val dep = Dependency(
  module = Module(
    Organization("io.netty"),
    ModuleName("netty-transport-native-epoll"),
    Map.empty
  ),
  "4.1.51.Final",
  configuration = Configuration("linux-x84_64"),
  exclusions = Set.empty,
  publication = Publication.empty,
  optional = false,
  transitive = true
)

Fetch().addDependencies(dep).run
