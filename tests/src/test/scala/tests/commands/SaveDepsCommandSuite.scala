package tests.commands

class SaveDepsCommandSuite extends tests.BaseSuite {

  checkOutput(
    "basic",
    arguments = List("deps", "save"),
    expectedOutput = "",
    workingDirectoryLayout =
      """|/dependencies.yaml
         |scala: 2.12.12
         |dependencies:
         |  - dependency: org.scalameta::munit:0.7.9
         |  - dependency: com.google.guava:guava:29.0-jre
         |    crossLibrary:
         |      old: 27.0-jre
         |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
         |    # forceVersions:
         |    #   - com.google.guava:guava:default
         |""".stripMargin
  )

}
