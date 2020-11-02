package multideps.loggers

import java.time.Duration

import scala.math.Ordered._

import multideps.diagnostics.MultidepsEnrichments.XtensionSeq
import multideps.outputs.Docs

import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import org.typelevel.paiges.Doc

class DownloadProgressRenderer(maxArtifacts: Long) extends ProgressRenderer {
  private lazy val timer = new PrettyTimer()
  val loggers =
    new CoursierLoggers(isArtifactDownload = true, _.contains(".jar"))
  override def renderStart(): Doc = {
    timer.elapsed() // start timer
    super.renderStart()
  }
  override def renderStop(): Doc = {
    if (
      timer.elapsed > Duration.ofSeconds(1) ||
      loggers.totalDownloadSize > 0
    ) {
      val jars = Words.shaFiles.format(loggers.totalTransitiveDependencies)
      val bytes = Words.bytes.format(loggers.totalDownloadSize)
      val cached =
        if (loggers.totalCachedArtifacts > 0)
          s", ${loggers.totalCachedArtifacts} cached files"
        else ""
      Docs.emoji.success + Doc.text(s"Computed $jars ($bytes$cached) in $timer")
    } else {
      Doc.empty
    }
  }
  override def renderStep(): ProgressStep = {
    val activeLoggers = loggers
      .getActiveLoggers()
      .sortByCachedFunction(-_.maxDownloadSize())
    if (activeLoggers.isEmpty) ProgressStep.empty
    else {
      val downloadSize = loggers.totalDownloadSize +
        activeLoggers.iterator.map(_.downloadSize()).sum
      val maxSize = loggers.totalMaxDownloadSize +
        activeLoggers.iterator.map(_.downloadSize()).sum
      val remaining = maxArtifacts - loggers.totalRootDependencies
      val header = Doc.text(
        List[String](
          "Downloading:",
          timer.formatPadded(),
          s"$remaining/$maxArtifacts remaining",
          s"(${Words.downloadedBytes.formatPadded(downloadSize)})"
        ).mkString(" ")
      )
      val rows = Doc.tabulate(
        ' ',
        " ",
        activeLoggers.take(12).map { logger =>
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
      ProgressStep(dynamic = table)
    }
  }
}
