package multideps.loggers

import java.util.concurrent.atomic.AtomicLong

import coursier.cache.CacheLogger

class FancyCacheLogger(
    val name: String,
    val isIncludedUrl: String => Boolean = _ => true
) {
  private val downloadedArtifactCounter = new AtomicLong(0)
  private val cachedArtifactCounter = new AtomicLong(0)
  private val downloadSizeCounter = new AtomicLong(0)
  private val maxDownloadCounter = new AtomicLong(0)
  val state = new ProgressBarState()
  def totalArtifactCount(): Long =
    downloadedArtifactCounter.get() + cachedArtifactCounter.get()
  def downloadedSize(): Long = 0L
  def maxDownloadSize(): Long = 0L
  val cacheLogger: CacheLogger = new CacheLogger {
    override def foundLocally(url: String): Unit = {
      state.tryStart()
      if (isIncludedUrl(url)) cachedArtifactCounter.incrementAndGet()
    }
    override def downloadedArtifact(url: String, success: Boolean): Unit = {
      if (isIncludedUrl(url)) downloadedArtifactCounter.incrementAndGet()
    }
    override def downloadProgress(url: String, downloaded: Long): Unit = {
      downloadSizeCounter.set(downloaded)
    }
    override def downloadLength(
        url: String,
        totalLength: Long,
        alreadyDownloaded: Long,
        watching: Boolean
    ): Unit = {
      maxDownloadCounter.set(totalLength)
      downloadSizeCounter.set(alreadyDownloaded)
    }
    override def init(sizeHint: Option[Int]): Unit = {
      state.tryStart()
    }
    override def stop(): Unit = {
      state.stop()
    }
  }
}
