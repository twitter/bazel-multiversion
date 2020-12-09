package tests.commands

class ExportCommandSuite extends tests.BaseSuite {

  checkDeps(
    "basic",
    """|  - dependency: com.google.guava:guava:29.0-jre
       |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
       |""".stripMargin,
    expectedOutput =
      """|/workingDirectory/3rdparty.yaml:3:16 error: transitive dependency 'com.google.guava:guava' has conflicting versions.
         |       found versions: 27.1-jre
         |    declared versions: 29.0-jre
         |      popular version: 29.0-jre
         |              ok deps: com.google.guava:guava:29.0-jre
         |           ok targets:
         |       unpopular deps: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
         |    unpopular targets:
         |  To fix this problem, add 'dependencies = ''' to the root dependencies OR add 'targets' to the transitive dependency.
         |  - dependency: com.google.guava:guava:29.0-jre
         |                ^""".stripMargin,
    expectedExit = 1
  )

  checkDeps(
    "libthrift",
    """|  - dependency: org.apache.thrift:libthrift:0.10.0
       |""".stripMargin
  )

  checkDeps(
    "version_scheme",
    s"""|  - dependency: com.lihaoyi:fansi_2.12:0.2.8
        |    versionScheme: pvp
        |  - dependency: com.lihaoyi:pprint_2.12:0.5.6
        |${scalaLibrary("MyApp.scala", "object MyApp { val x = 42 }")}
        |""".stripMargin
  )

  checkDeps(
    "duplicate",
    s"""|  - dependency: org.slf4j:slf4j-log4j12:1.6.1
        |  - dependency: org.slf4j:slf4j-log4j12:1.6.4
        |""".stripMargin
  )

  checkDeps(
    "classifier",
    s"""|  - dependency: jline:jline:2.14.6
        |  - dependency: jline:jline:2.14.6
        |    classifier: test
        |""".stripMargin
  )
}
