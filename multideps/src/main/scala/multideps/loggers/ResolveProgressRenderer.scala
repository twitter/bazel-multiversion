package multideps.loggers

import java.util.concurrent.ConcurrentLinkedQueue

import scala.collection.mutable

import multideps.outputs.Docs
import multideps.diagnostics.MultidepsEnrichments.XtensionDependency

import coursier.cache.CacheLogger
import coursier.core.Dependency
import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import org.typelevel.paiges.Doc

class ResolveProgressRenderer(maxRootDependencies: Long)
    extends ProgressRenderer {
  private val maxRootDependenciesWidth = maxRootDependencies.toString().length()
  private var totalRootDependencies, totalTransitiveDependencies = 0L
  private lazy val timer = new PrettyTimer()
  override def renderStop(): Doc = {
    Docs.emoji.success + Doc.text(
      s"Resolved $totalRootDependencies root dependencies and $totalTransitiveDependencies transitive dependencies in ${timer.format()}"
    )
  }
  override def renderStep(): ProgressStep = {
    val activeLoggers = getActiveLoggers()
    if (activeLoggers.isEmpty) ProgressStep.empty
    else {
      val currentTransitiveCount =
        activeLoggers.iterator.map(_.totalArtifactCount).sum
      val header = Doc.text(
        s"${timer.format()} " +
          s"${formatWorkerCount(activeLoggers.size)} " +
          s"${formatRootDependencies()} with " +
          s"${formatTransitiveDependencies(currentTransitiveCount)}"
      )
      val rows = Doc.tabulate(
        ' ',
        " ",
        activeLoggers.map { logger =>
          logger.name -> Doc.text(
            logger.totalArtifactCount + "transitive dependencies"
          )
        }
      )
      val table = header + Doc.line + rows + Doc.line
      ProgressStep(active = table)
    }
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
      val isDone = logger.state.isAfterStop
      if (isDone) {
        totalRootDependencies += 1
        totalTransitiveDependencies += logger.totalArtifactCount
      } else if (logger.state.isActive) {
        buf += logger
      }
      isDone
    }
    buf
  }
  def newCacheLogger(dep: Dependency): CacheLogger = {
    val logger = new FancyCacheLogger(dep.repr)
    loggers.add(logger)
    logger.cacheLogger
  }
}
