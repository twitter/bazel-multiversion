package multiversion.commands

import java.nio.file.Path
import java.nio.file.Paths

import moped.annotations.CommandName
import moped.annotations.Description
import moped.annotations.ExampleUsage
import moped.annotations.PositionalArguments
import moped.annotations.Repeated
import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.Result
import moped.reporters.Diagnostic
import multiversion.BazelUtil
import multiversion.diagnostics.MultidepsEnrichments._

@ExampleUsage("multiversion resolve //aaa/bbb:ccc")
@CommandName("resolve")
@Description("Query the classpath resolution given a target")
case class ResolveCommand(
    @Description("Print dependency tree") tree: Boolean = false,
    @Description("Print //3rdparty references") `3rdparty`: Boolean = false,
    @Description("Path to bazel executable") bazel: Path = Paths.get("bazel"),
    @Repeated @Description("Additional arguments to pass to bazel") bazelArgs: List[String] = Nil,
    @PositionalArguments targets: List[String] = Nil,
    app: Application = Application.default
) extends Command {

  override def run(): Int = app.complete(runResult())

  def runResult(): Result[Unit] =
    for {
      r <- runCustomQuery()
    } yield (if (tree) r else r.sorted).foreach(app.println(_))

  private def resolveQuery(target: String): String =
    s"""filter("@maven.*", kind("generated file", deps(${target})))"""

  private def resolve3rdpartyQuery(target: String): String =
    s"""kind("third_party_jvm_import", deps(${target}))"""

  private def allPathQuery(target: String): String =
    s"""allpaths($target, @maven//:all)"""

  def runCustomQuery(): Result[List[String]] = {
    val usage = Result.error(Diagnostic.error("resolve command requires 1 target argument"))
    def runResult0(target: String) =
      if (`3rdparty`) runQueryStr(resolve3rdpartyQuery(target))
      else if (tree) runQueryStr(allPathQuery(target))
      else runQueryStr(resolveQuery(target))
    if (targets.size != 1) usage
    else runResult0(targets.head)
  }

  private def runQueryStr(queryExpression: String): Result[List[String]] = {
    val extraBazelArgs =
      if (tree) List("--output", "graph") ::: bazelArgs
      else bazelArgs
    BazelUtil.runQueryStr(app, bazel, queryExpression, extraBazelArgs)
  }
}

object ResolveCommand {
  val default: ResolveCommand = ResolveCommand()
  implicit val parser: CommandParser[ResolveCommand] =
    CommandParser.derive(default)
}
