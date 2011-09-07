package org.doxla.privet.akka.bet
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

class BackSpec extends FlatSpec with MustMatchers {

  "A Back" should "have a potential liability equal to the stake" in {
    Back(10, Odds(1000), Matched).potentialLiability must equal(10)
    Back(20, Odds(1000), Matched).potentialLiability must equal(20)
    Back(0, Odds(1000), Matched).potentialLiability must equal(0)
    Back(100, Odds(1000), Matched).potentialLiability must equal(100)
  }

  it should "have a potential profit equal to the product of the stake and odds" in {
    Back(10, Odds(10), Matched).potentialProfit must equal(100)
    Back(1, Odds(1.77), Matched).potentialProfit must equal(1.77)
    Back(12, Odds(6.66), Matched).potentialProfit must equal(12 * 6.66)
    Back(2, Odds(2.5), Matched).potentialProfit must equal(2 * 2.5)
  }

  it should "have an implied probability of success equal to the implied probability of the odds" in {
    Back(10, Odds(2), Matched).impliedProbabilityOfSuccess must equal(Probability(0.5))
    Back(10, Odds(4), Matched).impliedProbabilityOfSuccess must equal(Probability(0.25))
  }

}