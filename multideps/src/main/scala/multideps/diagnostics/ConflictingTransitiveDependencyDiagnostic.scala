package multideps.diagnostics

import multideps.diagnostics.MultidepsEnrichments._

import coursier.core.Dependency
import coursier.core.Module
import moped.reporters.Diagnostic
import moped.reporters.ErrorSeverity
import moped.reporters.Position
import multideps.configs.DependencyConfig

class ConflictingTransitiveDependencyDiagnostic(
    val module: Module,
    val transitiveVersions: List[String],
    val declaredDeps: List[DependencyConfig],
    val rootDependencies: List[Dependency],
    pos: Position
) extends Diagnostic(ErrorSeverity, "", pos) {
  val declaredVersions = declaredDeps.flatMap(_.allVersions)
  require(transitiveVersions.nonEmpty)
  require(declaredVersions.nonEmpty)
  private val roots = rootDependencies.filterNot(_.module == module)
  override def message: String = {
    def tokenize(x: Any): Iterator[fansi.Str] =
      pprint.PPrinter.BlackWhite.tokenize(x)
    def pretty(xs: Iterable[Any]): String =
      if (xs.isEmpty) ""
      else if (xs.size == 1) tokenize(xs.head).mkString(" ", "", "")
      else xs.map(tokenize(_).mkString).mkString(" ", ", ", "")
    val toFix =
      if (pos.isNone)
        s"add 'dependencies' to the root dependencies OR create a new root dependency for the module '${module.repr}'."
      else
        "add 'dependencies = ''' to the root dependencies OR add 'targets' to the transitive dependency."
    val rootDependnecies = pretty(roots.distinct.take(5).map(_.repr))
    s"""transitive dependency '${module.repr}' has conflicting versions.
       |    resolved versions:${pretty(transitiveVersions)}
       |    declared versions:${pretty(declaredVersions)}
       |    root dependencies:${rootDependnecies}
       |  To fix this problem, $toFix
       |""".stripMargin.trim
  }
}
