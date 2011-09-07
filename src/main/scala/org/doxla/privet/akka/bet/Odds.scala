package org.doxla.privet.akka.bet

case class Odds(value: BigDecimal) {
  def impliedProbability = Probability(BigDecimal(1) / value)
}









