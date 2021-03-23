package multiversion.commands

import java.nio.file.Path
import java.nio.file.Paths

import scala.collection.JavaConverters._

import com.twitter.multiversion.Build.QueryResult
import com.twitter.multiversion.Build.Target
import moped.annotations.CommandName
import moped.annotations.Description
import moped.annotations.PositionalArguments
import moped.cli.Application
import moped.cli.Command
import moped.cli.CommandParser
import moped.json.Result
import moped.reporters.Diagnostic
import multiversion.BazelUtil
import multiversion.diagnostics.LintDiagnostic
import multiversion.diagnostics.MultidepsEnrichments._
import multiversion.indexes.DependenciesIndex

@CommandName("lint")
case class LintCommand(
    @Description("File to write lint report") lintReportPath: Option[Path] = None,
    @Description("Path to bazel executable") bazel: Path = Paths.get("bazel"),
    @PositionalArguments queryExpressions: List[String] = Nil,
    app: Application = Application.default
) extends Command {
  def run(): Int = app.complete(runResult())
  def runResult(): Result[Unit] = {
    val expr = queryExpressions.mkString(" ")
    for {
      targets <- getTargets(expr)
      query <- runQuery(s"allpaths($expr, @maven//:all)")
      index = new DependenciesIndex(query)
      conflicts = targets.map(findConflicts(_, index))
      diagnostic = Diagnostic.fromDiagnostics(conflicts.flatten.sortBy(_.toString))
      result <- diagnostic.map(Result.error).getOrElse(Result.value(()))
    } yield result
  }

  private def findConflicts(
      target: Target,
      index: DependenciesIndex
  ): List[LintDiagnostic] = {
    val targetName = target.getRule.getName
    val dependencies = index.dependencies(targetName).flatMap(_.dependency).toList
    val conflicts =
      dependencies
        .groupBy(d => (d.module, d.classifier))
        .filter { case (key, values) => values.length > 1 }
        .toList

    if (conflicts.isEmpty) Nil
    else {
      val pending = isPending(app, targetName)
      conflicts.map {
        case ((module, classifier), dependencies) =>
          LintDiagnostic(
            target.getRule.getName,
            module,
            classifier,
            dependencies.map(_.version).sorted,
            pending
          )
      }
    }
  }

  private def isPending(app: Application, label: String): Boolean = {
    val command = List(
      "query",
      s"""attr("tags", "dupped_3rdparty", $label)"""
    )
    BazelUtil
      .bazel(app, bazel, command)
      .map { out =>
        out.trim.linesIterator.contains(label)
      }
      .getOrElse(false)
  }

  private def getTargets(query: String, pred: Target => Boolean = _ => true): Result[List[Target]] =
    runQuery(query)
      .map {
        _.getTargetList().asScala
          .filter(pred)
          .toList
      }

  private def runQuery(queryExpression: String): Result[QueryResult] = {
    val command = List(
      "query",
      queryExpression,
      "--noimplicit_deps",
      "--notool_deps",
      "--output=proto"
    )
    BazelUtil.bazel(app, bazel, command).map { out =>
      QueryResult.parseFrom(out.bytes)
    }
  }
}

object LintCommand {
  val default: LintCommand = LintCommand()
  implicit val parser: CommandParser[LintCommand] =
    CommandParser.derive(default)
}
