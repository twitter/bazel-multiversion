package tests.commands

class SaveDepsCommandSuite extends tests.BaseSuite {

  checkOutput(
    "basic",
    arguments = List("deps", "save"),
    expectedOutput = "",
    workingDirectoryLayout = """|/dependencies.yaml
                                |scala: 2.12.12
                                |dependencies:
                                |  - "org.scalameta::munit:0.7.9"
                                |  - "com.google.guava:guava:29.0-jre"
                                |  - "org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0"
                                |""".stripMargin
  )

}
