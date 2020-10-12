package multideps.loggers

import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import org.typelevel.paiges.Doc
import multideps.outputs.Docs

class DownloadProgressRenderer(maxArtifacts: Long) extends ProgressRenderer {
  private lazy val timer = new PrettyTimer()
  val loggers =
    new CoursierLoggers(isArtifactDownload = true, _.endsWith(".jar"))
  override def renderStop(): Doc = {
    if (loggers.totalDownloadSize > 0) {
      val jars = Words.jarFiles.format(loggers.totalTransitiveDependencies)
      val bytes = Words.bytes.format(loggers.totalDownloadSize)
      val cached =
        if (loggers.totalCachedArtifacts > 0)
          s", ${loggers.totalCachedArtifacts} cached files"
        else ""
      Docs.emoji.success + Doc.text(
        s"Downloaded $jars ($bytes$cached) in $timer"
      )
    } else {
      Doc.empty
    }
  }
  override def renderStep(): ProgressStep = {
    val activeLoggers = loggers.getActiveLoggers().sortBy(-_.downloadProgress())
    if (activeLoggers.isEmpty) ProgressStep.empty
    else {
      val downloadSize = loggers.totalDownloadSize +
        activeLoggers.iterator.map(_.downloadSize()).sum
      val maxSize = loggers.totalMaxDownloadSize +
        activeLoggers.iterator.map(_.downloadSize()).sum
      val header = Doc.text(
        List[String](
          timer.format(),
          Words.worker.formatPadded(activeLoggers.size),
          Words.bytes.formatPadded(downloadSize)
        ).mkString(" ")
      )
      val rows = Doc.tabulate(
        ' ',
        " ",
        activeLoggers.map { logger =>
          val max = Words.bytes.format(logger.maxDownloadSize())
          val percentage = Words
            .percentage(logger.maxDownloadSize())
            .format(logger.downloadSize())
          val doc =
            if (logger.downloadSize() > 0) Doc.text(s"($percentage of $max)")
            else Doc.empty
          logger.name -> doc
        }
      )
      val table = header + Doc.line + rows + Doc.line
      ProgressStep(active = table)
    }
  }
}
