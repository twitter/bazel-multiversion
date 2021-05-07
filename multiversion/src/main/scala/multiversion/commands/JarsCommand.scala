package multiversion.commands

import java.nio.file.Path
import java.nio.file.Paths

import moped.annotations.CommandName
import moped.annotations.Description
import moped.annotations.ExampleUsage
import moped.annotations.PositionalArguments
import moped.annotations.Repeated
import moped.cli.Application
import moped.cli.CommandParser
import moped.json.Result
import moped.reporters.Diagnostic

@ExampleUsage("multiversion jars junit:junit")
@CommandName("jars")
@Description("Query for JARs given org:name or org:name:ver coordinates")
case class JarsCommand(
    @Description("Path to bazel executable") bazel: Path = Paths.get("bazel"),
    @Repeated @Description("Additional arguments to pass to bazel") bazelArgs: List[String] = Nil,
    @PositionalArguments modules: List[String] = Nil,
    app: Application = Application.default
) extends QueryBasedCommand {

  private val OrgArtifact = """(^[a-zA-Z0-9\.\-_]+)\:([a-zA-Z0-9\.\-_]+)$""".r
  private val OrgArtifactVersion =
    """(^[a-zA-Z0-9\.\-_]+)\:([a-zA-Z0-9\.\-_]+):([a-zA-Z0-9\.\-_]+)$""".r

  private def jarsQuery(org: String, name: String, version: String): String =
    s"""filter("@maven//:${org}/${name}/.*${version}\\.jar", kind("generated file", @maven//...:*))"""

  def runCustomQuery(): Result[List[String]] = {
    val usage = Result.error(Diagnostic.error("jars command requires 1 org:name argument"))
    def runResult0(module: String) =
      module match {
        case OrgArtifactVersion(o, n, v) =>
          runQueryStr(jarsQuery(o, n, v.replaceAllLiterally(".", "\\.")))
        case OrgArtifact(o, n) => runQueryStr(jarsQuery(o, n, ""))
        case _                 => usage
      }
    if (modules.size != 1) usage
    else runResult0(modules.head)
  }
}

object JarsCommand {
  val default: JarsCommand = JarsCommand()
  implicit val parser: CommandParser[JarsCommand] =
    CommandParser.derive(default)
}
