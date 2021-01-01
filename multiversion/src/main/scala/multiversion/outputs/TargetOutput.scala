package multiversion.outputs

import org.typelevel.paiges.Doc

final case class TargetOutput(
    kind: String,
    attributes: (String, Doc)*
) {
  def toDoc: Doc = {
    val arguments = Doc.intercalate(
      Doc.comma + Doc.space,
      attributes.map { case (key, value) => Doc.text(key) + Doc.char('=') + value }
    )
    Doc.text(kind) + arguments.tightBracketBy(Docs.open, Docs.close)
  }
}
