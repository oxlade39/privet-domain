package org.doxla.privet.akka.strategy

package simplestrategies {

import org.doxla.privet.akka.conf.Logging
import org.doxla.privet.akka.market._
import akka.actor.{Actor, ActorRef, FSM}
import akka.actor.FSM._
import akka.util.Duration
import akka.util.duration._

sealed trait TradingState
case object NoBetsPlaced extends TradingState
case object BackPlaced extends TradingState
case object BackMatched extends TradingState
case object LayPlaced extends TradingState
case object LayMatched extends TradingState

case class RunnerUpdate(id: Long, price: BigDecimal, amountAvailable: BigDecimal)


trait TradingStrategy extends Actor with FSM[TradingState, Unit] {

  val marketHistory: MarketHistory
  val betDecider: ActorRef

  startWith(NoBetsPlaced, Unit, 1 second)

  when(NoBetsPlaced) {
    case Event(StateTimeout, _) =>
      log.debug("Received StateTimeout")
      stay
    case Event(RunnerUpdate(id, price, available), _) =>
      log.debug("Received runner updated from: %s", NoBetsPlaced)
      val runnerHistory: RunnerPriceHistory = marketHistory.runnerPriceHistory(id)
      runnerHistory.currentPriceChange match {
        case Increasing(factor) => log.debug("runner price is increasing by factor: %s", factor)
        case Decreasing(factor) => log.debug("runner price is decreasing by factor: %s", factor)
      }
      stay
  }

  when(BackPlaced) {
    case Event(RunnerUpdate(id, price, available), _) =>
      log.debug("Received runner updated from: %s", BackPlaced)
      stay
  }

  when(BackMatched) {
    case Event(RunnerUpdate(id, price, available), _) =>
      log.debug("Received runner updated from: %s", BackMatched)
      stay
  }

  when(LayPlaced) {
    case Event(RunnerUpdate(id, price, available), _) =>
      log.debug("Received runner updated from: %s", LayPlaced)
      stay
  }

  when(LayMatched) {
    case Event(RunnerUpdate(id, price, available), _) =>
      log.debug("Received runner updated from: %s", LayMatched)
      stay
  }

  initialize
}

sealed trait TraderState
case object Waiting extends TraderState
case object SearchingForBack extends TraderState
case object BackMade extends TraderState

case class BetOn(runnerId: Option[Long])

trait Trader extends Actor with FSM[TraderState, BetOn] {
  val marketHistory: MarketHistory
  val runnerId: Long

  startWith(SearchingForBack, BetOn(None), 500 milliseconds)

  when(SearchingForBack) {
    case Event(StateTimeout, _) => marketHistory.runnerPriceHistory(runnerId).currentPriceChange match {
      case Decreasing(factor) if(factor > priceDecreaseFactorTrigger) =>
        goto(BackMade)
    }
  }

  def priceDecreaseFactorTrigger = -15
}
}