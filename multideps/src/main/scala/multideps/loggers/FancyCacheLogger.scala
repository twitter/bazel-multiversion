package multideps.loggers

import org.typelevel.paiges.Doc
import coursier.cache.CacheLogger
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class FancyCacheLogger(val name: String) { self =>
  private val myTotalProgress = new AtomicLong(0)
  private val BeforeStart = 0
  private val Active = 1
  private val AfterStop = 2
  private val state = new AtomicInteger(BeforeStart)
  private def start(): Unit = {
    state.compareAndSet(BeforeStart, Active)
  }
  def stop(): Unit = {
    state.set(AfterStop)
  }
  def progress: Doc =
    Doc.paragraph(s"${myTotalProgress.get()} transitive dependencies")
  def totalProgress: Long = myTotalProgress.get()
  def isActive: Boolean = state.get() == Active
  val cacheLogger: CacheLogger = new CacheLogger {
    override def foundLocally(url: String): Unit = {
      start()
      myTotalProgress.incrementAndGet()
    }
    override def downloadedArtifact(url: String, success: Boolean): Unit = {
      myTotalProgress.incrementAndGet()
    }
    override def init(sizeHint: Option[Int]): Unit = {
      start()
    }
    override def stop(): Unit = {
      self.stop()
    }
  }
}
