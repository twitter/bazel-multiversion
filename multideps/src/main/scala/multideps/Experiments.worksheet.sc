import org.typelevel.paiges.Doc
val d = Doc.tabulate(
  ' ',
  " ",
  List(
    "a" -> Doc.text("hello"),
    "bb" -> Doc.text("goodbye"),
    "ccc" -> Doc.paragraph("yeah! This is very good")
  )
)

println(d.render(40))
d.render(10)
