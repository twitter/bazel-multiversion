package bincompat

object NeedsGuava extends App {
  val hash = com.google.common.hash.Hashing
    .sha256()
    .hashString("Hello world", java.nio.charset.StandardCharsets.UTF_8)
  println(hash)
}
