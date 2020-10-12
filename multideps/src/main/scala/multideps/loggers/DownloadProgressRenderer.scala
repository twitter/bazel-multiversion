package multideps.loggers

import coursier.cache.CacheLogger
import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep

class DownloadProgressRenderer(maxArtifacts: Long) extends ProgressRenderer {
  private lazy val timer = new PrettyTimer()
  override def renderStep(): ProgressStep = {
    ProgressStep.empty
  }
  def newCacheLogger(): CacheLogger = {
    val logger: CacheLogger = new CacheLogger {
      override def downloadLength(
          url: String,
          totalLength: Long,
          alreadyDownloaded: Long,
          watching: Boolean
      ): Unit = ()
      override def downloadProgress(url: String, downloaded: Long): Unit = ()
      override def downloadedArtifact(url: String, success: Boolean): Unit = ()
      override def downloadingArtifact(url: String): Unit = ()
      override def foundLocally(url: String): Unit = ()
    }
    ???
  }
}
