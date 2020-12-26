package multideps.outputs

import multideps.configs.DependencyConfig
import multideps.diagnostics.MultidepsEnrichments.XtensionDependency

import coursier.core.Dependency
import coursier.util.Artifact
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
      case _ => false
    }
  override def hashCode(): Int = this.repr.##

  val classifierRepr: String =
    if (dependency.publication.classifier.nonEmpty)
      s"_${dependency.publication.classifier.value}"
    else ""
  val configRepr: String =
    if (dependency.publication.classifier.nonEmpty)
      s"_${dependency.publication.classifier.value}"
    else if (dependency.configuration.nonEmpty)
      dependency.configuration.value match {
        case "default" => ""
        case config => s"_$config"
      }
    else ""

  val label: String = dependency.bazelLabel
  val repr: String =
    s"""|Artifact(
        |  dep = "${label}",
        |  url = "${artifact.url}",
        |  sha = "${artifactSha256}"
        |)""".stripMargin
  val org = dependency.module.organization.value
  val moduleName = dependency.module.name.value
  val version = dependency.version
  // pprint.log(dependency.configRepr)
  val mavenLabel: String =
    s"@maven//:${org}/${moduleName}-${version}${configRepr}.jar"

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
    // only include dependencies for the root-level dependencies
    // otherwise the transitive artifact would end up pulling too many things
    val rawDependencies =
      if (
        dependency.module.organization.value == config.organization.value
        && dependency.module.name.value == config.name
        && dependency.version == config.version
      )
        index.dependencies
          .getOrElse(config.toId, Nil)
          .filterNot(_ == dependency)
      else Nil
    val depsRef: Seq[String] =
      rawDependencies.iterator
        .flatMap(d =>
          // todo: reconciledDependency could be questionable
          outputIndex.get(index.reconciledDependency(d).repr) match {
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
        "tags" -> Docs.array(
          s"jvm_module=${dependency.module.repr}",
          s"jvm_version=${dependency.version}"
        ),
        "visibility" -> Docs.array("//visibility:public")
      )

    genrule.toDoc /
      scalaImport.toDoc
  }
}
