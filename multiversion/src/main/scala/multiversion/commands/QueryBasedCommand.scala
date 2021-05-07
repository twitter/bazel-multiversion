package multiversion.commands

import java.nio.file.Path

import moped.cli.Application
import moped.cli.Command
import moped.json.Result
import moped.json.ValueResult
import multiversion.diagnostics.MultidepsEnrichments._
import os.Shellable

/**
 * A moped command that wraps around Bazel queries.
 */
abstract class QueryBasedCommand extends Command {
  def app: Application
  def bazel: Path
  def bazelArgs: List[String]
  def runCustomQuery(): Result[List[String]]

  override def run(): Int = app.complete(runResult())

  def runResult(): Result[Unit] =
    for {
      r <- runCustomQuery()
    } yield r.foreach(Console.out.println(_))

  /**
   * Run Bazel query using moped and capture the output as text.
   *
   * @param queryExpression
   */
  protected def runQueryStr(queryExpression: String): Result[List[String]] = {
    val command = List(
      bazel.toString,
      "query",
      queryExpression,
      "--notool_deps",
      "--noimplicit_deps"
    )
    val lines =
      app
        .process((command ::: bazelArgs).map(x => (x: Shellable)): _*)
        .call()
        .out
        .text()
        .linesIterator
        .toList
    ValueResult(lines)
  }
}
