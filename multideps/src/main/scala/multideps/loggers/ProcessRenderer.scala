package multideps.loggers

import java.time.Duration
import java.util.concurrent.ConcurrentLinkedDeque

import scala.collection.JavaConverters._
import scala.math.Ordered._

import multideps.outputs.Docs

import fansi.ErrorMode
import fansi.Str
import moped.json.ErrorResult
import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import moped.reporters.Diagnostic
import org.typelevel.paiges.Doc
import os.ProcessOutput

class ProcessRenderer(
    command: List[String],
    onStop: PrettyTimer => Doc = _ => Doc.empty,
    minimumDuration: Duration = Duration.ofSeconds(1)
) extends ProgressRenderer {
  val lines = new ConcurrentLinkedDeque[String]()
  val output: ProcessOutput.Readlines = os.ProcessOutput.Readlines(line => lines.addLast(line))
  lazy val timer = new PrettyTimer
  val commandString: String = command
    .map { arg =>
      if (arg.contains(" ")) s"'$arg'"
      else arg
    }
    .mkString(" ")
  private def shortCommandString =
    if (commandString.length() > 60) commandString.take(60) + "..."
    else commandString
  def asErrorResult(exitCode: Int): ErrorResult = {
    lines.addFirst(
      s"Bazel query command failed with exit code '${exitCode}'. To reproduce, run the command:\n$$ ${commandString}"
    )
    ErrorResult(Diagnostic.error(lines.asScala.mkString("\n")))
  }
  override def renderStop(): Doc = {
    if (timer.elapsed > minimumDuration) {
      Docs.emoji.success + Doc.text(s"Ran '$commandString' in $timer")
    } else {
      Doc.empty
    }
  }
  override def renderStep(): ProgressStep = {
    val elapsed = timer.elapsed()
    val lastLine = lines.peekLast()
    if (elapsed > minimumDuration && lastLine != null) {
      // Strip out ANSI escape codes since they mess up with the progress bar.
      val lastLineClean = Str(lastLine, errorMode = ErrorMode.Strip).plainText
      val endLine = if (lastLineClean.isEmpty()) Doc.empty else Doc.line
      val active =
        Doc.text(timer.formatPadded()) + Doc.space +
          Doc.text(commandString) + Doc.line +
          Doc.text(lastLineClean) + endLine
      ProgressStep(active = active)
    } else {
      ProgressStep.empty
    }
  }
}
