name := "Privet Domain"

version := "1.0"

organization := "org.doxla"

scalaVersion := "2.8.1"

offline := true

resolvers ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
)

libraryDependencies ++= Seq(
  "org.mockito" % "mockito-all" % "1.8.4" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
  "org.scala-tools.testing" % "specs_2.8.1" % "1.6.9-SNAPSHOT" % "test" withSources,
  "org.scalatest" % "scalatest" % "1.2" % "test" withSources,
  "junit" % "junit" % "4.7" % "test"
)