package multiversion.indexes

import scala.collection.JavaConverters._
import scala.collection.mutable

import com.twitter.multiversion.Build.QueryResult
import com.twitter.multiversion.Build.Target
import multiversion.resolvers.SimpleDependency

class DependenciesIndex(query: QueryResult) {
  private val targets =
    query
      .getTargetList()
      .asScala
      .filter(_.getType() == Target.Discriminator.RULE)
      .map(TargetIndex.fromQuery)

  val byName: Map[String, TargetIndex] =
    targets.map(t => t.name -> t).toMap

  private val jars: mutable.Map[TargetIndex, Set[TargetIndex]] =
    mutable.Map.empty

  private val byDependency: Map[SimpleDependency, TargetIndex] =
    (for {
      target <- targets
      dependency <- target.dependency
    } yield dependency -> target).toMap

  def dependencies(name: String): Set[TargetIndex] = {
    byName.get(name) match {
      case None         => Set.empty
      case Some(target) => dependencies(target)
    }
  }

  def dependencies(dependency: SimpleDependency): Set[TargetIndex] = {
    byDependency.get(dependency) match {
      case None         => Set.empty
      case Some(target) => dependencies(target)
    }
  }

  def dependencies(target: TargetIndex): Set[TargetIndex] = {
    def work(target: TargetIndex): Set[TargetIndex] = {
      if (isTransitive(target)) target.deps.flatMap(byName.get).flatMap(dependencies).toSet
      else Set(target)
    }
    jars.getOrElseUpdate(target, work(target))
  }

  private def isTransitive(target: TargetIndex): Boolean =
    !target.name.startsWith("@maven//:_")
}
