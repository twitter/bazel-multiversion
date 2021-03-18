package multiversion.commands

import java.nio.file.Path
import java.nio.file.Paths
import java.time.Duration

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
import multiversion.loggers.LintProgressRenderer
import multiversion.loggers.ProgressBars
import multiversion.resolvers.SimpleDependency

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
      renderer = new LintProgressRenderer(targets.length, app.env.clock, app.isTesting)
      progressBar = ProgressBars.create(app, renderer)
      conflicts = ProgressBars.run(progressBar) { targets.map(findConflicts(_, renderer)) }
      diagnostics <- Result.fromResults(conflicts)
      diagnostic = Diagnostic.fromDiagnostics(diagnostics.flatten)
      result <- diagnostic.map(Result.error).getOrElse(Result.value(()))
    } yield result
  }

  private def findConflicts(
      target: Target,
      renderer: LintProgressRenderer
  ): Result[List[LintDiagnostic]] = {
    val targetName = target.getRule.getName
    renderer.advance(targetName)
    getJars(targetName).map { jars =>
      val dependencies = jars.flatMap(j =>
        SimpleDependency.fromTarget(j) match {
          case None =>
            app.warning(s"Ignoring unknown dependency: '${j.getGeneratedFile.getName}'")
            None
          case some =>
            some
        }
      )
      val conflicts =
        dependencies
          .groupBy(d => (d.module, d.classifier))
          .filter { case (key, values) => values.length > 1 }
          .toList
          .map {
            case ((module, classifier), dependencies) =>
              val targetName = target.getRule.getName
              val pending = isPending(app, targetName)
              LintDiagnostic(
                target.getRule.getName,
                module,
                classifier,
                dependencies.map(_.version).sorted,
                pending
              )
          }
      renderer.reportConflict(conflicts.length)
      conflicts
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

  private def getJars(target: String): Result[List[Target]] = {
    val query = s"kind(file, allpaths($target, @maven//:all))"
    getTargets(query, _.getType() == Target.Discriminator.GENERATED_FILE)
  }

  private def runQuery(queryExpression: String): Result[QueryResult] = {
    val command = List(
      "query",
      queryExpression,
      "--noimplicit_deps",
      "--notool_deps",
      "--output=proto"
    )
    BazelUtil.bazel(app, bazel, command, minimumDuration = Duration.ofSeconds(5)).map { out =>
      QueryResult.parseFrom(out.bytes)
    }
  }
}

object LintCommand {
  val default: LintCommand = LintCommand()
  implicit val parser: CommandParser[LintCommand] =
    CommandParser.derive(default)
}
