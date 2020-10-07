package multideps.configs

import coursier.core.Module
import coursier.util.Artifact

final case class ArtifactOutput(
    module: Module,
    artifacts: List[Artifact]
) {
  require(artifacts.nonEmpty)
}
