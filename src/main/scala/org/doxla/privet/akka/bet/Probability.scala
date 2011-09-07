package org.doxla.privet.akka.bet

case class Probability(value: BigDecimal) {
  def +(other: Probability) = Probability(value + other.value)
  def -(other: Probability) = Probability(value - other.value)
}

object Probability {
  lazy val Certain: Probability = Probability(1)
  lazy val Evens: Probability = Probability(0.5)
}













