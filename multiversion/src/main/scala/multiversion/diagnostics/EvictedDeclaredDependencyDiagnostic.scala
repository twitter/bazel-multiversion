package multiversion.diagnostics

import coursier.core.Dependency
import moped.reporters.Diagnostic
import moped.reporters.Position
import moped.reporters.Severity
import multiversion.diagnostics.MultidepsEnrichments._

class EvictedDeclaredDependencyDiagnostic(
    declaredDependency: Dependency,
    declaringTargets: Iterable[String],
    selectedDependency: Dependency,
    breakingTargets: Iterable[String],
    hasUrl: Boolean,
    severity: Severity,
    pos: Position
) extends Diagnostic(severity, "", pos) {
  private val urlInfos =
    if (hasUrl)
      List(
        "  This dependency defines the URL of the JAR to resolve, forcing this message to the ERROR level."
      )
    else Nil
  private val infos = urlInfos ++ List(
    s"  '${declaredDependency.repr}' is declared in ${declaringTargets.toList.sorted.mkString(", ")}.",
    s"  '${selectedDependency.repr}' is a transitive dependency of ${breakingTargets.toList.sorted.mkString(", ")}."
  )

  override def message: String = {
    s"""|Declared third party dependency '${declaredDependency.repr}' is evicted in favor of '${selectedDependency.repr}'.
        |Update the third party declaration to use version '${selectedDependency.version}' instead of '${declaredDependency.version}' to reflect the effective dependency graph.
        |Info:
        |${infos.mkString(System.lineSeparator)}""".stripMargin
  }
}
