package org.doxla.privet.akka.session

import org.specs.Specification

object SessionSpecification extends Specification {

  "BetfairSession" should {
    "allow me to log in to the betfair service" in {
      val underTest = new BetfairSession{}
      underTest.login
      underTest.loggedIn mustBe true
    }
  }

}