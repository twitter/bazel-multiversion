package multiversion.resolvers

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

import scala.concurrent.ExecutionContext

/**
 * Thread pool used to resolve POM files.
 *
 * @param taskParalleism
 *   represents the number of parallel resolution tasks
 * @param downloadParallelism
 *   represents the number of parallel downloads
 */
class CoursierThreadPools(taskParalleism: Int, downloadParallelism: Int) {

  def close(): Unit = {
    downloadPool.shutdownNow()
    taskPool.shutdownNow()
  }

  private val threadFactory: ThreadFactory = new ThreadFactory {
    val defaultThreadFactory = Executors.defaultThreadFactory()
    def newThread(r: Runnable) = {
      val t = defaultThreadFactory.newThread(r)
      t.setDaemon(true)
      t
    }
  }

  // From https://github.com/johnynek/bazel-deps/blob/dbd90f155e45e0b2529999d0b74ea65b49e6fb07/src/scala/com/github/johnynek/bazel_deps/CoursierResolver.scala#L19
  val downloadPool: ExecutorService =
    Executors.newFixedThreadPool(downloadParallelism, threadFactory)
  val taskPool: ExecutorService = Executors.newFixedThreadPool(downloadParallelism, threadFactory)
  val ec: ExecutionContext = ExecutionContext.fromExecutorService(taskPool)
}
