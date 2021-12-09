package tests.commands

import multiversion.commands.ResolveCommand
import munit.TestOptions
import tests.BaseSuite
import tests.ConfigSyntax

class ResolveCommandTest extends BaseSuite with ConfigSyntax {
  testJarsResults(
    "basic",
    deps(
      dep("org.reflections:reflections:0.9.11")
        .target("reflections"),
      dep("com.google.inject:guice:4.0")
        .target("guice")
    ),
    List("@maven//:reflections", "@maven//:guice"),
    query = "foo",
    expectedOutput = expectedOutput1,
  )

  testJarsResults(
    "tree",
    deps(
      dep("org.reflections:reflections:0.9.11")
        .target("reflections"),
      dep("com.google.inject:guice:4.0")
        .target("guice")
    ),
    List("@maven//:reflections", "@maven//:guice"),
    query = "foo",
    tree = true,
    expectedOutput = expectedOutput2,
  )

  testJarsResults(
    "3rdparty",
    deps(
      dep("org.reflections:reflections:0.9.11")
        .target("reflections"),
      dep("com.google.inject:guice:4.0")
        .target("guice")
    ),
    List("@maven//:reflections", "@maven//:guice"),
    query = "foo",
    `3rdparty` = true,
    // this can't be tested since 3rdparty aliases are not created here
    expectedOutput = Nil,
  )

  lazy val expectedOutput1: List[String] = List(
    "@maven//:aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar",
    "@maven//:aopalliance/aopalliance/1.0/aopalliance-1.0.jar",
    "@maven//:com.google.guava/guava/16.0.1/guava-16.0.1-sources.jar",
    "@maven//:com.google.guava/guava/16.0.1/guava-16.0.1.jar",
    "@maven//:com.google.guava/guava/20.0/guava-20.0-sources.jar",
    "@maven//:com.google.guava/guava/20.0/guava-20.0.jar",
    "@maven//:com.google.inject/guice/4.0/guice-4.0-sources.jar",
    "@maven//:com.google.inject/guice/4.0/guice-4.0.jar",
    "@maven//:javax.inject/javax.inject/1/javax.inject-1-sources.jar",
    "@maven//:javax.inject/javax.inject/1/javax.inject-1.jar",
    "@maven//:org.javassist/javassist/3.21.0-GA/javassist-3.21.0-GA-sources.jar",
    "@maven//:org.javassist/javassist/3.21.0-GA/javassist-3.21.0-GA.jar",
    "@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11-sources.jar",
    "@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11.jar",
  )

  lazy val expectedOutput2: List[String] = List(
    "digraph mygraph {",
    "  node [shape=box];",
    "  \"//foo:foo\"",
    "  \"//foo:foo\" -> \"@maven//:guice\"",
    "  \"//foo:foo\" -> \"@maven//:reflections\"",
    "  \"@maven//:reflections\"",
    "  \"@maven//:reflections\" -> \"@maven//:org.reflections_reflections_0.9.11_132265592\"",
    "  \"@maven//:reflections\" -> \"@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11-sources.jar\"",
    "  \"@maven//:reflections\" -> \"@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11.jar\"",
    "  \"@maven//:org.reflections_reflections_0.9.11_132265592\"",
    "  \"@maven//:org.reflections_reflections_0.9.11_132265592\" -> \"@maven//:_com.google.guava_guava_20.0\"",
    "  \"@maven//:org.reflections_reflections_0.9.11_132265592\" -> \"@maven//:_org.javassist_javassist_3.21.0-GA\"",
    "  \"@maven//:org.reflections_reflections_0.9.11_132265592\" -> \"@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11.jar\"",
    "  \"@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11-sources.jar\"",
    "  \"@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11-sources.jar\" -> \"@maven//:genrules/org.reflections_reflections_0.9.11_sources\"",
    "  \"@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11.jar\"",
    "  \"@maven//:org.reflections/reflections/0.9.11/reflections-0.9.11.jar\" -> \"@maven//:genrules/org.reflections_reflections_0.9.11\"",
    "  \"@maven//:genrules/org.reflections_reflections_0.9.11\"",
    "  \"@maven//:genrules/org.reflections_reflections_0.9.11_sources\"",
    "  \"@maven//:_com.google.guava_guava_20.0\"",
    "  \"@maven//:_com.google.guava_guava_20.0\" -> \"@maven//:com.google.guava/guava/20.0/guava-20.0-sources.jar\"",
    "  \"@maven//:_com.google.guava_guava_20.0\" -> \"@maven//:com.google.guava/guava/20.0/guava-20.0.jar\"",
    "  \"@maven//:com.google.guava/guava/20.0/guava-20.0-sources.jar\"",
    "  \"@maven//:com.google.guava/guava/20.0/guava-20.0-sources.jar\" -> \"@maven//:genrules/com.google.guava_guava_20.0_sources\"",
    "  \"@maven//:com.google.guava/guava/20.0/guava-20.0.jar\"",
    "  \"@maven//:com.google.guava/guava/20.0/guava-20.0.jar\" -> \"@maven//:genrules/com.google.guava_guava_20.0\"",
    "  \"@maven//:genrules/com.google.guava_guava_20.0\"",
    "  \"@maven//:genrules/com.google.guava_guava_20.0_sources\"",
    "  \"@maven//:guice\"",
    "  \"@maven//:guice\" -> \"@maven//:com.google.inject_guice_4.0_-701794567\"",
    "  \"@maven//:guice\" -> \"@maven//:com.google.inject/guice/4.0/guice-4.0-sources.jar\"",
    "  \"@maven//:guice\" -> \"@maven//:com.google.inject/guice/4.0/guice-4.0.jar\"",
    "  \"@maven//:com.google.inject_guice_4.0_-701794567\"",
    "  \"@maven//:com.google.inject_guice_4.0_-701794567\" -> \"@maven//:_aopalliance_aopalliance_1.0\"",
    "  \"@maven//:com.google.inject_guice_4.0_-701794567\" -> \"@maven//:_com.google.guava_guava_16.0.1\"",
    "  \"@maven//:com.google.inject_guice_4.0_-701794567\" -> \"@maven//:_javax.inject_javax.inject_1\"",
    "  \"@maven//:com.google.inject_guice_4.0_-701794567\" -> \"@maven//:com.google.inject/guice/4.0/guice-4.0.jar\"",
    "  \"@maven//:com.google.inject/guice/4.0/guice-4.0-sources.jar\"",
    "  \"@maven//:com.google.inject/guice/4.0/guice-4.0-sources.jar\" -> \"@maven//:genrules/com.google.inject_guice_4.0_sources\"",
    "  \"@maven//:com.google.inject/guice/4.0/guice-4.0.jar\"",
    "  \"@maven//:com.google.inject/guice/4.0/guice-4.0.jar\" -> \"@maven//:genrules/com.google.inject_guice_4.0\"",
    "  \"@maven//:genrules/com.google.inject_guice_4.0\"",
    "  \"@maven//:genrules/com.google.inject_guice_4.0_sources\"",
    "  \"@maven//:_com.google.guava_guava_16.0.1\"",
    "  \"@maven//:_com.google.guava_guava_16.0.1\" -> \"@maven//:com.google.guava/guava/16.0.1/guava-16.0.1-sources.jar\"",
    "  \"@maven//:_com.google.guava_guava_16.0.1\" -> \"@maven//:com.google.guava/guava/16.0.1/guava-16.0.1.jar\"",
    "  \"@maven//:_aopalliance_aopalliance_1.0\"",
    "  \"@maven//:_aopalliance_aopalliance_1.0\" -> \"@maven//:aopalliance/aopalliance/1.0/aopalliance-1.0.jar\"",
    "  \"@maven//:_aopalliance_aopalliance_1.0\" -> \"@maven//:aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar\"",
    "  \"@maven//:aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar\"",
    "  \"@maven//:aopalliance/aopalliance/1.0/aopalliance-1.0-sources.jar\" -> \"@maven//:genrules/aopalliance_aopalliance_1.0_sources\"",
    "  \"@maven//:aopalliance/aopalliance/1.0/aopalliance-1.0.jar\"",
    "  \"@maven//:aopalliance/aopalliance/1.0/aopalliance-1.0.jar\" -> \"@maven//:genrules/aopalliance_aopalliance_1.0\"",
    "  \"@maven//:genrules/aopalliance_aopalliance_1.0\"",
    "  \"@maven//:genrules/aopalliance_aopalliance_1.0_sources\"",
    "  \"@maven//:_javax.inject_javax.inject_1\"",
    "  \"@maven//:_javax.inject_javax.inject_1\" -> \"@maven//:javax.inject/javax.inject/1/javax.inject-1-sources.jar\"",
    "  \"@maven//:_javax.inject_javax.inject_1\" -> \"@maven//:javax.inject/javax.inject/1/javax.inject-1.jar\"",
    "  \"@maven//:javax.inject/javax.inject/1/javax.inject-1-sources.jar\"",
    "  \"@maven//:javax.inject/javax.inject/1/javax.inject-1-sources.jar\" -> \"@maven//:genrules/javax.inject_javax.inject_1_sources\"",
    "  \"@maven//:javax.inject/javax.inject/1/javax.inject-1.jar\"",
    "  \"@maven//:javax.inject/javax.inject/1/javax.inject-1.jar\" -> \"@maven//:genrules/javax.inject_javax.inject_1\"",
    "  \"@maven//:genrules/javax.inject_javax.inject_1\"",
    "  \"@maven//:genrules/javax.inject_javax.inject_1_sources\"",
    "  \"@maven//:_org.javassist_javassist_3.21.0-GA\"",
    "  \"@maven//:_org.javassist_javassist_3.21.0-GA\" -> \"@maven//:org.javassist/javassist/3.21.0-GA/javassist-3.21.0-GA-sources.jar\"",
    "  \"@maven//:_org.javassist_javassist_3.21.0-GA\" -> \"@maven//:org.javassist/javassist/3.21.0-GA/javassist-3.21.0-GA.jar\"",
    "  \"@maven//:org.javassist/javassist/3.21.0-GA/javassist-3.21.0-GA-sources.jar\"",
    "  \"@maven//:org.javassist/javassist/3.21.0-GA/javassist-3.21.0-GA-sources.jar\" -> \"@maven//:genrules/org.javassist_javassist_3.21.0-GA_sources\"",
    "  \"@maven//:org.javassist/javassist/3.21.0-GA/javassist-3.21.0-GA.jar\"",
    "  \"@maven//:org.javassist/javassist/3.21.0-GA/javassist-3.21.0-GA.jar\" -> \"@maven//:genrules/org.javassist_javassist_3.21.0-GA\"",
    "  \"@maven//:genrules/org.javassist_javassist_3.21.0-GA\"",
    "  \"@maven//:genrules/org.javassist_javassist_3.21.0-GA_sources\"",
    "  \"@maven//:com.google.guava/guava/16.0.1/guava-16.0.1-sources.jar\"",
    "  \"@maven//:com.google.guava/guava/16.0.1/guava-16.0.1-sources.jar\" -> \"@maven//:genrules/com.google.guava_guava_16.0.1_sources\"",
    "  \"@maven//:com.google.guava/guava/16.0.1/guava-16.0.1.jar\"",
    "  \"@maven//:com.google.guava/guava/16.0.1/guava-16.0.1.jar\" -> \"@maven//:genrules/com.google.guava_guava_16.0.1\"",
    "  \"@maven//:genrules/com.google.guava_guava_16.0.1\"",
    "  \"@maven//:genrules/com.google.guava_guava_16.0.1_sources\"",
    "}",
  )

  private def testJarsResults(
      name: TestOptions,
      deps: String,
      combine: List[String],
      query: String,
      tree: Boolean = false,
      `3rdparty`: Boolean = false,
      extraBuild: String = "",
      expectedExitCode: Int = 0,
      expectedOutput: List[String] = Nil,
      tags: List[String] = Nil,
  ): Unit = {
    val workingDirectoryLayout = s"""|/3rdparty.yaml
                                     |scala: 2.12.1403772
                                     |dependencies:
                                     |$deps
                                     |${bazelWorkspace("")}
                                     |/foo/BUILD
                                     |load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library")
                                     |
                                     |scala_library(
                                     |  name = "foo",
                                     |  srcs = [],
                                     |  deps = ${combine.mkString("[\"", "\", \"", "\"]")},
                                     |  tags = ${tags.mkString("[\"", "\", \"", "\"]")},
                                     |)
                                     |$extraBuild""".stripMargin
    test(name) {
      checkCommand(
        arguments = exportCommand ++ List("--no-lint"),
        expectedExit = 0,
        expectedOutput = defaultExpectedOutput,
        workingDirectoryLayout = workingDirectoryLayout
      )
      val obtainedResult =
        ResolveCommand(
          tree = tree,
          `3rdparty` = `3rdparty`,
          targets = List(query),
          app = app()
        ).runCustomQuery()
      assertEquals(obtainedResult.get.sorted, expectedOutput.sorted)
    }
  }
}
