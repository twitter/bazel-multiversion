def scala212 = "2.12.12"
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
    scalaVersion := scala212,
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

lazy val multideps = project
  .in(file("multideps"))
  .settings(
    moduleName := "bazel-multideps",
    mainClass.in(Compile) := Some("multideps.Multideps"),
    libraryDependencies ++= List(
      "io.get-coursier" %% "coursier" % "2.0.0",
      "org.scalameta" %% "moped" % "0.1.2",
      "org.scalameta" %% "moped-yaml" % "0.1.2",
      "com.lihaoyi" %% "os-lib" % "0.7.1",
      "com.lihaoyi" %% "fansi" % "0.2.9",
      "com.lihaoyi" %% "pprint" % "0.6.0",
      "com.lihaoyi" %% "requests" % "0.6.5"
    )
  )
  .enablePlugins(ProtobufPlugin, NativeImagePlugin)

lazy val tests = project
  .in(file("tests"))
  .settings(
    testFrameworks := List(new TestFramework("munit.Framework")),
    libraryDependencies ++= List(
      "org.scalameta" %% "munit" % "0.7.13"
    )
  )
  .dependsOn(multideps)
