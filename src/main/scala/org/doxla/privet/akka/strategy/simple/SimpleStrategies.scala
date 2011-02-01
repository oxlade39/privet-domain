package org.doxla.privet.akka.strategy.simple

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

case class Trade(lastRate: Odds, back: RunnerPosition)

sealed trait TradingMessage
case class RateUpdate(currentOdds: Odds, change: Int) extends TradingMessage
case class PlaceBack(amount: BigDecimal) extends TradingMessage
case object GetRunerPosition extends TradingMessage

trait TradingStrategy extends Actor with FSM[TradingState, Trade] {

  val betPlacer: ActorRef

  startWith(NoBetsPlaced, Trade(Odds(0), Watching), 1 second)

  val receivePriceUpdate: StateFunction = {
    case Event(RateUpdate(rate, change), currentTrade) =>
      stay using Trade(rate, currentTrade.back)
  }

  val respondWithRunnerPosition: StateFunction = {
    case Event(GetRunerPosition, currentTrade) =>
      self.reply(currentTrade.back)
      stay
  }

  val logReceive: StateFunction = {
    case Event(message, trade) =>
      log.debug("Received %s at trade %s", message, trade)
      stay
  }

  val placeBackWithBetPlacer: StateFunction = {
    case Event(PlaceBack(amount), Trade(lastRate, _)) =>
      betPlacer ! PlaceBack(amount)
      goto(BackPlaced) using Trade(lastRate, Back(amount, lastRate, UnMatched))
  }

  when(NoBetsPlaced)(receivePriceUpdate orElse placeBackWithBetPlacer orElse respondWithRunnerPosition)

  when(BackPlaced)(receivePriceUpdate orElse respondWithRunnerPosition)

  when(BackMatched)(receivePriceUpdate)

  when(LayPlaced)(receivePriceUpdate)

  when(LayMatched)(receivePriceUpdate)

  initialize
}