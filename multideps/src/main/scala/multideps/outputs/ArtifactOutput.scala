package multideps.outputs

import multideps.diagnostics.MultidepsEnrichments.XtensionDependency
import coursier.util.Artifact
import coursier.core.Dependency
import org.typelevel.paiges.Doc

final case class ArtifactOutput(
    index: ResolutionIndex,
    outputs: collection.Map[Dependency, ArtifactOutput],
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
  def dependencies =
    index.dependencies
      .getOrElse(dependency.withoutMetadata, Nil)
      .iterator
      .map(d => outputs(d.withoutMetadata))
      .map(_.label)
      .toSeq
  def repr =
    s"""|Artifact(
        |  dep = "${label}",
        |  url = "${artifact.url}",
        |  sha = "${artifactSha256}"
        |)""".stripMargin
  val org = dependency.module.organization.value
  val moduleName = dependency.module.name.value
  val version = dependency.version
  val mavenLabel = s"@maven//:${org}/${moduleName}-${version}.jar"
  // require(artifactsnonEmpty)
  // def label: String = ""
  // def name = ""
  val open = Doc.text("(")
  val close = Doc.text(")")
  def httpFile: TargetOutput =
    TargetOutput(
      kind = "http_file",
      "name" -> Docs.literal(label),
      "urls" -> Docs.array(artifact.url),
      "sha256" -> Docs.literal(artifactSha256)
    )
  def build: Doc =
    genrule.toDoc /
      scalaImport.toDoc
  def genrule: TargetOutput =
    TargetOutput(
      kind = "genrule",
      "name" -> Docs.literal(label + "_extension"),
      "srcs" -> Docs.array(s"@${label}//file"),
      "outs" -> Docs.array(mavenLabel),
      "cmd" -> Docs.literal("cp $< $@")
    )
  def scalaImport: TargetOutput =
    TargetOutput(
      kind = "scala_import",
      "name" -> Docs.literal(label),
      "jars" -> Docs.array(mavenLabel),
      "deps" -> Docs.array(dependencies: _*),
      "tags" -> Docs.array(
        s"jvm_module=${dependency.module.repr}",
        s"jvm_version=${dependency.version}"
      ),
      // TODO(olafur): only make root deps public
      "visibility" -> Docs.array("//visibility:public")
    )
}
