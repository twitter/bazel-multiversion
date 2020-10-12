package multideps.loggers

import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import org.typelevel.paiges.Doc

class DownloadProgressRenderer(maxArtifacts: Long) extends ProgressRenderer {
  private lazy val timer = new PrettyTimer()
  val loggers = new CoursierLoggers
  override def renderStep(): ProgressStep = {
    val activeLoggers = loggers.getActiveLoggers()
    if (activeLoggers.isEmpty) ProgressStep.empty
    else {
      val header = Doc.text(
        List[String](
          timer.format(),
          Words.worker.formatPadded(activeLoggers.size),
          Words.bytes.formatPadded(loggers.totalDownloadSize)
        ).mkString(" ")
      )
      val rows = Doc.tabulate(
        ' ',
        " ",
        activeLoggers.map { logger =>
          val percentage = Words
            .percentage(logger.maxDownloadSize())
            .format(logger.downloadSize())
          logger.name -> Doc.text(s"(${percentage})")
        }
      )
      val table = header + Doc.line + rows + Doc.line
      ProgressStep(active = table)
    }
  }
}
