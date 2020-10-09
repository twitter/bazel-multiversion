package multideps.outputs

import org.typelevel.paiges.Doc

object Docs {
  def attr(name: String, value: Doc): Doc =
    Doc.text(name) + Doc.text(" = ") + value
  def literal(value: String): Doc = quote + Doc.text(value) + quote
  def array(values: String*): Doc =
    Doc
      .fill(Doc.comma, values.map(Doc.text(_)))
      .bracketBy(openBracket, closeBracket)
  val blankLine = Doc.line + Doc.line
  val quote = Doc.char('"')
  val open = Doc.char('(')
  val close = Doc.char(')')
  val openBracket = Doc.char('[')
  val closeBracket = Doc.char(']')
}
