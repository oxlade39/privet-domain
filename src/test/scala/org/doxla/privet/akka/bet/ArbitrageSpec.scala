package org.doxla.privet.akka.bet
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

class ArbitrageSpec extends FlatSpec with MustMatchers {

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
    Back(2, Odds(5), Matched).potentialProfit must equal(10)
    Lay(3, Odds(2), Matched).potentialLiability must equal(6)

    Lay(3, Odds(2), Matched).potentialProfit must equal(3)
    Back(2, Odds(5), Matched).potentialLiability must equal(2)

    // if back wins, win 10, lose 6
    (Back(2, Odds(5), Matched) arbWith Lay(3, Odds(2), Matched)).profitIfBackWins must equal(10 - 6)

    // if lay wins, win 3, lose 2
    (Back(2, Odds(5), Matched) arbWith Lay(3, Odds(2), Matched)).profitIfLayWins must equal(3 - 2)

    // minimum of ((10-6) or (3-2)) -> (3-2)
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