package multiversion.diagnostics

import moped.reporters.Diagnostic
import moped.reporters.ErrorSeverity
import moped.reporters.NoPosition
import moped.reporters.WarningSeverity
import multiversion.diagnostics.MultidepsEnrichments.XtensionStrings
import multiversion.resolvers.SimpleModule

case class LintDiagnostic(
    target: String,
    module: SimpleModule,
    classifier: Option[String],
    versions: Seq[String],
    isPending: Boolean
) extends Diagnostic(if (isPending) WarningSeverity else ErrorSeverity, "", NoPosition) {
  override def message: String = {
    val repr = module.repr + ":" + versions.commas + classifier.map(":" + _).getOrElse("")
    s"""|Target '$target' depends on conflicting versions of the 3rdparty dependency '$repr'.
        |\tTo fix this problem, modify the dependency list of this target so that it only
        |\tdepends on one version of the 3rdparty module '${module.repr}'""".stripMargin
  }
}
