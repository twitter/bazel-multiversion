inThisBuild(
  List(
    scalaVersion := "2.12.12"
  )
)

libraryDependencies ++= List(
  "io.get-coursier" %% "coursier" % "2.0.0",
  "com.lihaoyi" %% "pprint" % "0.6.0",
  "com.lihaoyi" %% "requests" % "0.6.5",
  "org.mockito" % "mockito-all" % "1.10.19"
)
