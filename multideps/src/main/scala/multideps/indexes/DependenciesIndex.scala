package multideps.indexes

import scala.collection.JavaConverters._
import org.scalameta.bazel_multideps.Build.QueryResult
import scala.collection.mutable
import org.scalameta.bazel_multideps.Build.Target
import java.{util => ju}

class DependenciesIndex(query: QueryResult) {
  private val deps: mutable.LinkedHashMap[String, List[TargetIndex]] =
    mutable.LinkedHashMap.empty
  val targets = query
    .getTargetList()
    .asScala
    .filter(_.getType() == Target.Discriminator.RULE)
    .map(TargetIndex.fromQuery)
  val byName = targets.map(t => t.name -> t).toMap
  def dependencies(target: String): List[TargetIndex] = {
    def bfs(t: String): Iterable[TargetIndex] = {
      val result = mutable.LinkedHashSet.empty[TargetIndex]
      val seen = mutable.Set.empty[String]
      val q = new ju.ArrayDeque[String]
      q.add(t)
      while (!q.isEmpty()) {
        val curr = byName(q.pop())
        result += curr
        // TODO(olafur): cache result here
        curr.deps.foreach { dep =>
          if (!seen.contains(dep)) {
            seen += dep
            q.push(dep)
          }
        }
      }
      result
    }
    deps.get(target) match {
      case Some(cached) =>
        cached
      case None =>
        val result = bfs(target).toList
        deps(target) = result
        result
    }
  }
}
