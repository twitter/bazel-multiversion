package multideps.configs

import multideps.diagnostics.MultidepsEnrichments.XtensionDependency
import coursier.util.Artifact
import coursier.core.Dependency

final case class ArtifactOutput(
    dependency: Dependency,
    artifact: Artifact,
    artifactSha256: String,
    sourcesArtifact: Option[Artifact] = None,
    sourcesArtifactSha256: Option[String] = None
) {
  // val label =
  //   s"${dependency.module.organization.value}/${dependency.module.name.value}/${dependency.version}"
  // Bazel workspace names may contain only A-Z, a-z, 0-9, '-', '_' and '.'
  val label = dependency.repr.replaceAll("[^a-zA-Z0-9-\\.]", "_")
  def repr =
    s"""|Artifact(
        |  dep = "${label}",
        |  url = "${artifact.url}",
        |  sha = "${artifactSha256}"
        |)""".stripMargin
  val org = dependency.module.organization.value
  val moduleName = dependency.module.name.value
  val version = dependency.version
  val mavenLabel = s"@maven//:${org}/${moduleName}/${version}"
  // require(artifactsnonEmpty)
  // def label: String = ""
  // def name = ""
  def httpFile: String =
    s"""http_file(name = "${label}", urls = ["${artifact.url}"], sha256 = "$artifactSha256")"""
  def genrule: String =
    s"""genrule(name = "${label}_extension", srcs = ["@${label}//file"], outs = "$mavenLabel""""
  def jvmImport: String =
    s"""genrule(name = "${label}", jars = "$mavenLabel"""" // TODO: exports, deps
}
