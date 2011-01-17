package org.doxla.privet.akka.market


trait MarketHistory {
  def runnerPriceHistory(id: Long): RunnerPriceHistory
}

sealed trait PriceChange
case class Increasing(factor: Float) extends PriceChange
case class Decreasing(factor: Float) extends PriceChange

case class RunnerPriceHistory(id: Long, currentPriceChange: PriceChange)