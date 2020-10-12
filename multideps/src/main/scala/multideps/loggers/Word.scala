package multideps.loggers

import coursier.cache.loggers.ProgressBarRefreshDisplay

sealed abstract class Word(paddingWidth: Int) {
  final def formatPadded(count: Long): String =
    format(count).padTo(paddingWidth, ' ')
  def format(count: Long): String
}
final case class SimpleWord(
    singular: String,
    plural: String,
    reasonableMaximumValue: Int
) extends Word(reasonableMaximumValue.toString.length) {
  def format(count: Long): String = {
    if (count == 1) s"$count $singular"
    else s"$count $plural"
  }
}

final case class PercentageWord(max: Long) extends Word("99.2%".length()) {
  def format(count: Long): String = {
    if (max == 0) ""
    else {
      val p = math.min(1, count.toDouble / max)
      f"$p%.1f%%"
    }
  }
}
object BytesWord extends Word("100mb".length()) {
  def format(count: Long): String = {
    ProgressBarRefreshDisplay.byteCount(count, si = false)
  }
}
object Word {
  def simple(
      singular: String,
      plural: String,
      reasonableMaximumValue: Int
  ): Word =
    SimpleWord(singular, plural, reasonableMaximumValue)
  def simple(word: String, reasonableMaximumValue: Int): Word =
    Word.simple(word, word + "s", reasonableMaximumValue)
}
