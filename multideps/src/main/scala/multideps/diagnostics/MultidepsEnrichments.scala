package multideps.diagnostics

import scala.collection.mutable

import coursier.core.Dependency

object MultidepsEnrichments {
  implicit class XtensionString(string: String) {
    def asLiteral = pprint.PPrinter.BlackWhite.tokenize(string).mkString
  }
  implicit class XtensionStrings(xs: Iterable[String]) {
    def commas: String =
      if (xs.isEmpty) ""
      else if (xs.size == 1) xs.head
      else xs.mkString(", ")
  }
  implicit class XtensionDependency(dep: Dependency) {
    def repr: String = s"${dep.module.repr}:${dep.version}"
    def withoutMetadata: Dependency = Dependency(dep.module, dep.version)
  }
  implicit class XtensionList[A](xs: List[A]) {
    def distinctBy[B](fn: A => B): List[A] = {
      val seen = mutable.LinkedHashSet.empty[B]
      val buf = mutable.ListBuffer.empty[A]
      xs.foreach { x =>
        val isNew = seen.add(fn(x))
        if (isNew) buf += x
      }
      buf.toList
    }
  }

}
