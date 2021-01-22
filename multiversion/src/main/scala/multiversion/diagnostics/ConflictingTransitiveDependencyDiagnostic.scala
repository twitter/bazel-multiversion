package multiversion.diagnostics

import coursier.core.Dependency
import coursier.core.Module
import moped.reporters.Diagnostic
import moped.reporters.ErrorSeverity
import moped.reporters.Position
import multiversion.configs.DependencyConfig
import multiversion.diagnostics.MultidepsEnrichments._

class ConflictingTransitiveDependencyDiagnostic(
    val module: Module,
    val foundVersions: List[String],
    val declaredDeps: List[DependencyConfig],
    val popularVersion: String,
    val okRoots: List[Dependency],
    val okDepsConfig: List[DependencyConfig],
    val unpopularRoots: List[Dependency],
    val unpopularDepsConfig: List[DependencyConfig],
    pos: Position
) extends Diagnostic(ErrorSeverity, "", pos) {
  val declaredVersions: List[String] = declaredDeps.flatMap(_.allVersions)
  require(foundVersions.nonEmpty)
  require(declaredVersions.nonEmpty)
  override def message: String = {
    def pretty(xs: Iterable[Any]): String =
      if (xs.isEmpty) ""
      else xs.mkString(" ", ", ", "")
    val depsStr =
      (foundVersions
        .map { v =>
          s"'dependency = ${Dependency(module, v).repr}'"
        })
        .mkString(", ")
    val toFix =
      if (pos.isNone)
        s"add $depsStr to the root dependencies OR create a new root dependency for the module '${module.repr}'."
      else
        s"add $depsStr to the root dependencies OR add 'targets' to the transitive dependency."
    val okRootsStr = pretty(okRoots.distinct.take(20).map(_.repr))
    val okTargetsStr = pretty(okDepsConfig.distinct.take(20).flatMap(_.targets))
    val unpopularRootsStr = pretty(unpopularRoots.distinct.map(_.repr))
    val unpopularTargetsStr = pretty(unpopularDepsConfig.flatMap(_.targets))
    s"""transitive dependency '${module.repr}' has conflicting versions.
       |       found versions:${pretty(foundVersions)}
       |    declared versions:${pretty(declaredVersions)}
       |      popular version: ${popularVersion}
       |              ok deps:${okRootsStr}
       |           ok targets:${okTargetsStr}
       |       unpopular deps:${unpopularRootsStr}
       |    unpopular targets:${unpopularTargetsStr}
       |  To fix this problem, $toFix
       |""".stripMargin.trim
  }
}
