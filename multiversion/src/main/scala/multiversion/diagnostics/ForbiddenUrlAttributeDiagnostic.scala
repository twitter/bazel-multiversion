package multiversion.diagnostics

import moped.reporters.Diagnostic
import moped.reporters.ErrorSeverity
import moped.reporters.Position
import multiversion.diagnostics.MultidepsEnrichments._

class ForbiddenUrlAttributeDiagnostic(
    targets: List[String],
    pos: Position
) extends Diagnostic(ErrorSeverity, "", pos) {
  override def message: String = {
    s"""|The third party dependency declaration in ${targets.commas} is defining the URL of the JAR
        |to resolve, which is not permitted.
        |""".stripMargin
  }
}
