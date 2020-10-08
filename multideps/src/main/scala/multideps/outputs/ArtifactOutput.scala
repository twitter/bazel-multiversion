package multideps.configs

import coursier.core.Module
import coursier.util.Artifact

final case class ArtifactOutput(
    label: String,
    urls: List[String],
    sha256: List[String],
    sourcesUrls: List[String],
    sourcesSha256: String,
    module: Module,
    artifacts: List[Artifact]
) {
  require(artifacts.nonEmpty)
  def name = ""
  def httpFile: String = ""
  def genrule: String = ""
  def javaImport: String = ""
}
