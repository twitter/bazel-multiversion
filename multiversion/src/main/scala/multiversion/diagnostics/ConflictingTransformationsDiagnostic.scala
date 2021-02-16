package multiversion.diagnostics

import moped.reporters.Diagnostic
import moped.reporters.ErrorSeverity
import multiversion.configs.Transformation
import multiversion.diagnostics.MultidepsEnrichments.XtensionStrings

case class ConflictingTransformationsDiagnostic(
    val conflicts: List[(Transformation, Transformation)]
) extends Diagnostic(ErrorSeverity) {
  private val grouped: List[(Transformation, List[Transformation])] =
    conflicts.groupBy(_._1).mapValues(_.map(_._2)).toList
  private val messages: List[String] = grouped.map {
    case (transformation, conflicts) =>
      s"""| - Transformation '${transformation.show}' ${definedOn(transformation)} conflicts with:
          |${conflicts
        .map(t => s"     - ${t.show} ${definedOn(t)}")
        .mkString(System.lineSeparator())}
          |""".stripMargin
  }
  override def message: String =
    s"""|Conflicting transformations have been found:
        |${messages.mkString(System.lineSeparator())}
        |""".stripMargin
  private def definedOn(t: Transformation): String =
    s"(defined on ${t.definedOn.targets.commas})"
}
