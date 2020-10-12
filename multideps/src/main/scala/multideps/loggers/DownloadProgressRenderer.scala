package multideps.loggers

import coursier.cache.CacheLogger
import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep

class DownloadProgressRenderer(maxArtifacts: Long) extends ProgressRenderer {
  private lazy val timer = new PrettyTimer()
  val loggers = new CoursierLoggers
  override def renderStep(): ProgressStep = {
    ProgressStep.empty
  }
}
