package org.doxla.privet.akka.bet
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers
import Probability._

class BetSpec extends FlatSpec with MustMatchers {

  "Odds" should "have an implied probability equal to 1/odds value" in {
    Odds(1).impliedProbability must equal(Probability(1))
    Odds(2).impliedProbability must equal(Probability(0.5))
    Odds(4).impliedProbability must equal(Probability(0.25))

    Odds(1000).impliedProbability must equal(Probability(0.001))
  }

  "Certain" should "be the probability which is 1, i.e definite" in {
    Probability(1) must equal(Probability(1))
    Certain must equal(Certain)
    Certain must equal(Probability(1))
    Probability(1) must equal(Certain)
    Odds(1).impliedProbability must equal(Certain)
  }

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

  "A Back arbed with an equal oposite Lay" should "have a maximum exposure of zero" in {
    (Back(10, Odds(1), Matched) arbWith Lay(10, Odds(1), Matched)).minimumProfit must equal(0)
    (Back(1, Odds(10), Matched) arbWith Lay(1, Odds(10), Matched)).minimumProfit must equal(0)
    (Back(5, Odds(1.77), Matched) arbWith Lay(5, Odds(1.77), Matched)).minimumProfit must equal(0)
  }

  "A Lay arbed with an equal oposite Back" should "have a maximum exposure of zero" in {
    (Lay(10, Odds(1), Matched) arbWith Back(10, Odds(1), Matched)).minimumProfit must equal(0)
    (Lay(1, Odds(10), Matched) arbWith Back(1, Odds(10), Matched)).minimumProfit must equal(0)
    (Lay(5, Odds(1.77), Matched) arbWith Back(5, Odds(1.77), Matched)).minimumProfit must equal(0)
  }

  "A Back arbed with a Lay" should "have a profit if back wins of the back profit minus the Lay liability" in {
    (Back(2, Odds(5), Matched) arbWith Lay(3, Odds(2), Matched)).profitIfBackWins must equal(10 - 6)
  }

  "A Back arbed with a Lay" should "have a profit if lay wins of the Lay profit minus the Back liability" in {
    (Back(2, Odds(5), Matched) arbWith Lay(3, Odds(2), Matched)).profitIfLayWins must equal(3 - 2)
  }

  "A Back arbed with a Lay" should "have a minimim profit of the minimum from the profit if Lay and profit if Back wins" in {
    (Back(2, Odds(5), Matched) arbWith Lay(3, Odds(2), Matched)).minimumProfit must equal(3 - 2)
    (Back(2, Odds(5), Matched) arbWith Lay(3, Odds(5), Matched)).minimumProfit must equal(10 - 15)
    (Back(2, Odds(5), Matched) arbWith Lay(2, Odds(5), Matched)).minimumProfit must equal(0)
  }

  "A Back arbed with a Lay" should "yield a profit if the minimim profit is greater than zero" in {
    (Back(2, Odds(2), Matched) arbWith Lay(3, Odds(1.2), Matched)).yieldsProfit must equal(true)
  }

  "A Back arbed with a Lay" should "not yield a profit if the minimim profit is less than zero" in {
    (Back(2, Odds(1.5), Matched) arbWith Lay(3, Odds(1.2), Matched)).yieldsProfit must equal(false)
  }

  "A Back arbed with a Lay" should "not yield a profit if the minimim profit is zero" in {
    (Back(2, Odds(1.5), Matched) arbWith Lay(2, Odds(1.5), Matched)).yieldsProfit must equal(false)
  }
}