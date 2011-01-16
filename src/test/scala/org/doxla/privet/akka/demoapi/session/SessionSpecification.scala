package org.doxla.privet.akka.demoapi.session

import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

class DemoAPIBetfairSessionSpecification extends FlatSpec with MustMatchers {

  "DemoAPIBetfairSession" should
    "allow me to log in to the betfair service" in {
    val underTest = new DemoAPIBetfairSession {}
    underTest.login
    underTest.loggedIn must be(true)
  }

}