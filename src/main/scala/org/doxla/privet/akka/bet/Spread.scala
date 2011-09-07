package org.doxla.privet.akka.bet

class Spread(left: BigDecimal, right: BigDecimal)
  extends Pair[BigDecimal, BigDecimal](left.min(right), left.max(right)) {
  def upperBound = _2
  def lowerBound = _1

  def alwaysYieldsProfit = lowerBound > 0

  override def hashCode() = super.hashCode + swap.hashCode
  override def equals(other: Any) = super.equals(other) || swap.equals(other)
}

object Spread {
  def apply(left: BigDecimal, right: BigDecimal) = new Spread(left, right)
}













