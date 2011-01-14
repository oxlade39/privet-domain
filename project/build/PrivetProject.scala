import sbt._

class PrivetProject(info: ProjectInfo) extends DefaultProject(info) with AkkaProject {
  val mavenLocal = "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"
  val scalaSnapshots = "Scala Snapshots repo" at "http://scala-tools.org/repo-snapshots"

  override def includeTest(s: String) = {
    s.endsWith("Spec") || s.endsWith("Specification")
  }

  //  val betfairDemo = "org.betfair" % "betfair-demo" % "2.0"

  val marketSim = "dk.bettingai" % "market-data-collector" % "1.3-SNAPSHOT" withSources


  override def ivyXML =
    <dependencies>
      <dependency org="dk.bettingai" name="market-data-collector" rev="1.3-SNAPSHOT">
        <exclude org="org.slf4j" />
      </dependency>
    </dependencies>

  val specs = "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7-SNAPSHOT" % "test" withSources
  val junit = "junit" % "junit" % "4.7" % "test" withSources


  // val mockito = "org.mockito" % "mockito-all" % "1.8.0" % "test" withSources
  //   	val hamcrest = "org.hamcrest" % "hamcrest-all" % "1.1" % "test" withSources

  // val akkaFsm = akkaModule("fsm")

}
