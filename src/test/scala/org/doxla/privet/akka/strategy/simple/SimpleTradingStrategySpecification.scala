package org.doxla.privet.akka.strategy.simple

import org.scalatest.FlatSpec
import org.specs.mock.Mockito
import akka.actor.Actor._
import akka.actor.{FSM, Actor, ActorRef}
import akka.util.duration._
import org.doxla.privet.test.{TestEchoActor, ActorTest, MockitoScalaTestAdapter}
import org.doxla.privet.akka.bet.Odds

class SimpleTradingStrategySpecification extends FlatSpec with ActorTest with Mockito with MockitoScalaTestAdapter {

  var underTest:     ActorRef = null

  class UnderTest(actor: ActorRef) extends TradingStrategy {
    val betPlacer = actor
  }

  "SimpleTradingStrategy" should
    "accept RateUpdates" in {
    underTest ! RateUpdate(Odds(1), -1)
  }

  it should "forward PlaceBet messages to the betPlacer" in {
    within(1000 millis) {
      underTest ! PlaceBack(10)
      expectMsg(PlaceBack(10))
    }
  }

  def initialiseActors = {
    underTest = actorOf(new UnderTest(testActor)).start
    List(underTest)
  }
}