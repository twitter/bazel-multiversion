package multideps.loggers

import java.io.Writer

import coursier.cache.CacheLogger
import coursier.core.Dependency

final case class SaveDepsLogger(writer: Writer) {
  private val p = new ProgressLogger[Dependency](
    "Resolved",
    "dependencies",
    writer
  )
  def start(): Unit = p.start()
  def stop(): Unit = p.stop(keep = true)
  private class SaveDepsCacheLogger(dep: Dependency) extends CacheLogger {
    override def downloadedArtifact(url: String, success: Boolean): Unit = {
      p.processed(url, dep, errored = !success)
    }
    override def downloadingArtifact(url: String): Unit = {
      p.processing(url, dep)
    }
    override def stop(): Unit = {
      p.processedSet(dep)
    }
    override def init(sizeHint: Option[Int]): Unit = {
      p.processingSet(dep, sizeHint)
    }
  }
  def startResolve(dep: Dependency): CacheLogger = new SaveDepsCacheLogger(dep)
  def endResolve(dep: Dependency): Unit = ()
}
