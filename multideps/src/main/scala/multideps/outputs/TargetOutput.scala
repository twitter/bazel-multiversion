package multideps.outputs

import org.typelevel.paiges.Doc

final case class TargetOutput(
    kind: String,
    attributes: (String, Doc)*
) {
  def toDoc: Doc = {
    val arguments = Doc.fill(
      Doc.comma,
      attributes.map { case (key, value) => Docs.attr(key, value) }
    )
    Doc.text(kind) + arguments.bracketBy(Docs.open, Docs.close)
  }
}
