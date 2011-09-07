package org.doxla.privet.akka.bet
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

class LaySpec extends FlatSpec with MustMatchers {

  "A Lay" should "have a potential profit equal to the stake" in {
    Lay(10, Odds(10), Matched).potentialProfit must equal(10)
    Lay(20, Odds(100), Matched).potentialProfit must equal(20)
    Lay(100, Odds(20), Matched).potentialProfit must equal(100)
    Lay(10, Odds(1), Matched).potentialProfit must equal(10)
  }

  it should "have a potential liability equal to the product of the stake and odds" in {
    Lay(10, Odds(10), Matched).potentialLiability must equal(100)
    Lay(1, Odds(1.77), Matched).potentialLiability must equal(1.77)
    Lay(12, Odds(6.66), Matched).potentialLiability must equal(12 * 6.66)
    Lay(2, Odds(2.5), Matched).potentialLiability must equal(2 * 2.5)
  }

  it should "have an implied probability of success equal to Certain minus the implied probability of the odds" in {
    Lay(10, Odds(2), Matched).impliedProbabilityOfSuccess must equal(Probability(0.5))
    Lay(10, Odds(4), Matched).impliedProbabilityOfSuccess must equal(Probability(0.75))
  }

}