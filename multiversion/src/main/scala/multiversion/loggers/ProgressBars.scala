package multiversion.loggers

import moped.progressbars.ProgressBar

object ProgressBars {
  def run[T](p: ProgressBar)(thunk: => T): T = {
    try {
      p.start()
      thunk
    } finally {
      p.stop()
    }
  }
}
