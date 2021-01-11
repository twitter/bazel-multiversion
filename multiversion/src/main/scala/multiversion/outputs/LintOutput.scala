package multiversion.outputs

import multiversion.resolvers.SimpleDependency
import multiversion.resolvers.SimpleModule
import org.typelevel.paiges.Doc

final case class LintOutput(
    root: String,
    conflicts: Map[SimpleModule, Set[SimpleDependency]],
    isFailure: Boolean
) {
  def toDoc: Doc = {
    // Sort the conflicts to ensure the output is stable.
    val sortedConflicts = conflicts
      .map {
        case (module, deps) =>
          val versionsDoc = Docs.array(deps.toList.map(_.version).sorted: _*)
          module.repr -> versionsDoc
      }
      .toList
      .sortBy(_._1)

    Docs.literal(root) + Docs.colon + Doc.space + Docs.obj(
      List(
        "failure" -> Doc.str(isFailure),
        "conflicts" -> Docs.obj(sortedConflicts)
      )
    )
  }
}
