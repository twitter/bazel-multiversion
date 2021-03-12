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

  val suffix: String =
    "_" + ("_dependencies_" + config.dependencies.sorted.mkString(
      "_"
    ) + "_exclusions_" + config.exclusions.map(_.repr).toSeq.sorted.mkString("_")).hashCode()
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
      outputIndex: collection.Map[String, ArtifactOutput],
  ): Doc = {
    val depId = o.config.toId
    val isDeclaredDependency = index.thirdparty.declaredDependencies.contains(depId)
    if (isDeclaredDependency) buildDeclaredDependencyDoc(o, index, outputIndex)
    else buildGenruleAndImportDoc(o)
  }

  def buildThirdPartyDoc(
      target: String,
      outputs: Seq[ArtifactOutput]
  ): Doc = {
    val name = target.replace(':', '_').stripPrefix("//")
    val jars = outputs.map(_.dependency.mavenLabel)
    val depLabels = outputs.map(o => o.dependency.bazelLabel + o.suffix)
    TargetOutput(
      kind = "scala_import",
      "name" -> Docs.literal(name),
      "jars" -> Docs.array(jars: _*),
      "deps" -> Docs.array(depLabels: _*),
      "exports" -> Docs.array(depLabels: _*),
      "visibility" -> Docs.array("//visibility:public")
    ).toDoc
  }

  private def buildGenruleAndImportDoc(
      o: ArtifactOutput,
  ): Doc = {
    val genrule =
      TargetOutput(
        kind = "genrule",
        "name" -> Docs.literal(s"genrules/${o.label}"),
        "srcs" -> Docs.array(s"@${o.label}//file"),
        "outs" -> Docs.array(o.mavenLabel),
        "cmd" -> Docs.literal("cp $< $@"),
        "tags" -> Docs.array(tags(o.dependency): _*)
      ).toDoc

    val scalaImport =
      TargetOutput(
        kind = "scala_import",
        "name" -> Docs.literal("_" + o.label),
        "jars" -> Docs.array(o.mavenLabel),
        "deps" -> Docs.array(),
        "exports" -> Docs.array(),
        "tags" -> Docs.array(tags(o.dependency): _*),
        "visibility" -> Docs.array("//visibility:public")
      ).toDoc

    genrule / scalaImport
  }

  private def buildDeclaredDependencyDoc(
      o: ArtifactOutput,
      index: ResolutionIndex,
      outputIndex: collection.Map[String, ArtifactOutput]
  ): Doc = {
    val depsRef = index.dependencies
      .getOrElse(o.config.toId, Nil)
      .filterNot(_ == o.dependency)
      .iterator
      .map(index.reconciledDependency(_).bazelLabel)
      .flatMap(outputIndex.get)
      .map("_" + _.label)
      .toSeq
      .distinct

    val scalaImport =
      TargetOutput(
        kind = "scala_import",
        "name" -> Docs.literal(o.label + o.suffix),
        "jars" -> Docs.array(o.mavenLabel),
        "deps" -> Docs.array(depsRef: _*),
        "exports" -> Docs.array(depsRef: _*),
        "tags" -> Docs.array(tags(o.dependency): _*),
        "visibility" -> Docs.array("//visibility:public")
      ).toDoc

    val genRuleAndImport = buildGenruleAndImportDoc(o)

    scalaImport / genRuleAndImport
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
