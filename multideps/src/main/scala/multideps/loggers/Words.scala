package multideps.loggers

object Words {
  val cached =
    Word.simple("cached", "cached", 12)
  val worker =
    Word.simple("worker", 12)
  val remaining =
    Word.simple("remaining", "remaining", 12)
  val dependencies =
    Word.simple("dependency", "dependencies", 1000)
  val transitiveDendencies =
    Word.simple("transitive dependency", "transitive dependencies", 10000)
  val bytes: Word = BytesWord
  val jarFiles: Word = Word.simple("jar file", 1000)
  def percentage(max: Long): Word = PercentageWord(max)
}
