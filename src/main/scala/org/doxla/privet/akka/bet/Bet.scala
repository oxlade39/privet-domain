package org.doxla.privet.akka.bet


sealed trait BetStatus
case object Matched extends BetStatus
case object UnMatched extends BetStatus

sealed trait RunnerPosition
sealed abstract class Bet(stake: BigDecimal, odds: BigDecimal, betStatus: BetStatus) extends RunnerPosition {
  def potentialLiability: BigDecimal
  def potentialProfit: BigDecimal
}

case class Back(stake: BigDecimal, odds: BigDecimal, betStatus: BetStatus) extends Bet(stake, odds, betStatus) {
  def potentialProfit = stake * odds
  def potentialLiability = stake
  def arbWith(other: Lay): Arbitrage = Arbitrage(this, other)
}

case class Lay(stake: BigDecimal, odds: BigDecimal, betStatus: BetStatus) extends Bet(stake, odds, betStatus) {
  def potentialProfit = stake
  def potentialLiability = stake * odds
  def arbWith(other: Back): Arbitrage = Arbitrage(other, this)
}

case class Arbitrage(back: Back, lay: Lay) extends RunnerPosition {
  def profitIfBackWins: BigDecimal = back.potentialProfit - lay.potentialLiability
  def profitIfLayWins: BigDecimal = lay.potentialProfit - back.potentialLiability

  def minimumProfit: BigDecimal = profitIfBackWins.min(profitIfLayWins)
  def yieldsProfit: Boolean = minimumProfit > 0
}