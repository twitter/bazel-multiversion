package multideps.loggers

object Words {
  val worker =
    Word.simple("worker", 12)
  val dependencies =
    Word("dependency", "dependencies", 1000)
  val transitiveDendencies =
    Word("transitive dependency", "transitive dependencies", 10000)
}
