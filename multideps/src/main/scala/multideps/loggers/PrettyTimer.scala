package multideps.loggers

import java.time.Clock
import java.time.Duration
import java.time.LocalTime
import java.util.concurrent.TimeUnit

class PrettyTimer(clock: Clock = Clock.systemDefaultZone()) {
  val start: LocalTime = LocalTime.now(clock)
  def elapsed(): Duration = Duration.between(start, LocalTime.now())
  def format(): String = PrettyTimer.formatDuration(elapsed())
}

object PrettyTimer {

  private def formatDuration(elapsed: Duration): String = {
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
}
