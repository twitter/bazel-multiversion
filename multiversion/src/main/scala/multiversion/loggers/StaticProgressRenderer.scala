package multiversion.loggers

import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import org.typelevel.paiges.Doc

class StaticProgressRenderer(underlying: ProgressRenderer) extends ProgressRenderer {
  override def renderStart(): Doc =
    underlying.renderStart()
  override def renderStep(): ProgressStep =
    underlying.renderStep().withDynamic(Doc.empty)
  override def renderStop(): Doc =
    underlying.renderStop()
}

object StaticProgressRenderer {
  def ifAnsiDisabled(
      underlying: ProgressRenderer,
      useAnsiOutput: Boolean
  ): ProgressRenderer =
    if (useAnsiOutput) underlying
    else new StaticProgressRenderer(underlying)
}
