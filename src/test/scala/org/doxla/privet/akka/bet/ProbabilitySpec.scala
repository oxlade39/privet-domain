package org.doxla.privet.akka.bet
import org.scalatest.FlatSpec
import org.scalatest.matchers.MustMatchers
import Probability._

class ProbabilitySpec extends FlatSpec with MustMatchers {

  "Any Probability" should "be equal to a probability of equal value" in {
    Probability(1) must equal(Probability(1))
    Probability(0.5) must equal(Probability(0.5))
    Probability(0) must equal(Probability(0))
    Probability(0.1) must equal(Probability(0.1))

    Probability(0.1) must not(equal(Probability(0.2)))
    Probability(0.2) must not(equal(Probability(0.1)))
    Probability(0.5) must not(equal(Probability(0.2)))
    Probability(1) must not(equal(Probability(0.5)))
  }

  "Certain" should "be the probability which is 1, i.e definite" in {
    Probability(1) must equal(Probability(1))
    Certain must equal(Certain)
    Certain must equal(Probability(1))
    Probability(1) must equal(Certain)
    Odds(1).impliedProbability must equal(Certain)
  }

  "Evens" should "be the probability which is half, i.e 50/50" in {
    Probability(0.5) must equal(Probability(0.5))
    Evens must equal(Evens)
    Evens must equal(Probability(0.5))
  }
}