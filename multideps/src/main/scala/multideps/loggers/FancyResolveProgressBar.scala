package multideps.loggers

import java.util.concurrent.ConcurrentLinkedQueue
import moped.progressbars.InteractiveProgressBar
import moped.progressbars.ProgressRenderer
import java.io.Writer
import moped.reporters.Terminals
import moped.progressbars.ProgressStep
import org.typelevel.paiges.Doc
import scala.collection.mutable
import java.time.LocalTime
import java.time.Duration
import java.util.concurrent.TimeUnit
import multideps.outputs.Docs
import coursier.core.Dependency
import coursier.cache.CacheLocks
import coursier.cache.CacheLogger

class FancyResolveProgressBar(maxRootDependencies: Long)
    extends ProgressRenderer {
  private val maxRootDependenciesWidth = maxRootDependencies.toString().length()
  private var totalRootDependencies, totalTransitiveDependencies = 0L
  private var startTime = LocalTime.now()
  override def renderStart(): Doc = {
    startTime = LocalTime.now()
    Doc.empty
  }
  override def renderStop(): Doc = {
    Docs.emoji.success + Doc.text(
      s"Resolved $totalRootDependencies dependencies in ${formatDuration()}"
    )
  }
  override def renderStep(): ProgressStep = {
    val activeLoggers = getActiveLoggers()
    val activeCount = activeLoggers.iterator.map(_.totalProgress).sum
    val header = Doc.text(
      s"${formatDuration()} " +
        s"${formatWorkerCount(activeLoggers.size)} " +
        s"${formatRootDependencies()} with " +
        s"${formatTransitiveDependencies(activeCount)}"
    )
    val rows = Doc.tabulate(
      ' ',
      " ",
      activeLoggers.map { logger =>
        logger.name -> logger.progress
      }
    )
    val active = header + Doc.line + rows + Doc.line
    ProgressStep(
      active = active
      // static = active + Doc.line + Doc.text("#" * 10) + Doc.line
    )
  }

  private def formatDuration(): String = {
    val elapsed = Duration.between(startTime, LocalTime.now())
    val sec = elapsed.getSeconds()
    val hr = TimeUnit.SECONDS.toHours(sec).toDouble
    val min = TimeUnit.SECONDS.toMinutes(sec).toDouble
    val n = elapsed.getNano()
    val ms =
      if (sec > 10) 0
      else TimeUnit.NANOSECONDS.toMillis(n).toDouble / 1000
    val value = List[(String, Double)](
      "hr" -> hr,
      "min" -> min,
      "s" -> (sec.toDouble + ms)
    )
    value
      .collect {
        case (l, v) if v > 0 => f"$v%.1f$l"
      }
      .mkString
      .padTo("1min10.4sec".length(), ' ')
  }
  private def formatCount(
      width: Int,
      whatSingular: String,
      whatPlural: String,
      count: Long
  ): String = {
    val value =
      if (count == 0) ""
      else if (count == 1) s"1 $whatSingular"
      else s"$count $whatPlural"
    value.padTo(width, ' ')
  }
  private def formatCountWithKnownMax(
      width: Int,
      whatSingular: String,
      whatPlural: String,
      count: Long,
      knownMax: Long
  ): String = {
    val value =
      if (count == 0) ""
      else if (count == 1) s"1 $whatSingular"
      else s"$count $whatPlural"
    value.padTo(width, ' ')
  }
  private def formatWorkerCount(count: Long): String = {
    formatCount("12 workers".length(), "worker", "workers", count)
  }
  private def formatRootDependencies(): String = {
    formatCount(
      maxRootDependenciesWidth,
      "dependency",
      "dependencies",
      totalRootDependencies
    )
  }
  private def formatTransitiveDependencies(activeCount: Long): String = {
    formatCount(
      maxRootDependenciesWidth,
      "transitive dependency",
      "transitive dependencies",
      totalTransitiveDependencies + activeCount
    )
  }
  private val loggers = new ConcurrentLinkedQueue[FancyCacheLogger]
  private def getActiveLoggers(): collection.Seq[FancyCacheLogger] = {
    val buf = mutable.ArrayBuffer.empty[FancyCacheLogger]
    loggers.removeIf { logger =>
      val isDone = logger.isAfterStop
      if (isDone) {
        totalRootDependencies += 1
        totalTransitiveDependencies += logger.totalProgress
      } else if (logger.isActive) {
        buf += logger
      }
      isDone
    }
    buf
  }
  def newCacheLogger(dep: Dependency): CacheLogger = {
    import multideps.diagnostics.MultidepsEnrichments.XtensionDependency
    val logger = new FancyCacheLogger(dep.repr)
    loggers.add(logger)
    logger.cacheLogger
  }
}
