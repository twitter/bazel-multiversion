package multideps.outputs

import java.{util => ju}

import org.typelevel.paiges.Doc

final case class DepsOutput(
    artifacts: Seq[ArtifactOutput],
    index: ResolutionIndex,
    outputIndex: ju.Map[String, ArtifactOutput]
) {
  require(artifacts.nonEmpty)
  def validate(): Unit = {
    // val names = artifacts.map()

  }
  def render: String = {
    val width = 120000
    val httpFiles = Doc
      .intercalate(Doc.line, artifacts.map(_.httpFile.toDoc))
      .nested(4)
      .render(width)
    val builds = Doc
      .intercalate(
        Docs.blankLine,
        artifacts.map(ArtifactOutput.buildDoc(_, index, outputIndex))
      )
      .render(width)
    s"""# DO NOT EDIT: this file is auto-generated
def _jvm_deps_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_jvm_deps():
    $httpFiles

'''
    ctx.file("jvm_deps.bzl", content, executable = False)
    build_content = '''
load(\"@io_bazel_rules_scala//scala:scala_import.bzl\", \"scala_import\")

$builds
'''
    ctx.file("BUILD", build_content, executable = False)

jvm_deps_rule = repository_rule(
    implementation = _jvm_deps_impl,
)
def jvm_deps():
  jvm_deps_rule(name = "maven")
""".stripMargin
  }
}
