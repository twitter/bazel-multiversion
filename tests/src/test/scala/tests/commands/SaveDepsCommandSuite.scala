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
         |    crossVersions:
         |      - name: old
         |        version: 27.1-jre
         |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
         |    forceVersions:
         |      - com.google.guava:guava:default
         |""".stripMargin
  )

}
