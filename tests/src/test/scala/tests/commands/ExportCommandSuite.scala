package tests.commands

import munit.TestOptions

class ExportCommandSuite extends tests.BaseSuite {

  def checkDeps(name: TestOptions, deps: String)(implicit
      loc: munit.Location
  ): Unit = {
    test(name) {
      checkCommand(
        arguments = List("export", "--no-use-ansi-output"),
        expectedExit = 0,
        expectedOutput =
          """|✔ Generated '/workingDirectory/3rdparty/jvm_deps.bzl'
             |""".stripMargin,
        workingDirectoryLayout = s"""|/3rdparty.yaml
                                     |scala: 2.12.12
                                     |dependencies:
                                     |$deps
                                     |$bazelWorkspace
                                     |""".stripMargin
      )
    }

  }

  checkDeps(
    "basic",
    """|  - dependency: com.google.guava:guava:29.0-jre
       |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
       |""".stripMargin
  )
  checkDeps(
    "libthrift".only,
    """|  - dependency: org.apache.thrift:libthrift:0.10.0
       |""".stripMargin
  )
}
