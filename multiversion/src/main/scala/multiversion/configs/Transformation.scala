package multiversion.configs

import scala.collection.mutable.Buffer

import coursier.core.Module
import moped.json.ErrorResult
import moped.json.Result
import moped.json.ValueResult
import multiversion.configs.Transformation._
import multiversion.diagnostics.ConflictingTransformationsDiagnostic
import multiversion.diagnostics.MultidepsEnrichments.XtensionStrings

/**
 * A transformation that modifies the dependency resolution graph.
 *
 * Every transformation can be either canonical or local. Canonical transformations are
 * transformations that are defined on canonical definitions, and apply globally to the dependency
 * graph. All other transformations are local transformations and apply only on the targets where
 * they're defined. These transformations introduce a fork in the dependency resolution graph.
 */
sealed trait Transformation {

  /** The configuration where this transformation was defined */
  def definedOn: DependencyConfig

  /** Is this transformation a canonical transformation? */
  def canonical: Boolean = {
    val org = definedOn.organization.value.replaceAllLiterally(".", "/")
    definedOn.targets.exists(_.startsWith(s"3rdparty/jvm/$org:"))
  }

  /**
   * Does this transformation subsume the given transformation? That is, does this transformation
   * render the given transformation redundant?
   *
   * Only global transformations can subsume other transformations.
   */
  def subsumes(t1: Transformation): Boolean =
    (this, t1) match {
      case (ex0 @ Exclusion(d0, m0), ex1 @ Exclusion(d1, m1)) =>
        ex0.canonical &&
          m0 == m1 &&
          d0.organization.value == d1.organization.value &&
          d0.name == d1.name

      case (f0 @ Force(d0, m0, v0), f1 @ Force(d1, m1, v1)) =>
        f0.canonical && m0 == m1 && v0 == v1

      case (a0 @ Addition(d0, dep0), a1 @ Addition(d1, dep1)) =>
        a0.canonical &&
          dep0 == dep1 &&
          d0.organization.value == d1.organization.value &&
          d0.name == d1.name

      case _ =>
        false
    }

  /** Does this transformation conflict with the given transformation? */
  def conflictsWith(t1: Transformation): Boolean =
    (this, t1) match {
      case (f0 @ Force(d0, m0, v0), f1 @ Force(d1, m1, v1)) =>
        m0 == m1 && v0 != v1 &&
          (
            (f0.canonical && f1.canonical) ||
              (!f0.canonical && !f1.canonical && d0 == d1)
          )

      case (a0 @ Addition(d0, dep0), a1 @ Addition(d1, dep1)) =>
        a0.canonical && a1.canonical &&
          d0.organization.value == d1.organization.value &&
          d0.name == d1.name &&
          d0.version == d1.version &&
          dep0.organization.value == dep1.organization.value &&
          dep0.name == dep1.name &&
          dep0.version != dep1.version

      case _ =>
        false
    }

  def show: String
}

object Transformation {

  /**
   * A transformation configuring the exclusion of the given module.
   *
   * Canonical exclusion transformations will prevent the given module from being resolved in any
   * resolution that (possibly transitively) include the module the transformation is defined on.
   *
   * Local exclusion transformations will prevent the given module from being resolved when
   * resolving the target on which the transformation is defined.
   *
   * @param definedOn The dependency on which the transformation is defined.
   * @param excluded The module to exclude from resolution.
   */
  case class Exclusion(definedOn: DependencyConfig, excluded: Module) extends Transformation {
    override def show: String =
      if (canonical)
        s"exclude ${excluded.repr} when resolving ${definedOn.organization.value}:${definedOn.name}"
      else s"exclude ${excluded.repr} when resolving ${definedOn.targets.commas}"
  }

  /**
   * A transformation configuring the addition of a dependency to a given module.
   *
   * Canonical addition transformations will modify the dependency graph so that the added
   * dependency is always resolved when the artifact on which the transformation is defined is
   * resolved.
   *
   * Local addition transformations will add the dependency when the target on which the
   * transformation is defined is being resolved.
   *
   * @param definedOn The dependency on which the transformation is defined.
   * @param dependency The dependency to add.
   */
  case class Addition(definedOn: DependencyConfig, dependency: DependencyConfig)
      extends Transformation {
    override def show: String = {
      val depRepr = s"${dependency.organization.value}:${dependency.name}:${dependency.version}"
      if (canonical)
        s"always depend on $depRepr when resolving ${definedOn.organization.value}:${definedOn.name}"
      else s"depend on $depRepr when resolving ${definedOn.targets.commas}"
    }
  }

  /**
   * A transformation configuring the replacement of a version for another.
   *
   * Canonical force transformations apply to the entirety of the main dependency graph and will
   * force the given module to be resolved with the given version.
   *
   * Local force transformations will apply only on the resolution of the module on which they're
   * defined.
   *
   * @param definedOn The dependency on which the transformation is defined.
   * @param module The module whose version to force.
   * @param version The forced version.
   */
  case class Force(definedOn: DependencyConfig, module: Module, version: String)
      extends Transformation {
    override def show: String =
      if (canonical) s"always force version of ${module.repr} to $version"
      else
        s"force version of ${module.repr} to $version when resolving ${definedOn.targets.commas}"
  }

  def inferTransformations(config: ThirdpartyConfig): Result[List[Transformation]] =
    config.dependencies2
      .foldLeft(TransformationsBuffer.empty)(_ ++ infer(config, _))
      .toList

  def globalAdditions(transformations: List[Transformation]): List[Addition] =
    transformations.collect {
      case a: Addition if a.canonical => a
    }

  def globalForces(transformations: List[Transformation]): List[Force] =
    transformations.collect {
      case f: Force if f.canonical => f
    }

  def globalExclusions(transformations: List[Transformation]): List[Exclusion] =
    transformations.collect {
      case e: Exclusion if e.canonical => e
    }

  private def infer(config: ThirdpartyConfig, dep: DependencyConfig): List[Transformation] = {
    val dependencies = dep.dependencies.flatMap { depSpec =>
      config.depsByTargets
        .getOrElse(depSpec, Nil)
        .map(d => (d.coursierModule(config.scala), depSpec, d.version))
    }
    val additionTransformations = dependencies
      .flatMap {
        case (_, depSpec, _) =>
          config.depsByTargets.getOrElse(depSpec, Nil).flatMap { dependency =>
            val module = ModuleConfig(dependency.organization.value, dependency.name)
            if (dep.exclusions.contains(module)) None
            else Some(Addition(dep, dependency))
          }
      }
    val exclusionTransformations = dep.exclusions
      .filter { ex => !dependencies.exists { case (m, _, _) => m == ex.coursierModule } }
      .map { ex => Exclusion(dep, ex.coursierModule) }
      .toList
    val forceTransformations = dependencies
      .filter {
        case (m, _, _) =>
          dep.exclusions.exists {
            case ex if ex.name.value == "*" => m.organization.value == ex.organization.value
            case ex                         => m == ex.coursierModule
          }
      }
      .map { case (m, _, v) => Force(dep, m, v) }
    additionTransformations ++ exclusionTransformations ++ forceTransformations
  }

  private case class TransformationsBuffer(
      exclusions: Buffer[Exclusion],
      additions: Buffer[Addition],
      forces: Buffer[Force]
  ) {
    def ++(rs: List[Transformation]): TransformationsBuffer = rs.foldLeft(this)(_ + _)
    def +(r: Transformation): TransformationsBuffer =
      r match {
        case e: Exclusion => exclusions += e; this
        case a: Addition  => additions += a; this
        case f: Force     => forces += f; this
      }

    def toList: Result[List[Transformation]] = {
      val transformations =
        cleanTransformations(exclusions) ++ cleanTransformations(forces) ++ cleanTransformations(
          additions
        )
      findConflicts(transformations) match {
        case Nil       => ValueResult(transformations)
        case conflicts => ErrorResult(ConflictingTransformationsDiagnostic(conflicts))
      }
    }

    @annotation.tailrec
    private def findConflicts(
        transformations: List[Transformation],
        acc: List[(Transformation, Transformation)] = Nil
    ): List[(Transformation, Transformation)] = {
      transformations match {
        case current :: rest =>
          val conflicts = rest.filter(current.conflictsWith(_)).map((current, _))
          findConflicts(rest, acc ++ conflicts)
        case Nil =>
          acc
      }
    }

    private def cleanTransformations[T <: Transformation](transformations: Buffer[T]): List[T] = {
      val (canonTransformations, localTransformations) = transformations.partition(_.canonical)
      (canonTransformations ++ localTransformations)
        .foldLeft(List.empty[T]) {
          case (acc, transformation) if !acc.exists(_.subsumes(transformation)) =>
            transformation :: acc
          case (acc, _) => acc
        }
        .reverse
    }
  }

  private object TransformationsBuffer {
    def empty: TransformationsBuffer =
      TransformationsBuffer(Buffer.empty, Buffer.empty, Buffer.empty)
  }
}
