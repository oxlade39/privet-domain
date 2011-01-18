package org.doxla.privet.akka.bet
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

class BetSpec extends FlatSpec with MustMatchers {

  "A Back" should "have a potential liability equal to the stake" in {
    Back(10, 1000, Matched).potentialLiability must equal(10)
    Back(20, 1000, Matched).potentialLiability must equal(20)
    Back(0, 1000, Matched).potentialLiability must equal(0)
    Back(100, 1000, Matched).potentialLiability must equal(100)
  }

  it should "have a potential profit equal to the product of the stake and odds" in {
    Back(10, 10, Matched).potentialProfit must equal(100)
    Back(1, 1.77, Matched).potentialProfit must equal(1.77)
    Back(12, 6.66, Matched).potentialProfit must equal(12 * 6.66)
    Back(2, 2.5, Matched).potentialProfit must equal(2 * 2.5)
  }

  "A Lay" should "have a potential profit equal to the stake" in {
    Lay(10, 10, Matched).potentialProfit must equal(10)
    Lay(20, 100, Matched).potentialProfit must equal(20)
    Lay(100, 20, Matched).potentialProfit must equal(100)
    Lay(10, 1, Matched).potentialProfit must equal(10)
  }

  it should "have a potential liability equal to the product of the stake and odds" in {
    Lay(10, 10, Matched).potentialLiability must equal(100)
    Lay(1, 1.77, Matched).potentialLiability must equal(1.77)
    Lay(12, 6.66, Matched).potentialLiability must equal(12 * 6.66)
    Lay(2, 2.5, Matched).potentialLiability must equal(2 * 2.5)
  }

  "A Back arbed with an equal oposite Lay" should "have a maximum exposure of zero" in {
    (Back(10, 1, Matched) arbWith Lay(10, 1, Matched)).minimumProfit must equal(0)
    (Back(1, 10, Matched) arbWith Lay(1, 10, Matched)).minimumProfit must equal(0)
    (Back(5, 1.77, Matched) arbWith Lay(5, 1.77, Matched)).minimumProfit must equal(0)
  }

  "A Lay arbed with an equal oposite Back" should "have a maximum exposure of zero" in {
    (Lay(10, 1, Matched) arbWith Back(10, 1, Matched)).minimumProfit must equal(0)
    (Lay(1, 10, Matched) arbWith Back(1, 10, Matched)).minimumProfit must equal(0)
    (Lay(5, 1.77, Matched) arbWith Back(5, 1.77, Matched)).minimumProfit must equal(0)
  }

  "A Back arbed with a Lay" should "have a profit if back wins of the back profit minus the Lay liability" in {
    (Back(2, 5, Matched) arbWith Lay(3, 2, Matched)).profitIfBackWins must equal(10 - 6)
  }

  "A Back arbed with a Lay" should "have a profit if lay wins of the Lay profit minus the Back liability" in {
    (Back(2, 5, Matched) arbWith Lay(3, 2, Matched)).profitIfLayWins must equal(3 - 2)
  }

  "A Back arbed with a Lay" should "have a minimim profit of the minimum from the profit if Lay and profit if Back wins" in {
    (Back(2, 5, Matched) arbWith Lay(3, 2, Matched)).minimumProfit must equal(3 - 2)
    (Back(2, 5, Matched) arbWith Lay(3, 5, Matched)).minimumProfit must equal(10 - 15)
    (Back(2, 5, Matched) arbWith Lay(2, 5, Matched)).minimumProfit must equal(0)
  }

  "A Back arbed with a Lay" should "yield a profit if the minimim profit is greater than zero" in {
    (Back(2, 2, Matched) arbWith Lay(3, 1.2, Matched)).yieldsProfit must equal(true)
  }

  "A Back arbed with a Lay" should "not yield a profit if the minimim profit is less than zero" in {
    (Back(2, 1.5, Matched) arbWith Lay(3, 1.2, Matched)).yieldsProfit must equal(false)
  }

  "A Back arbed with a Lay" should "not yield a profit if the minimim profit is zero" in {
    (Back(2, 1.5, Matched) arbWith Lay(2, 1.5, Matched)).yieldsProfit must equal(false)
  }
}