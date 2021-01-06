package multiversion.outputs

import multiversion.resolvers.SimpleModule
import org.typelevel.paiges.Doc

final case class LintOutput(
    root: String,
    conflicts: Map[SimpleModule, Set[String]]
) {
  def toDoc: Doc = {
    // Sort the conflicts to ensure the output is stable.
    val sortedConflicts = conflicts
      .map { case (module, versions) => module.repr -> versions.toList.sorted }
      .toList
      .sortBy(_._1)
    val conflictDocs = sortedConflicts.map {
      case (module, versions) =>
        Doc.spaces(2) + Docs.dash + Doc.space + Docs.literal(module) + Docs.colon + Doc.space + Docs
          .array(versions: _*)
    }
    Docs.literal(root) + Docs.colon + Doc.line + Doc.intercalate(Doc.line, conflictDocs)
  }
}
