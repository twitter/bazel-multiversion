package tests.commands

import tests.BaseSuite

class LintCommandSuite extends BaseSuite {
  test("basic") {
    checkCommand(
      arguments = List("export", "--no-use-ansi-output"),
      expectedOutput =
        """|info: generated: /workingDirectory/3rdparty/jvm_deps.bzl
           |""".stripMargin,
      workingDirectoryLayout = s"""|/3rdparty.yaml
                                   |scala: 2.12.12
                                   |dependencies:
                                   |  - dependency: com.google.guava:guava:29.0-jre
                                   |  - dependency: com.google.guava:guava:27.1-jre
                                   |    targets: [guava27]
                                   |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
                                   |    dependencies: [guava27]
                                   |$bazelWorkspace
                                   |""".stripMargin
    )
  }
}
