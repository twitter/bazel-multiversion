package multiversion.loggers

import java.io.PrintWriter
import java.time.Duration

import moped.cli.Application
import moped.progressbars.InteractiveProgressBar
import moped.progressbars.ProgressBar
import moped.progressbars.ProgressRenderer

object ProgressBars {
  def create(
      app: Application,
      renderer: ProgressRenderer,
      intervalDuration: Duration = Duration.ofMillis(16)
  ): ProgressBar = {
    val out = new PrintWriter(app.env.standardError)

    val ttyRenderer =
      // Attempt to detect if we are writing to a TTY.
      // See caveats at https://stackoverflow.com/q/1403772
      if (System.console() != null) {
        renderer
      } else {
        new StaticProgressRenderer(renderer)
      }

    new InteractiveProgressBar(
      out = out,
      renderer = ttyRenderer,
      terminal = app.terminal,
      intervalDuration = intervalDuration,
      isDynamicPartEnabled = app.env.isColorEnabled
    )
  }

  def run[T](p: ProgressBar)(thunk: => T): T = {
    try {
      p.start()
      thunk
    } finally {
      p.stop()
    }
  }
}
