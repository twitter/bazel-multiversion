package multideps.loggers

import multideps.outputs.Docs

import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import org.typelevel.paiges.Doc

class ResolveProgressRenderer(maxRootDependencies: Long)
    extends ProgressRenderer {
  private val maxRootDependenciesWidth = maxRootDependencies.toString().length()
  val loggers =
    new CoursierLoggers(isArtifactDownload = false, _.endsWith(".pom"))
  private lazy val timer = new PrettyTimer()
  override def renderStop(): Doc = {
    Docs.emoji.success + Doc.text(
      s"Resolved ${loggers.totalRootDependencies} root dependencies and ${loggers.totalTransitiveDependencies} transitive dependencies in ${timer.format()}"
    )
  }
  override def renderStep(): ProgressStep = {
    val activeLoggers =
      loggers.getActiveLoggers().sortBy(-_.totalArtifactCount())
    if (activeLoggers.isEmpty) ProgressStep.empty
    else {
      val currentTransitiveCount =
        activeLoggers.iterator.map(_.totalArtifactCount).sum
      val header = Doc.text(
        List[String](
          "Resolving:",
          timer.format(),
          Words.worker.formatPadded(activeLoggers.size),
          Words.dependencies.formatPadded(loggers.totalRootDependencies),
          Words.transitiveDendencies.formatPadded(
            loggers.totalTransitiveDependencies + currentTransitiveCount
          )
        ).mkString(" ")
      )
      val rows = Doc.tabulate(
        ' ',
        " ",
        activeLoggers.take(12).map { logger =>
          logger.name -> Doc.text(
            Words.transitiveDendencies.format(logger.totalArtifactCount())
          )
        }
      )
      val table = header + Doc.line + rows + Doc.line
      ProgressStep(active = table)
    }
  }

}
