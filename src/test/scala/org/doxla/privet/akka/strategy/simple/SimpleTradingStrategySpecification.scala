package org.doxla.privet.akka.strategy.simple

import org.scalatest.FlatSpec
import org.specs.mock.Mockito
import org.doxla.privet.test.{ActorTest, MockitoScalaTestAdapter}
import akka.actor.Actor._
import akka.actor.{FSM, Actor, ActorRef}

class SimpleTradingStrategySpecification extends FlatSpec with ActorTest with Mockito with MockitoScalaTestAdapter {

  val testBetPlacer = actorOf( new Actor { def receive = { case foo => println(foo) } } ).start

  class UnderTest extends TradingStrategy {
    val betPlacer = testBetPlacer
  }
  var underTest: ActorRef = null

  "SimpleTradingStrategy" should
    "accept RateUpdates" in {
    underTest ! RateUpdate(1, -1)
  }

  def initialiseActors = {
    underTest = actorOf(new UnderTest)
    underTest.start
    List(testBetPlacer, underTest)
  }
}