package multideps.commands

import moped.cli.Application
import java.nio.file.Path
import moped.json.DecodingResult
import multideps.configs.WorkspaceConfig
import java.nio.file.Files
import moped.json.ErrorResult
import moped.reporters.Diagnostic
import moped.reporters.Input
import moped.json.ValueResult
import moped.json.JsonEncoder
import moped.json.JsonDecoder

final class MultidepsApplication(val app: Application) {
  val configPath: Path =
    app.env.workingDirectory.resolve("dependencies.yaml")
  def workspaceConfig: DecodingResult[WorkspaceConfig] = {
    if (!Files.isRegularFile(configPath)) {
      ErrorResult(
        Diagnostic.error(
          s"no such file: $configPath\n\tTo fix this problem, change your working directory or create this file"
        )
      )
    } else {
      WorkspaceConfig.parse(Input.path(configPath))
    }
  }
  def run(result: DecodingResult[Int]): Int =
    result match {
      case ValueResult(exit) =>
        exit
      case ErrorResult(error) =>
        app.reporter.log(error)
        1
    }
}

object MultidepsApplication {
  val default = new MultidepsApplication(Application.default)
  implicit val encoder =
    JsonEncoder.applicationJsonEncoder.contramap[MultidepsApplication](_.app)
  implicit val decoder =
    JsonDecoder.applicationJsonDecoder.map(new MultidepsApplication(_))
}
