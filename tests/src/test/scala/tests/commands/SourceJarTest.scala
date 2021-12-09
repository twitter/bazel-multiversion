package tests.commands

class SourceJarTest extends tests.BaseSuite with tests.ConfigSyntax {
  checkDeps(
    "scalatest",
    """|  - dependency: org.scalatest:scalatest_2.12:3.1.2
       |    targets: [scalatest]
       |""".stripMargin,
    queryArgs = nonEmptySrcjar("@maven//:scalatest"),
    expectedQuery = """@maven//:_org.scala-lang.modules_scala-xml_2.12_1.2.0
         |@maven//:_org.scala-lang_scala-library_2.12.11
         |@maven//:_org.scala-lang_scala-reflect_2.12.11
         |@maven//:_org.scalactic_scalactic_2.12_3.1.2
         |@maven//:scalatest
         |""".stripMargin
  )
}
