package multideps.loggers

case class Word(singular: String, plural: String, reasonableMaximumValue: Int) {

  def formatPadded(count: Long): String = {
    format(count).padTo(reasonableMaximumValue.toString().length(), ' ')
  }

  def format(count: Long): String = {
    if (count == 1) s"$count $singular"
    else s"$count $plural"
  }
}
object Word {
  def simple(word: String, reasonableMaximumValue: Int): Word =
    Word(word, word + "s", reasonableMaximumValue)
}
