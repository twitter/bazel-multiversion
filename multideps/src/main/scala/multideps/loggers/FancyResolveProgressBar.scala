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

class FancyResolveProgressBar(
    out: Writer,
    terminals: Terminals,
    maxRootDependencies: Long
) {
  private val maxRootDependenciesWidth = maxRootDependencies.toString().length()
  private var totalRootDependencies, totalTransitiveDependencies = 0L
  private val renderer: ProgressRenderer = new ProgressRenderer() {
    private var start = LocalTime.now()
    override def renderStart(): Doc = {
      start = LocalTime.now()
      Doc.empty
    }
    override def renderStep(): ProgressStep = {
      val activeLoggers = getActiveLoggers()
      val activeCount = activeLoggers.iterator.map(_.totalProgress).sum
      val header = Doc.text(
        s"${formatDuration()} " +
          s"${formatWorkerCount(activeLoggers.size)} " +
          s"${formatRootDependencies()} " +
          s"${formatTransitiveDependencies(activeCount)}"
      )
      val rows = Doc.tabulate(
        ' ',
        " ",
        activeLoggers.map { logger =>
          logger.name -> logger.progress
        }
      )
      val active = header + Doc.line + rows
      // pprint.log(active.render(80))
      ProgressStep(
        static = active
      )
    }

    private def formatDuration(): String = {
      val elapsed = Duration.between(start, LocalTime.now())
      val label = List("hr", "min", "sec")
      val value =
        List(elapsed.toHours(), elapsed.toMinutes(), elapsed.getSeconds())
      value
        .zip(label)
        .collect {
          case (v, l) if v > 0 => s"$v$l"
        }
        .mkString
        .padTo("1hr10min10sec".length(), ' ')
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
  }
  private val p = new InteractiveProgressBar(
    out = out,
    renderer = renderer,
    intervalDuration = Duration.ofMillis(20),
    terminal = terminals
  )
  def start() = p.start()
  def stop() = p.stop()

  private val loggers = new ConcurrentLinkedQueue[FancyCacheLogger]
  private def getActiveLoggers(): collection.Seq[FancyCacheLogger] = {
    val buf = mutable.ArrayBuffer.empty[FancyCacheLogger]
    loggers.removeIf { logger =>
      val isActive = logger.isActive
      if (isActive) buf += logger
      else {
        totalRootDependencies += 1
        totalTransitiveDependencies += logger.totalProgress
      }
      isActive
    }
    buf
  }
  def addLogger(logger: FancyCacheLogger): logger.type = {
    loggers.add(logger)
    logger
  }
}
