package org.doxla.privet.akka.strategy

package simplestrategies {

import akka.actor.{Actor, ActorRef, FSM}
import akka.actor.FSM._
import akka.util.duration._
import org.doxla.privet.akka.bet._

sealed trait TradingState
case object NoBetsPlaced extends TradingState
case object BackPlaced extends TradingState
case object BackMatched extends TradingState
case object LayPlaced extends TradingState
case object LayMatched extends TradingState

case class Trade(lastRate: BigDecimal, back: Option[Bet], arb: Option[Arbitrage])

sealed trait TradingMessage
case class RateUpdate(currentPrice: BigDecimal, change: Int) extends TradingMessage
case class PlaceBack(amount: BigDecimal) extends TradingMessage
case class Matched(price: BigDecimal)

trait TradingStrategy extends Actor with FSM[TradingState, Trade] {

  val betPlacer: ActorRef

  startWith(NoBetsPlaced, Trade(0, None, None), 1 second)

  when(NoBetsPlaced) {
    case Event(StateTimeout, _) =>
      log.debug("Received StateTimeout")
      stay
    case Event(RateUpdate(rate, change), _) =>
      stay using Trade(rate, None, None)
    case Event(PlaceBack(amount), Trade(lastRate, _, _)) =>
      betPlacer ! PlaceBack(amount)
      goto(BackPlaced) using Trade(lastRate, Some(Back(amount, lastRate, UnMatched)), None)
  }

  when(BackPlaced) {
    case Event(RateUpdate(price, change), _) => stay
  }

  when(BackMatched) {
    case Event(_, _) =>
      log.debug("Received runner updated from: %s", BackMatched)
      stay
  }

  when(LayPlaced) {
    case Event(_, _) =>
      log.debug("Received runner updated from: %s", LayPlaced)
      stay
  }

  when(LayMatched) {
    case Event(_, _) =>
      log.debug("Received runner updated from: %s", LayMatched)
      stay
  }

  initialize
}

}