package tests.commands

class SaveDepsCommandSuite extends tests.BaseSuite {

  checkOutput(
    "basic",
    arguments = List("deps", "save"),
    expectedOutput = "",
    workingDirectoryLayout =
      """|/3rdparty.yaml
         |scala: 2.12.12
         |dependencies:
         |  - dependency: com.google.guava:guava:29.0-jre
         |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
         |""".stripMargin
  )

}
