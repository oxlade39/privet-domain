package org.doxla.privet.akka.bet


sealed trait BetStatus
case object Matched extends BetStatus
case object UnMatched extends BetStatus

case class Probability(value: BigDecimal) {
  def +(other: Probability) = Probability(value + other.value)
  def -(other: Probability) = Probability(value - other.value)
}
object Probability {
  lazy val Certain: Probability = Probability(1)
}

case class Odds(value: BigDecimal) {
  def impliedProbability = Probability(BigDecimal(1) / value)
}

sealed trait RunnerPosition
sealed abstract class Bet(stake: BigDecimal, odds: Odds, betStatus: BetStatus) extends RunnerPosition {
  def potentialLiability: BigDecimal
  def potentialProfit: BigDecimal

  def impliedProbabilityOfSuccess: Probability
}

case class Back(stake: BigDecimal, odds: Odds, betStatus: BetStatus) extends Bet(stake, odds, betStatus) {
  def potentialProfit = stake * odds.value
  def potentialLiability = stake
  def arbWith(other: Lay): Arbitrage = Arbitrage(this, other)
  def impliedProbabilityOfSuccess = odds.impliedProbability
}

case class Lay(stake: BigDecimal, odds: Odds, betStatus: BetStatus) extends Bet(stake, odds, betStatus) {
  def potentialProfit = stake
  def potentialLiability = stake * odds.value
  def arbWith(other: Back): Arbitrage = Arbitrage(other, this)

  def impliedProbabilityOfSuccess = Probability.Certain - odds.impliedProbability
}

case class Arbitrage(back: Back, lay: Lay) extends RunnerPosition {
  def profitIfBackWins: BigDecimal = back.potentialProfit - lay.potentialLiability
  def profitIfLayWins: BigDecimal = lay.potentialProfit - back.potentialLiability

  def minimumProfit: BigDecimal = profitIfBackWins.min(profitIfLayWins)
  def yieldsProfit: Boolean = minimumProfit > 0
}