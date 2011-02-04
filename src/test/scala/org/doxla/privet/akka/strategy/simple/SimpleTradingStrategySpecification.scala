package org.doxla.privet.akka.strategy.simple

import org.scalatest.FlatSpec
import org.specs.mock.Mockito
import akka.actor.Actor._
import akka.actor.ActorRef
import akka.util.duration._
import org.doxla.privet.test.{ActorTest, MockitoScalaTestAdapter}
import org.doxla.privet.akka.bet._

class SimpleTradingStrategySpecification extends FlatSpec with ActorTest with Mockito with MockitoScalaTestAdapter {

  var underTest: ActorRef = null

  class UnderTest(actor: ActorRef) extends TradingStrategy {
    val betPlacer = actor
  }

  def withinAnExceptableTime[T](f : => T) = within(100 millis)(f)

  "SimpleTradingStrategy" should "start in a Watching position" in {
    withinAnExceptableTime {
      assertRunnerPositionIs(Watching)
    }
  }

  it should "accept RateUpdates" in {
    underTest ! RateUpdate(Odds(1), -1)
  }

  it should "forward PlaceBack messages to #betPlacer" in {
    withinAnExceptableTime {
      underTest ! PlaceBack(10)
      expectMsg(PlaceBack(10))
    }
  }

  it should "have an UnMatched runner position after placing a back without response" in {
    withinAnExceptableTime {
      placeBack()
      assertRunnerPositionIs(Back(10, Odds(0), UnMatched))
    }
  }

  it should "have an Matched runner position when #betPlacer responds to a PlaceBet" in {
    withinAnExceptableTime {
      placeBack()
      underTest ! BackMatched
      assertRunnerPositionIs(Back(10, Odds(0), Matched))
    }
  }

  def placeBack(amount: BigDecimal = 10): Unit = {
    underTest ! PlaceBack(amount)
    expectMsg(PlaceBack(amount))
  }

  def assertRunnerPositionIs(position: RunnerPosition): AnyRef = {
    underTest ! GetRunerPosition
    expectMsg(position)
  }

  def initialiseActors = {
    underTest = actorOf(new UnderTest(testActor)).start
    List(underTest)
  }
}