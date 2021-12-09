package multiversion.diagnostics

import java.{ util => ju }

import scala.collection.mutable

import coursier.core.Configuration
import coursier.core.Dependency
import coursier.error.ResolutionError
import coursier.util.Task
import moped.cli.Application
import moped.json.ErrorResult
import moped.json.Result
import moped.json.ValueResult
import moped.reporters.Diagnostic
import moped.reporters.ErrorSeverity
import moped.reporters.Reporter
import moped.reporters.WarningSeverity

object MultidepsEnrichments {
  implicit class XtensionString(string: String) {
    def asLiteral: String = pprint.PPrinter.BlackWhite.tokenize(string).mkString
  }
  implicit class XtensionReporter(reporter: Reporter) {
    def exitCode(): Int = if (reporter.hasErrors()) 1 else 0
  }
  implicit class XtensionStrings(xs: Iterable[String]) {
    def commas: String =
      if (xs.isEmpty) ""
      else if (xs.size == 1) xs.head
      else xs.mkString(", ")
  }
  implicit class XtensionApplication(app: Application) {
    def isTesting: Boolean =
      app.env.isSettingTrue("MULTIDEPS_TESTING")

    def completeEither(result: Result[Either[Diagnostic, Unit]], silence: Boolean = false): Int =
      result match {
        case ValueResult(Right(())) =>
          app.reporter.exitCode()
        case ValueResult(Left(diagnostic)) =>
          if (!silence) {
            app.reporter.log(diagnostic)
          }
          100
        case ErrorResult(error) =>
          if (!silence) {
            app.reporter.log(error)
          }
          1
      }

    def complete(result: Result[Unit]): Int =
      result match {
        case ValueResult(()) =>
          app.reporter.exitCode()
        case ErrorResult(error) =>
          app.reporter.log(error)
          1
      }

    def reportOrElse[T](diagnostics: List[Diagnostic], result: => T): Result[T] = {
      val hasError = diagnostics.exists(_.severity >= ErrorSeverity)
      if (hasError) {
        Diagnostic.fromDiagnostics(diagnostics) match {
          case Some(diagnostic) => ErrorResult(diagnostic)
          case None             => ValueResult(result)
        }
      } else {
        diagnostics.foreach(app.reporter.log)
        val warnings = diagnostics.count(_.severity == WarningSeverity)
        if (warnings > 0) {
          app.reporter.warning(warnings + " warning(s) found.")
        }
        ValueResult(result)
      }
    }
  }
  private val isEmptyLikeConfiguration = Set(
    Configuration.empty,
    Configuration.compile,
    Configuration.defaultCompile,
    Configuration.default,
    Configuration.runtime
  )
  implicit class XtensionDependency(dep: Dependency) {
    def configRepr: String =
      if (isEmptyLikeConfiguration(dep.configuration)) ""
      else s"_${dep.configuration.value}"
    def repr: String = s"${dep.module.repr}:${dep.version}${configRepr}"
    def withoutMetadata: Dependency = Dependency(dep.module, dep.version)

    def bazelLabel: String = {
      val classifierRepr =
        if (dep.publication.classifier.nonEmpty)
          s"_${dep.publication.classifier.value}"
        else ""
      // Bazel workspace names may contain only A-Z, a-z, 0-9, '-', '_' and '.'
      repr.replaceAll("[^a-zA-Z0-9-\\.]", "_") + classifierRepr
    }

    def baseMavenLabel: String = {
      val org = dep.module.organization.value
      val moduleName = dep.module.name.value
      val version = dep.version
      val classifierOrConfigRepr: String =
        if (dep.publication.classifier.nonEmpty)
          s"-${dep.publication.classifier.value}"
        else if (dep.configuration.nonEmpty)
          dep.configuration.value match {
            case "default" => ""
            case config    => s"-$config"
          }
        else ""

      s"@maven//:${org}/${moduleName}/${version}/${moduleName}-${version}${classifierOrConfigRepr}"
    }

    def mavenLabel: String = s"$baseMavenLabel.jar"
    def mavenSourcesLabel: String = s"$baseMavenLabel-sources.jar"

    def withoutConfig: Dependency =
      dep.withConfiguration(Configuration.empty)
  }
  implicit class XtensionSeq[A](xs: Seq[A]) {
    def sortByCachedFunction[B: Ordering](fn: A => B): Seq[A] = {
      val map = new ju.IdentityHashMap[A, B]
      xs.foreach { x =>
        // Cache the function value to obey sorting invariants.
        map.put(x, fn(x))
      }
      xs.sortBy(x => map.get(x))
    }
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
  implicit class XtensionTaskIO[T](t: Task[T]) {
    def toResult: Task[Result[T]] =
      t.map(ValueResult(_)).handle { case ex: ResolutionError =>
        ErrorResult(Diagnostic.error(ex.getMessage()))
      }
  }

}
