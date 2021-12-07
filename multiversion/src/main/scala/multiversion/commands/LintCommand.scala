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
import moped.annotations.Repeated
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
    @Repeated @Description("Additional arguments to pass to bazel") bazelArgs: List[String] = Nil,
    @PositionalArguments queryExpressions: List[String] = Nil,
    app: Application = Application.default
) extends Command {
  val silence: Boolean = lintReportPath.exists { p => p.toString() == "-" }
  def run(): Int = app.completeEither(runResult(), silence)

  def runResult(): Result[Either[Diagnostic, Unit]] = {
    val expr = queryExpressions.mkString(" ")
    for {
      targets <- getTargets(expr, bazelArgs)
      query <- runQuery(s"allpaths($expr, @maven//:all)", bazelArgs)
      index = new DependenciesIndex(query)
      conflicts = targets.map(findConflicts(_, index)).flatten.sortBy(_.toString)
      _ = writeLintReport(conflicts, lintReportPath)
      diagnostic = Diagnostic.fromDiagnostics(conflicts)
      result <-
        diagnostic
          .map { d =>
            Result.value(Left(d))
          }
          .getOrElse(Result.value(Right(())))
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
      val pending = isPending(index, targetName)
      conflicts.map { case ((module, classifier), dependencies) =>
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

  private def isPending(index: DependenciesIndex, label: String): Boolean = {
    index.byName.get(label).map(_.tags.contains("dupped_3rdparty")).getOrElse(false)
  }

  private def getTargets(
      query: String,
      bazelArgs: List[String],
      pred: Target => Boolean = _ => true
  ): Result[List[Target]] =
    runQuery(query, bazelArgs)
      .map {
        _.getTargetList().asScala
          .filter(pred)
          .toList
      }

  private def runQuery(queryExpression: String, bazelArgs: List[String]): Result[QueryResult] = {
    val command = List(
      "query",
      queryExpression,
      "--noimplicit_deps",
      "--notool_deps",
      "--output=proto"
    ) ++ bazelArgs
    BazelUtil.bazelBytes(app, bazel, command).map { out =>
      QueryResult.parseFrom(out.bytes)
    }
  }

  private def writeLintReport(conflicts: List[LintDiagnostic], path: Option[Path]): Unit =
    path
      .foreach { out =>
        val grouped = conflicts.groupBy(_.target).toList.sortBy(_._1)
        val docs = grouped.map { case (target, conflicts) =>
          val isFailure = conflicts.exists(!_.isPending)
          val moduleVersions = conflicts
            .sortBy(_.module.repr)
            .map { case LintDiagnostic(_, module, _, versions, _) =>
              Docs.obj(
                List(
                  "dependency" -> Docs.literal(module.repr),
                  "versions" -> Docs.array(versions.sorted: _*)
                )
              )
            }
          Docs.dash + Doc.space + Docs.obj(
            List(
              "target" -> Docs.literal(target),
              "failure" -> Doc.str(isFailure),
              "conflicts" -> (Docs.openBracket + Doc
                .intercalate(Doc.comma + Doc.space, moduleVersions) + Docs.closeBracket)
            )
          )
        }
        val rendered = Doc.intercalate(Doc.line, docs).render(Int.MaxValue)
        if (out.toString() == "-") {
          writeToStdout(rendered)
        } else {
          writeToFile(rendered, out)
        }
      }

  def writeToStdout(report: String): Unit = {
    app.println("# -------- Begin Lint Result")
    app.println(report)
    app.println("# -------- End Lint Result")
  }

  def writeToFile(report: String, path: Path): Unit = {
    val pathAbs = if (path.isAbsolute()) {
      path
    } else {
      app.env.workingDirectory.resolve(path)
    }
    Files.createDirectories(pathAbs.getParent())
    Files.write(pathAbs, report.getBytes(StandardCharsets.UTF_8))
  }
}

object LintCommand {
  val default: LintCommand = LintCommand()
  implicit val parser: CommandParser[LintCommand] =
    CommandParser.derive(default)
}
