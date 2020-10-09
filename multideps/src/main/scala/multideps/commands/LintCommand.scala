package multideps.commands

import multideps.indexes.DependenciesIndex

import moped.annotations.CommandName
import moped.annotations.PositionalArguments
import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import moped.reporters.Diagnostic
import org.scalameta.bazel_multideps.Build.QueryResult
import org.scalameta.bazel_multideps.Build.Target

@CommandName("lint")
case class LintCommand(
    @PositionalArguments queryExpressions: List[String] = Nil,
    app: Application = Application.default
) extends Command {
  def run(): Int = {
    val command = List(
      "bazel",
      "query",
      "--notool_deps",
      "--noimplicit_deps",
      s"deps(${queryExpressions.mkString(" ")})",
      "--output=proto"
    )
    val process = os.proc(command).call(cwd = os.Path(app.env.workingDirectory))
    val query = QueryResult.parseFrom(process.out.bytes)
    val index = new DependenciesIndex(query)
    pprint.log(index.targets.map(_.name).sorted)
    val deps = index.dependencies(
      "//tricky/user/src/main/scala/bincompat:NeedsGuavaIllegal"
    )
    val grouped = deps.groupBy(_.dependency.map(_.module))
    pprint.log(grouped)
    0
  }

  private def lintTarget(
      t: Target,
      index: DependenciesIndex
  ): Option[Diagnostic] = {
    pprint.log(t.getRule().getName())
    // pprint.log(deps)
    // pprint.log(tags)
    // pprint.log(dependency)
    None
  }

}

object LintCommand {
  val default: LintCommand = LintCommand()
  implicit val parser: CommandParser[LintCommand] =
    CommandParser.derive(default)
}
