package multideps.loggers

import moped.progressbars.ProgressRenderer
import org.typelevel.paiges.Doc
import moped.progressbars.ProgressStep

class StaticProgressRenderer(underlying: ProgressRenderer)
    extends ProgressRenderer {
  override def renderStart(): Doc =
    underlying.renderStart()
  override def renderStep(): ProgressStep =
    underlying.renderStep().copy(active = Doc.empty)
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
