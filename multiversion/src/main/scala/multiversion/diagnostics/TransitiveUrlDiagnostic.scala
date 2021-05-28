package multiversion.diagnostics

import moped.reporters.Diagnostic
import moped.reporters.Position
import moped.reporters.WarningSeverity
import multiversion.diagnostics.MultidepsEnrichments._

class TransitiveUrlDiagnostic(
    targets: List[String],
    pos: Position
) extends Diagnostic(WarningSeverity, "", pos) {
  override def message: String = {
    s"""|The third party dependency declaration in ${targets.commas} is defining the URL of the JAR
        |to resolve, but isn't marked as intransitive. It will be considered intransitive.
        |""".stripMargin
  }
}
