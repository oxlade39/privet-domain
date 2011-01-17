package org.doxla.privet.akka.session

import org.specs.mock.Mockito
import akka.actor.Actor._
import akka.util.duration._
import org.scalatest.FlatSpec
import org.doxla.privet.test.{ActorTest, MockitoScalaTestAdapter}
import akka.actor.ActorRef

class SessionSpecification extends FlatSpec with ActorTest with Mockito with MockitoScalaTestAdapter {

  val mockSession = mock[Session]
  var session: ActorRef = null

  "SessionActor" should
    "use session to log in on Login message" in {

      within(1000 millis) {
        session !! Login
        there was one(mockSession).login
      }
    }

    it should "use session to log out on Logout message" in {

      within(1000 millis) {
        session !! Logout
        there was one(mockSession).logout
      }
    }

  class TestSessionActor extends SessionActor {
    val session = mockSession
  }

  def initialiseActor = {
    session = actorOf(new TestSessionActor)
    session.start
  }
}