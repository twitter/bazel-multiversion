package multiversion

import moped.cli.Application
import moped.cli.CommandParser
import moped.commands.CompletionsCommand
import moped.commands.HelpCommand
import moped.commands.VersionCommand
import multiversion.commands._

object MultiVersion {
  val app: Application = Application.fromName(
    "multiversion",
    BuildInfo.version,
    List(
      CommandParser[VersionCommand],
      CommandParser[HelpCommand],
      CommandParser[ImportBuildCommand],
      CommandParser[ExportCommand],
      CommandParser[PantsExportCommand],
      CommandParser[LintCommand],
      CommandParser[ResolveCommand],
      CommandParser[JarsCommand],
      CommandParser[CompletionsCommand]
    )
  )
  def main(args: Array[String]): Unit = {
    app.runAndExitIfNonZero(args.toList)
  }
}
