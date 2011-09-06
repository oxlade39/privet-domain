name := "privet-akka"

version := "1.0"

organization := "org.doxla"

scalaVersion := "2.8.1"

resolvers ++= Seq(
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
  "Scala Snapshots repo" at "http://scala-tools.org/repo-snapshots",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases"
)

libraryDependencies ++= Seq(
  "se.scalablesolutions.akka" % "akka-actor" % "1.0-RC3",
  "se.scalablesolutions.akka" % "akka-typed-actor" % "1.0-RC3",
  "org.betfair" % "betfair-demo" % "2.0" withSources,
  "org.mockito" % "mockito-all" % "1.8.4" % "test",
  "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
  "org.scala-tools.testing" % "specs_2.8.1" % "1.6.9-SNAPSHOT" % "test" withSources,
  "org.scalatest" % "scalatest" % "1.2" % "test" withSources,
  "junit" % "junit" % "4.7" % "test"
)