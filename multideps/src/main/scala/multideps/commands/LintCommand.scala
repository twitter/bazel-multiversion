package multideps.commands

import scala.collection.JavaConverters._
import moped.cli.Command
import moped.annotations.CommandName
import moped.annotations.PositionalArguments
import moped.cli.CommandParser
import moped.cli.Application
import org.scalameta.bazel_multideps.Build.Target
import org.scalameta.bazel_multideps.Build.QueryResult
import scala.collection.mutable
import coursier.core.Module
import coursier.core.Organization
import coursier.core.ModuleName
import scala.util.matching.Regex
import moped.reporters.Diagnostic
import multideps.indexes.TargetIndex
import multideps.resolvers.SimpleModule
import multideps.resolvers.SimpleDependency

@CommandName("lint")
case class LintCommand(
    @PositionalArguments targets: List[String] = Nil,
    app: Application = Application.default
) extends Command {
  def run(): Int = {
    val command = List(
      "bazel",
      "query",
      "--notool_deps",
      "--noimplicit_deps",
      s"deps(${targets.mkString(" ")})",
      "--output=proto"
    )
    val process = os.proc(command).call(cwd = os.Path(app.env.workingDirectory))
    val query = QueryResult.parseFrom(process.out.bytes)
    val index = new DependenciesIndex(query)
    val ts = query
      .getTargetList()
      .asScala
      .filter(_.getType() == Target.Discriminator.RULE)
      .filter(_.getRule().getRuleClass() == "scala_import")
      .map(TargetIndex.fromQuery)
    pprint.log(ts)
    0
  }

  private class DependenciesIndex(query: QueryResult) {
    private val deps: mutable.LinkedHashMap[String, List[String]] =
      mutable.LinkedHashMap.empty
    def dependencies(target: String): List[String] = {
      deps.get(target) match {
        case Some(cached) => cached
        case None =>
          Nil
      }
    }
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
  val default = LintCommand()
  implicit val parser = CommandParser.derive(default)
}
