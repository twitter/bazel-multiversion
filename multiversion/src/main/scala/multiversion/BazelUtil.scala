package multiversion

import java.nio.file.Path

import geny.ByteData
import moped.cli.Application
import moped.json.Result
import moped.json.ValueResult
import moped.progressbars.ProcessRenderer
import multiversion.loggers.ProgressBars
import multiversion.loggers.StaticProgressRenderer

object BazelUtil {

  /** The path to the root of the package owning the given label. */
  def packageRoot(app: Application, bazelBin: Path, label: String): Result[Path] = {
    val command = List(
      "query",
      label,
      "--output",
      "package"
    )

    bazel(app, bazelBin, command).map { out =>
      app.env.workingDirectory.resolve(out.trim())
    }
  }

  def bazel(app: Application, bazelBin: Path, command: List[String]): Result[ByteData.Chunks] = {
    val pr0 = new ProcessRenderer(command, command, clock = app.env.clock)
    val pr = StaticProgressRenderer.ifAnsiDisabled(pr0, app.env.isColorEnabled)
    val pb = ProgressBars.create(app, pr)
    val process = ProgressBars.run(pb) {
      os.proc(bazelBin.toString :: command)
        .call(cwd = os.Path(app.env.workingDirectory), stderr = pr0.output, check = false)
    }
    if (process.exitCode == 0) {
      ValueResult(process.out)
    } else {
      pr0.asErrorResult(process.exitCode)
    }
  }
}
