package multiversion.diagnostics

import coursier.core.Module
import moped.reporters.Diagnostic
import moped.reporters.ErrorSeverity
import moped.reporters.Position
import multiversion.diagnostics.MultidepsEnrichments._

class IntraTargetConflictDiagnostic(
    val target: String,
    val module: Module,
    val foundVersions: List[String],
    pos: Position
) extends Diagnostic(ErrorSeverity, "", pos) {
  require(foundVersions.nonEmpty)
  override def message: String = {
    s"""|Within '$target', the module '${module.repr}' is resolved multiple times with incompatible versions ${foundVersions.commas}.
        |To fix this problem, update your dependencies to compatible versions, or add exclusion rules to force compatible versions of '${module.repr}'.
        |""".stripMargin
  }
}
