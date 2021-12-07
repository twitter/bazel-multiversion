package multiversion.outputs

import org.typelevel.paiges.Doc

object Docs {
  def attr(name: String, value: Doc): Doc =
    Doc.text(name) + Doc.text(" = ") + value
  def literal(value: String): Doc = quote + Doc.text(value) + quote
  def array(values: String*): Doc =
    apply(openBracket, closeBracket, values.map(literal))
  def apply(open: Doc, close: Doc, values: Iterable[Doc]): Doc =
    Doc
      .intercalate(Doc.comma + Doc.lineOrSpace, values)
      .tightBracketBy(open, close)
  val blankLine: Doc = Doc.line + Doc.line
  val quote: Doc = Doc.char('"')
  val open: Doc = Doc.char('(')
  val close: Doc = Doc.char(')')
  val openBrace: Doc = Doc.char('{')
  val closeBrace: Doc = Doc.char('}')
  val openBracket: Doc = Doc.char('[')
  val closeBracket: Doc = Doc.char(']')
  val colon: Doc = Doc.char(':')
  val dash: Doc = Doc.char('-')
  def obj(entries: Iterable[(String, Doc)]): Doc = {
    val mappings = entries.map { case (key, value) =>
      literal(key) + colon + Doc.space + value
    }
    Doc
      .intercalate(Doc.comma + Doc.space, mappings)
      .tightBracketBy(openBrace, closeBrace)
  }
  object emoji {
    val success: Doc = colors.green + Doc.text("✔ ") + colors.reset
    val error: Doc = Doc.text("❗")
    val info: Doc = colors.yellow + Doc.text("ℹ ") + colors.reset
  }
  def successMessage(message: String): String =
    (emoji.success + Doc.text(message)).render(1000)
  def infoMessage(message: String): String =
    (emoji.info + Doc.text(message)).render(1000)
  object colors {
    val bold: Doc = Doc.zeroWidth(Console.BOLD)
    val green: Doc = Doc.zeroWidth(Console.GREEN)
    val yellow: Doc = Doc.zeroWidth(Console.YELLOW)
    val reset: Doc = Doc.zeroWidth(Console.RESET)
  }
}
