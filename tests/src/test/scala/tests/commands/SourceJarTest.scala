package tests.commands

class SourceJarTest extends tests.BaseSuite with tests.ConfigSyntax {
  checkDeps(
    "scalatest",
    """|  - dependency: org.scalatest:scalatest_2.12:3.1.2
       |    targets: [scalatest]
       |""".stripMargin,
    queryArgs = nonEmptySrcjar("@maven//:scalatest"),
    expectedQuery = """@maven//:scalatest
         |""".stripMargin
  )
}
