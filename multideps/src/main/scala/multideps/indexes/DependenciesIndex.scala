package multideps.indexes

import scala.collection.JavaConverters._
import org.scalameta.bazel_multideps.Build.QueryResult
import scala.collection.mutable
import org.scalameta.bazel_multideps.Build.Target
import java.text.SimpleDateFormat
import multideps.resolvers.SimpleDependency

class DependenciesIndex(query: QueryResult) {
  private val deps: mutable.LinkedHashMap[String, Set[TargetIndex]] =
    mutable.LinkedHashMap.empty
  private val targets = query
    .getTargetList()
    .asScala
    .filter(_.getType() == Target.Discriminator.RULE)
    .map(TargetIndex.fromQuery)
  private val byName: Map[String, TargetIndex] =
    targets.map(t => t.name -> t).toMap
  val byDependency: Map[SimpleDependency, TargetIndex] = targets
    .flatMap(t => t.dependency.map(d => d -> t))
    .toMap

  def dependencies(dependency: SimpleDependency): Set[TargetIndex] = {
    byDependency.get(dependency) match {
      case Some(target) => dependencies(target)
      case None => Set.empty
    }
  }
  def dependencies(target: String): Set[TargetIndex] = {
    dependencies(byName(target))
  }
  def dependencies(target: TargetIndex): Set[TargetIndex] = {
    deps.getOrElseUpdate(
      target.name,
      Set(target) ++ target.deps.flatMap(dependencies)
    )
  }
}
