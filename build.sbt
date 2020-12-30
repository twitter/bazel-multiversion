lazy val V = new {
  def scala212 = "2.12.12"
  def moped = "0.1.8"
}

inThisBuild(
  List(
    organization := "org.scalameta",
    homepage := Some(url("https://github.com/twitter/bazel-multiversion")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers := List(
      Developer(
        "eed3si9n",
        "Eugene Yokota",
        "@eed3si9n",
        url("https://eed3si9n.com")
      ),
      Developer(
        "olafurpg",
        "Ólafur Páll Geirsson",
        "olafurpg@gmail.com",
        url("https://geirsson.com")
      )
    ),
    scalaVersion := V.scala212,
    scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.2",
    scalafixCaching := true,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions ++= List(
      "-Ywarn-unused:imports",
      "-Yrangepos"
    ),
    version := "0.1.0-SNAPSHOT"
  )
)

lazy val multiversion = project
  .in(file("multiversion"))
  .enablePlugins(ProtobufPlugin, NativeImagePlugin, BuildInfoPlugin)
  .settings(
    name := "bazel-multiversion",
    mainClass.in(Compile, packageBin) := Some("multiversion.MultiVersion"),
    mainClass.in(Compile) := Some("multiversion.MultiVersion"),
    fork := true,
    javaOptions ++= List(
      "-Xmx8g",
      "-XX:+UseG1GC"
    ),
    baseDirectory.in(run) := {
      baseDirectory.in(ThisBuild).value / "multiversion-example"
    },
    libraryDependencies ++= List(
      "io.get-coursier" %% "coursier" % "2.0.0",
      "io.get-coursier" %% "versions" % "0.3.0",
      "org.scalameta" %% "moped" % V.moped,
      "org.scalameta" %% "moped-yaml" % V.moped,
      "com.lihaoyi" %% "os-lib" % "0.7.1",
      "com.lihaoyi" %% "fansi" % "0.2.9",
      "com.lihaoyi" %% "pprint" % "0.6.0",
      "com.lihaoyi" %% "requests" % "0.6.5"
    ),
    buildInfoPackage := "multiversion",
    buildInfoKeys := Seq[BuildInfoKey](
      version
    ),
    name.in(NativeImage) := "bm",
    nativeImageOptions ++= List(
      "--initialize-at-build-time=scala.collection.immutable.VM",
      "--initialize-at-run-time=multiversion,moped.cli.Environment$",
      "-H:+TraceClassInitialization",
      "--report-unsupported-elements-at-runtime"
    )
  )

lazy val tests = project
  .in(file("tests"))
  .dependsOn(multiversion)
  .settings(
    Test / parallelExecution := false,
    testFrameworks := List(new TestFramework("munit.Framework")),
    libraryDependencies ++= List(
      "org.scalameta" %% "munit" % "0.7.13",
      "org.scalameta" %% "moped-testkit" % V.moped
    )
  )
