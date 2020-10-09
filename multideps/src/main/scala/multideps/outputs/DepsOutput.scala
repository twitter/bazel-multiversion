package multideps.outputs

import org.typelevel.paiges.Doc

final case class DepsOutput(
    artifacts: Seq[ArtifactOutput]
) {
  def render: String = {
    val width = 1000
    val httpFiles = Doc
      .intercalate(Doc.line, artifacts.map(_.httpFile.toDoc))
      .nested(4)
      .render(width)
    val builds = Doc
      .intercalate(Docs.blankLine, artifacts.map(_.build))
      .render(width)
    s"""# DO NOT EDIT: this file is auto-generated
def _http_files_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_http_files():
    $httpFiles

'''
    ctx.file("deps.bzl", content, executable = False)
    build_content = '''
package(default_visibility = [\"//visibility:public\"])

$builds
'''
    ctx.file("BUILD", build_content, executable = False)

http_files = repository_rule(
    implementation = _http_files_impl,
)


def all_deps():
  http_files(name = "maven")

""".stripMargin
  }
}
