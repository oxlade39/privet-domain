package org.doxla.privet.akka.bet
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers

class OddsSpec extends FlatSpec with MustMatchers {

  "Odds" should "have an implied probability of 1 over odds" in {
    Odds(1).impliedProbability must equal(Probability(1))

    Odds(2).impliedProbability must equal(Probability(0.5))
    Odds(4).impliedProbability must equal(Probability(0.25))

    Odds(1000).impliedProbability must equal(Probability(0.001))
  }

}