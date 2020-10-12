package multideps.loggers

object Words {
  val cached =
    Word.simple("cached", "cached", 12)
  val worker =
    Word.simple("worker", 12)
  val remaining =
    Word.simple("dependency remaining", "dependencies remaining", 10000)
  val dependencies =
    Word.simple("dependency", "dependencies", 1000)
  val done =
    Word.simple("done", "done", 1000)
  val transitiveDendencies =
    Word.simple("transitive dependency", "transitive dependencies", 10000)
  val bytes: Word = BytesWord
  val downloadedBytes: Word = DownloadedBytesWord
  val jarFiles: Word = Word.simple("jar file", 1000)
  def percentage(max: Long): Word = PercentageWord(max)
}
