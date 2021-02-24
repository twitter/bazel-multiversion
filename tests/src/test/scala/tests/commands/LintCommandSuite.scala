package tests.commands

import tests.BaseSuite

class LintCommandSuite extends BaseSuite {
  test("basic") {
    checkCommand(
      arguments = exportCommand,
      expectedOutput = """|✔ Generated '/workingDirectory/3rdparty/jvm_deps.bzl'
                          |""".stripMargin,
      workingDirectoryLayout = s"""|/3rdparty.yaml
                                   |scala: 2.12.12
                                   |dependencies:
                                   |  - dependency: com.google.guava:guava:29.0-jre
                                   |  - dependency: com.google.guava:guava:27.1-jre
                                   |    targets:
                                   |      - guava27
                                   |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
                                   |    dependencies:
                                   |      - guava27
                                   |$bazelWorkspace
                                   |""".stripMargin
    )
  }

  test("classifier") {
    checkCommand(
      arguments = exportCommand,
      expectedOutput = """|✔ Generated '/workingDirectory/3rdparty/jvm_deps.bzl'
                          |""".stripMargin,
      workingDirectoryLayout = s"""|/3rdparty.yaml
                                   |scala: 2.12.12
                                   |dependencies:
                                   |  - dependency: org.apache.kafka:kafka_2.12:2.4.1
                                   |    versionScheme: pvp
                                   |  - dependency: org.apache.kafka:kafka_2.12:2.4.1
                                   |    versionScheme: pvp
                                   |    classifier: test
                                   |/foo/BUILD
                                   |load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary")
                                   |
                                   |scala_library(
                                   |  name = "foo",
                                   |  srcs = [],
                                   |  deps = [
                                   |    "@maven//:org.apache.kafka_kafka_2.12_2.4.1",
                                   |    "@maven//:org.apache.kafka_kafka_2.12_2.4.1_test",
                                   |  ],
                                   |)
                                   |$bazelWorkspace
                                   |""".stripMargin,
      lintArgs = List("lint", "//foo:foo"),
    )
  }
}
