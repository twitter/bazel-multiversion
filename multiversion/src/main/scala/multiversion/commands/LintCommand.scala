package multiversion.commands

import java.nio.charset.StandardCharsets
import java.nio.file.Files
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
import multiversion.outputs.Docs
import org.typelevel.paiges.Doc

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
      conflicts = targets.map(findConflicts(_, index)).flatten.sortBy(_.toString)
      _ = writeLintReport(conflicts, lintReportPath)
      diagnostic = Diagnostic.fromDiagnostics(conflicts)
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

  private def writeLintReport(conflicts: List[LintDiagnostic], path: Option[Path]): Unit =
    path
      .map(p => if (p.isAbsolute()) p else app.env.workingDirectory.resolve(p))
      .foreach { out =>
        val grouped = conflicts.groupBy(_.target).toList.sortBy(_._1)
        val docs = grouped.map {
          case (target, conflicts) =>
            val isFailure = conflicts.exists(!_.isPending)
            val moduleVersions = conflicts
              .map {
                case LintDiagnostic(_, module, _, versions, _) =>
                  module.repr -> Docs.array(versions.sorted: _*)
              }
              .sortBy(_._1)
            Docs.literal(target) + Docs.colon + Doc.space + Docs.obj(
              List("failure" -> Doc.str(isFailure), "conflicts" -> Docs.obj(moduleVersions))
            )
        }
        val rendered = Doc.intercalate(Doc.line, docs).render(Int.MaxValue)
        Files.createDirectories(out.getParent())
        Files.write(out, rendered.getBytes(StandardCharsets.UTF_8))
      }
}

object LintCommand {
  val default: LintCommand = LintCommand()
  implicit val parser: CommandParser[LintCommand] =
    CommandParser.derive(default)
}
