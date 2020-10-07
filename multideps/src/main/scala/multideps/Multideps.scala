package multideps

import java.nio.file.Paths

import org.scalameta.bazel_multideps.Build.Target.Discriminator
import moped.cli.Application
import moped.commands.VersionCommand
import moped.commands.HelpCommand
import multideps.commands.DepsCommand
import moped.commands.CompletionsCommand
import moped.cli.CommandParser

object Multideps {
  val app = Application.fromName(
    "bm",
    BuildInfo.version,
    List(
      CommandParser[VersionCommand],
      CommandParser[HelpCommand],
      CommandParser[DepsCommand],
      CommandParser[CompletionsCommand]
    )
  )
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
