package multideps.loggers

import java.util.concurrent.ConcurrentLinkedQueue

import scala.collection.mutable

import multideps.diagnostics.MultidepsEnrichments.XtensionDependency

import coursier.cache.CacheLogger
import coursier.core.Dependency

class CoursierLoggers {
  private val loggers = new ConcurrentLinkedQueue[TrackingCoursierLogger]
  var totalRootDependencies, totalTransitiveDependencies = 0L
  def getActiveLoggers(): collection.Seq[TrackingCoursierLogger] = {
    val buf = mutable.ArrayBuffer.empty[TrackingCoursierLogger]
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
    val logger = new TrackingCoursierLogger(dep.repr)
    loggers.add(logger)
    logger.cacheLogger
  }
}
