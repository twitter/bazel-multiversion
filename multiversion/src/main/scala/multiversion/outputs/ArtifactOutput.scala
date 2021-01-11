package multiversion.outputs

import coursier.core.Dependency
import coursier.util.Artifact
import multiversion.configs.DependencyConfig
import multiversion.diagnostics.MultidepsEnrichments.XtensionDependency
import org.typelevel.paiges.Doc

final case class ArtifactOutput(
    dependency: Dependency,
    config: DependencyConfig,
    artifact: Artifact,
    artifactSha256: String,
    sourcesArtifact: Option[Artifact] = None,
    sourcesArtifactSha256: Option[String] = None
) {
  override def equals(o: Any): Boolean =
    o match {
      case o: ArtifactOutput => this.repr == o.repr
      case _                 => false
    }
  override def hashCode(): Int = this.repr.##

  val classifierRepr: String =
    if (dependency.publication.classifier.nonEmpty)
      s"_${dependency.publication.classifier.value}"
    else ""
  val label: String = dependency.bazelLabel
  val repr: String =
    s"""|Artifact(
        |  dep = "${label}",
        |  url = "${artifact.url}",
        |  sha = "${artifactSha256}"
        |)""".stripMargin
  val org: String = dependency.module.organization.value
  val moduleName: String = dependency.module.name.value
  val version: String = dependency.version
  val mavenLabel: String = dependency.mavenLabel

  def httpFile: TargetOutput =
    TargetOutput(
      kind = "http_file",
      "name" -> Docs.literal(label),
      "urls" -> Docs.array(artifact.url),
      "sha256" -> Docs.literal(artifactSha256)
    )
}

object ArtifactOutput {
  def buildDoc(
      o: ArtifactOutput,
      index: ResolutionIndex,
      outputIndex: collection.Map[String, ArtifactOutput]
  ): Doc = {
    import o._
    val rawDependencies =
      if (
        dependency.module.organization.value == config.organization.value
        && dependency.module.name.value == config.name
        && dependency.version == config.version
      )
        index.dependencies
          .getOrElse(config.toId, Nil)
          .filterNot(_ == dependency)
      else index.maybeDependencies(dependency)
    val depsRef: Seq[String] =
      rawDependencies.iterator
        .flatMap(d =>
          outputIndex.get(index.reconciledDependency(d).bazelLabel) match {
            case Some(x) => Some(x)
            case _ =>
              val recon = index.reconciledDependency(d)
              println(
                s"[warn] ${recon.repr} (originally ${d.repr} called by $label) is missing from `outputs`"
              )
              // sys.error(s"${d.repr} is missing from `outputs`")
              None
          }
        )
        .map(_.label)
        .toSeq
        .distinct
    def genrule: TargetOutput =
      TargetOutput(
        kind = "genrule",
        "name" -> Docs.literal(s"genrules/$label"),
        "srcs" -> Docs.array(s"@${label}//file"),
        "outs" -> Docs.array(mavenLabel),
        "cmd" -> Docs.literal("cp $< $@")
      )
    def scalaImport: TargetOutput =
      TargetOutput(
        kind = "scala_import",
        "name" -> Docs.literal(label),
        "jars" -> Docs.array(mavenLabel),
        "deps" -> Docs.array(depsRef: _*),
        "exports" -> Docs.array(depsRef: _*),
        "tags" -> Docs.array(tags(dependency): _*),
        "visibility" -> Docs.array("//visibility:public")
      )

    genrule.toDoc /
      scalaImport.toDoc
  }

  def tags(dep: Dependency): List[String] =
    List(s"jvm_module=${dep.module.repr}", s"jvm_version=${dep.version}") ::: {
      if (dep.publication.classifier.nonEmpty)
        List(s"jvm_classifier=${dep.publication.classifier.value}")
      else Nil
    }

  def buildEvictedDoc(
      dep: Dependency,
      winner: String,
      index: ResolutionIndex,
      outputIndex: collection.Map[String, ArtifactOutput]
  ): Doc = {
    val depsRef: Seq[String] = Seq(
      dep.withoutConfig.withVersion(winner).bazelLabel
    )
    def scalaImport: TargetOutput =
      TargetOutput(
        kind = "scala_import",
        "name" -> Docs.literal(dep.bazelLabel),
        "jars" -> Docs.array(),
        "deps" -> Docs.array(depsRef: _*),
        "exports" -> Docs.array(depsRef: _*),
        "tags" -> Docs.array(tags(dep) ::: List("evicted=True"): _*),
        "visibility" -> Docs.array("//visibility:public")
      )
    scalaImport.toDoc
  }
}
