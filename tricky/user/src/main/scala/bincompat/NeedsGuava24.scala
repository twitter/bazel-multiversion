package bincompat

object NeedsGuava24 extends App {
  val x = com.google.common.io.Files.fileTreeTraverser()
  val hash = com.google.common.hash.Hashing
    .sha256()
    .hashString("Hello world", java.nio.charset.StandardCharsets.UTF_8)
  println(hash)
}
