package org.doxla.privet.akka.bet
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

class SpreadSpec extends FlatSpec with MustMatchers {

  "Spread" should "have an upper and lower bound" in {
    Spread(1, 2).upperBound must equal(2)
    Spread(1, 2).lowerBound must equal(1)

    Spread(2, 1).upperBound must equal(2)
    Spread(2, 1).lowerBound must equal(1)
  }

  it should "be unordered" in {
    Spread(1, 2) must equal(Spread(2, 1))
  }

  it should "always yield profit if both sides of the spread are positive" in {
    Spread(1, 2).alwaysYieldsProfit must equal(true)
  }

  it should "not always yield profit if a component of the spread is non positive" in {
    Spread(-2, 10).alwaysYieldsProfit must equal(false)
    Spread(0, 10).alwaysYieldsProfit must equal(false)
  }

}