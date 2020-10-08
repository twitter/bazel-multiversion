package multideps.diagnostics

import coursier.core.Dependency

object MultidepsEnrichments {
  implicit class XtensionDependency(dep: Dependency) {
    def repr: String = s"${dep.module.repr}:${dep.version}"
  }

}
