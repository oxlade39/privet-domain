package org.doxla.privet.akka.bet


sealed trait BetStatus

case object Matched extends BetStatus

case object UnMatched extends BetStatus
















