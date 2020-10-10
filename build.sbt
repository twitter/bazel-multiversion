lazy val V = new {
  def scala212 = "2.12.12"
  def moped = "0.1.2+5-4211a71e-SNAPSHOT"
}
inThisBuild(
  List(
    organization := "org.scalameta",
    homepage := Some(url("https://github.com/scalameta/bazel-multideps")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers := List(
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
    )
  )
)

disablePlugins(BloopPlugin)

lazy val multideps = project
  .in(file("multideps"))
  .settings(
    moduleName := "bazel-multideps",
    mainClass.in(Compile) := Some("multideps.Multideps"),
    fork := true,
    javaOptions ++= List(
      "-Xmx8g",
      "-XX:+UseG1GC"
    ),
    libraryDependencies ++= List(
      "com.google.guava" % "guava" % "29.0-jre",
      "io.get-coursier" %% "coursier" % "2.0.0",
      "org.typelevel" %% "paiges-core" % "0.3.2",
      "org.scalameta" %% "moped" % V.moped,
      "org.scalameta" %% "moped-yaml" % V.moped,
      "com.lihaoyi" %% "os-lib" % "0.7.1",
      "com.lihaoyi" %% "fansi" % "0.2.9",
      "com.lihaoyi" %% "pprint" % "0.6.0",
      "com.lihaoyi" %% "requests" % "0.6.5"
    ),
    buildInfoPackage := "multideps",
    buildInfoKeys := Seq[BuildInfoKey](
      version
    ),
    name.in(NativeImage) := "bm",
    nativeImageOptions ++= List(
      "--initialize-at-build-time=scala.collection.immutable.VM",
      "--initialize-at-run-time=multideps,moped.cli.Environment$",
      "-H:+TraceClassInitialization",
      "--report-unsupported-elements-at-runtime"
    )
  )
  .enablePlugins(ProtobufPlugin, NativeImagePlugin, BuildInfoPlugin)

lazy val tests = project
  .in(file("tests"))
  .settings(
    testFrameworks := List(new TestFramework("munit.Framework")),
    libraryDependencies ++= List(
      "org.scalameta" %% "munit" % "0.7.13",
      "org.scalameta" %% "moped-testkit" % V.moped
    )
  )
  .dependsOn(multideps)
