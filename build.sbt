lazy val V = new {
  def scala212 = "2.12.17"
  def moped = "0.1.9"
}

ThisBuild / organization := "com.twitter.multiversion"
ThisBuild / version := {
  val snapshotV = "0.1.0-SNAPSHOT"
  val old = (ThisBuild / version).value
  (sys.env.get("BUILD_VERSION") orElse sys.props.get("sbt.build.version")) match {
    case Some(v) => v
    case _ =>
      if ((ThisBuild / isSnapshot).value) snapshotV
      else old
  }
}
ThisBuild / homepage := Some(url("https://github.com/twitter/bazel-multiversion"))
ThisBuild / licenses := List(
  "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
)
ThisBuild / developers := List(
  Developer(
    "eed3si9n",
    "Eugene Yokota",
    "@eed3si9n",
    url("https://eed3si9n.com")
  ),
  Developer(
    "Duhemm",
    "Martin Duhem",
    "@Duhemm",
    url("https://github.com/Duhemm")
  ),
  Developer(
    "AngelaGuardia",
    "Angela Guardia",
    "@AngelaGuardia",
    url("https://github.com/AngelaGuardia")
  ),
  Developer(
    "olafurpg",
    "Ólafur Páll Geirsson",
    "olafurpg@gmail.com",
    url("https://geirsson.com")
  ),
)
ThisBuild / scalaVersion := V.scala212
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.4.2"
ThisBuild / scalafixCaching := true
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
ThisBuild / scalacOptions ++= List(
  "-Ywarn-unused:imports",
  "-Yrangepos"
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
      "io.get-coursier" %% "coursier" % "2.1.0-RC1",
      "io.get-coursier" %% "versions" % "0.3.1",
      "org.scalameta" %% "moped" % V.moped,
      "org.scalameta" %% "moped-yaml" % V.moped,
      "com.lihaoyi" %% "os-lib" % "0.7.1",
      "com.lihaoyi" %% "fansi" % "0.2.9",
      "com.lihaoyi" %% "pprint" % "0.6.0",
      "com.lihaoyi" %% "requests" % "0.6.5",
      "com.eed3si9n.starlark" % "starlark" % "4.2.1",
    ),
    buildInfoPackage := "multiversion",
    buildInfoKeys := Seq[BuildInfoKey](
      version
    ),
    name.in(NativeImage) := "multiversion",
    nativeImageVersion := "22.0",
    nativeImageOptions ++= List(
      "--initialize-at-build-time=scala.collection.immutable.VM",
      "--initialize-at-run-time=multiversion,moped.cli.Environment$",
      s"-H:ReflectionConfigurationFiles=${baseDirectory.value}/reflect.config",
      "--enable-http",
      "--enable-https",
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
