package multideps

import java.nio.file.Paths

import org.scalameta.bazel_multideps.Build.Target.Discriminator

object Multideps {
  def main(args: Array[String]): Unit = {
    val cwd = Paths.get(".").toAbsolutePath().normalize()
    val command = List(
      "bazel",
      "query",
      "--notool_deps",
      "--noimplicit_deps",
      "deps(tricky/user/src/main/scala/bincompat:NeedsVersion1)",
      "--output=proto"
    )
    val x = os.proc(command).call(cwd = os.Path(cwd))
    val query =
      org.scalameta.bazel_multideps.Build.QueryResult.parseFrom(x.out.bytes)
    import scala.collection.JavaConverters._
    val names = query
      .getTargetList()
      .asScala
      .filter(_.getType() == Discriminator.RULE)
      .map(t =>
        t.getRule().getName() -> t
          .getRule()
          .getAttributeList()
          .asScala
          .filter(_.getName() == "deps")
          .flatMap(_.getStringListValueList().asScala)
      )
    pprint.log(names)
  }
}
