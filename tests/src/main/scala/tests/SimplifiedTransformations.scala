package tests

import multiversion.configs.Transformation

sealed trait SimplifiedTransformation {
  def canonical: Boolean
  def definedOn: List[String]
}

object SimplifiedTransformation {

  implicit val ordering: Ordering[SimplifiedTransformation] = Ordering.by(_.toString)

  def convert(transformation: Transformation): SimplifiedTransformation =
    transformation match {
      case Transformation.Addition(definedOn, dependency) =>
        val repr = s"${dependency.organization.value}:${dependency.name}:${dependency.version}"
        Addition(transformation.canonical, definedOn.targets, repr)
      case Transformation.Force(definedOn, module, version) =>
        Force(transformation.canonical, definedOn.targets, module.repr, version)
      case Transformation.Exclusion(definedOn, removing) =>
        Exclusion(transformation.canonical, definedOn.targets, removing.repr)
    }
}

case class Addition(canonical: Boolean, definedOn: List[String], dependency: String)
    extends SimplifiedTransformation
case class Force(canonical: Boolean, definedOn: List[String], module: String, version: String)
    extends SimplifiedTransformation
case class Exclusion(canonical: Boolean, definedOn: List[String], module: String)
    extends SimplifiedTransformation
