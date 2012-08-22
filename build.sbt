name := "Privet Domain"

version := "1.0"

organization := "org.doxla"

resolvers ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  ScalaToolsSnapshots
)

libraryDependencies ++= Seq(
  "org.mockito" % "mockito-all" % "1.8.4" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
  "org.scalatest" %% "scalatest" % "1.8" % "test",
  "junit" % "junit" % "4.7" % "test"
)