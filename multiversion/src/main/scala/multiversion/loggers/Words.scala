package multiversion.loggers

object Words {
  val transitiveDendencies: Word =
    Word.simple("transitive dependency", "transitive dependencies", 10000)
  val bytes: Word = BytesWord
  val downloadedBytes: Word = DownloadedBytesWord
  val shaFiles: Word = Word.simple("sha file", 1000)
  def percentage(max: Long): Word = PercentageWord(max)
}
