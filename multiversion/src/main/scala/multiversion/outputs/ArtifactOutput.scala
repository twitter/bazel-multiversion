package multiversion.outputs

import coursier.core.Dependency
import coursier.util.Artifact
import multiversion.diagnostics.MultidepsEnrichments.XtensionDependency
import multiversion.resolvers.ResolvedDependency
import org.typelevel.paiges.Doc

final case class ArtifactOutput(
    dependency: Dependency,
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
  val sourcesLabel: String = label + "_sources"
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
  val mavenSourcesLabel: String = dependency.mavenSourcesLabel
  def hasSources: Boolean = sourcesArtifact.isDefined && sourcesArtifactSha256.isDefined

  def httpFiles: List[TargetOutput] =
    List(httpFile) ::: sourcesHttpFile.toList

  def httpFile: TargetOutput =
    TargetOutput(
      kind = "http_file",
      "name" -> Docs.literal(label),
      "urls" -> Docs.array(artifact.url),
      "sha256" -> Docs.literal(artifactSha256)
    )

  def sourcesHttpFile: Option[TargetOutput] =
    (sourcesArtifact, sourcesArtifactSha256) match {
      case (Some(art), Some(sha)) =>
        Some(
          TargetOutput(
            kind = "http_file",
            "name" -> Docs.literal(sourcesLabel),
            "urls" -> Docs.array(art.url),
            "sha256" -> Docs.literal(sha)
          )
        )
      case _ => None
    }
}

object ArtifactOutput {

  def buildThirdPartyDoc(
      target: String,
      index: ResolutionIndex,
      outputIndex: collection.Map[String, ArtifactOutput]
  ): Doc = {
    val name = target.replace(':', '_').stripPrefix("//")
    val targetConfigs = index.thirdparty.depsByTargets.getOrElse(target, Nil)
    val jarsAndLabels = targetConfigs.flatMap { cfg =>
      val dependency = cfg.toCoursierDependency(index.thirdparty.scala)
      val reconciledDependency = index.reconciledDependency(dependency)
      outputIndex
        .get(reconciledDependency.bazelLabel)
        .map(o =>
          (
            o.mavenLabel,
            o.dependency,
            o.label + cfg.suffix,
            if (o.hasSources) Some(o.mavenSourcesLabel) else None,
          )
        )
    }

    val (jars, dependencies, depLabels, sourceJars) =
      if (jarsAndLabels.nonEmpty) {
        unzip4(jarsAndLabels)
      } else {
        // Some resolutions produce no artifacts because they configure a classifier that
        // doesn't exist. In this case, we return the dependencies that were resolved
        // alongside this non-existent artifact.
        unzip4(index.unevictedArtifacts.collect {
          case ResolvedDependency(config, dependency, _, _, _) if config.targets.contains(target) =>
            (dependency.mavenLabel, dependency, "_" + dependency.bazelLabel, None: Option[String])
        })
      }
    val sourceJarOpt = sourceJars.flatten.headOption

    val overriddingTargets = for {
      config <- targetConfigs
      dependency <- config.dependencies
      if index.thirdparty.overriddingTargets.contains(dependency)
    } yield dependency
    val allLabels = (overriddingTargets ++ depLabels).distinct
    val depTags = dependencies.distinct.flatMap(tags)
    sourceJarOpt match {
      case Some(sourceJar) =>
        TargetOutput(
          kind = "scala_import",
          "name" -> Docs.literal(name),
          "jars" -> Docs.array(jars: _*),
          "deps" -> Docs.array(allLabels: _*),
          "exports" -> Docs.array(allLabels: _*),
          "srcjar" -> Docs.literal(sourceJar),
          "tags" -> Docs.array(depTags: _*),
          "visibility" -> Docs.array("//visibility:public")
        ).toDoc
      case _ =>
        TargetOutput(
          kind = "scala_import",
          "name" -> Docs.literal(name),
          "jars" -> Docs.array(jars: _*),
          "deps" -> Docs.array(allLabels: _*),
          "exports" -> Docs.array(allLabels: _*),
          "tags" -> Docs.array(depTags: _*),
          "visibility" -> Docs.array("//visibility:public")
        ).toDoc
    }
  }

  private def buildGenruleAndImportDoc(
      o: ArtifactOutput,
  ): Doc = {
    def genrule =
      TargetOutput(
        kind = "genrule",
        "name" -> Docs.literal(s"genrules/${o.label}"),
        "srcs" -> Docs.array(s"@${o.label}//file"),
        "outs" -> Docs.array(o.mavenLabel),
        "cmd" -> Docs.literal("cp $< $@"),
        "tags" -> Docs.array(tags(o.dependency): _*)
      ).toDoc

    def sourcesGenrule =
      TargetOutput(
        kind = "genrule",
        "name" -> Docs.literal(s"genrules/${o.sourcesLabel}"),
        "srcs" -> Docs.array(s"@${o.sourcesLabel}//file"),
        "outs" -> Docs.array(o.mavenSourcesLabel),
        "cmd" -> Docs.literal("cp $< $@"),
      ).toDoc

    def scalaImport =
      TargetOutput(
        kind = "scala_import",
        "name" -> Docs.literal("_" + o.label),
        "jars" -> Docs.array(o.mavenLabel),
        "deps" -> Docs.array(),
        "exports" -> Docs.array(),
        "tags" -> Docs.array(tags(o.dependency): _*),
        "visibility" -> Docs.array("//visibility:public")
      ).toDoc

    def scalaImportWithSrcJar =
      TargetOutput(
        kind = "scala_import",
        "name" -> Docs.literal("_" + o.label),
        "jars" -> Docs.array(o.mavenLabel),
        "deps" -> Docs.array(),
        "exports" -> Docs.array(),
        "srcjar" -> Docs.literal(o.mavenSourcesLabel),
        "tags" -> Docs.array(tags(o.dependency): _*),
        "visibility" -> Docs.array("//visibility:public")
      ).toDoc

    if (o.hasSources) genrule / sourcesGenrule / scalaImportWithSrcJar
    else genrule / scalaImport
  }

  def buildDoc(
      o: ArtifactOutput,
      index: ResolutionIndex,
      outputIndex: collection.Map[String, ArtifactOutput]
  ): Doc = {
    // This `ArtifactOutput` may belong to several `DependencyConfig`. We need to create a target
    // corresponding to each of these configurations, to reflect the different resolution contexts.
    val scalaImports = index.configsOf(o.dependency).map { config =>
      val depsRef = index.dependencies
        .getOrElse(config.id, Nil)
        .filterNot(_.repr == o.dependency.repr)
        .iterator
        .map(index.reconciledDependency(_).bazelLabel)
        .flatMap(outputIndex.get)
        .map("_" + _.label)
        .toSeq
        .distinct

      TargetOutput(
        kind = "scala_import",
        "name" -> Docs.literal(o.label + config.suffix),
        "jars" -> Docs.array(o.mavenLabel),
        "deps" -> Docs.array(depsRef: _*),
        "exports" -> Docs.array(depsRef: _*),
        "tags" -> Docs.array(tags(o.dependency): _*),
        "visibility" -> Docs.array("//visibility:public")
      ).toDoc
    }

    val genRuleAndImport = buildGenruleAndImportDoc(o)

    Doc.stack(scalaImports) / genRuleAndImport
  }

  def tags(dep: Dependency): List[String] =
    List(s"jvm_module=${dep.module.repr}", s"jvm_version=${dep.version}") ::: {
      if (dep.publication.classifier.nonEmpty)
        List(s"jvm_classifier=${dep.publication.classifier.value}")
      else Nil
    }

  def unzip4[A1, A2, A3, A4](
      xs: List[(A1, A2, A3, A4)]
  ): (List[A1], List[A2], List[A3], List[A4]) = {
    val b1 = List.newBuilder[A1]
    val b2 = List.newBuilder[A2]
    val b3 = List.newBuilder[A3]
    val b4 = List.newBuilder[A4]
    xs.foreach { case (a1, a2, a3, a4) =>
      b1 += a1
      b2 += a2
      b3 += a3
      b4 += a4
    }
    (b1.result, b2.result, b3.result, b4.result)
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
