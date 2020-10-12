package multideps.loggers

object Words {
  val worker =
    Word.simple("worker", 12)
  val dependencies =
    Word.simple("dependency", "dependencies", 1000)
  val transitiveDendencies =
    Word.simple("transitive dependency", "transitive dependencies", 10000)
  val bytes: Word = BytesWord
  def percentage(max: Long): Word = PercentageWord(max)
}
