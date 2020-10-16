package multideps.loggers

import scala.collection.JavaConverters._

import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import org.typelevel.paiges.Doc
import java.time.Duration
import scala.math.Ordered._
import java.util.concurrent.ConcurrentLinkedDeque
import moped.json.ErrorResult
import moped.reporters.Diagnostic
import fansi.Str
import fansi.ErrorMode
import multideps.outputs.Docs

class ProcessRenderer(
    command: List[String],
    onStop: PrettyTimer => Doc = _ => Doc.empty,
    minimumDuration: Duration = Duration.ofSeconds(1)
) extends ProgressRenderer {
  val lines = new ConcurrentLinkedDeque[String]()
  val output = os.ProcessOutput.Readlines(line => lines.addLast(line))
  lazy val timer = new PrettyTimer
  val commandString = command
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
