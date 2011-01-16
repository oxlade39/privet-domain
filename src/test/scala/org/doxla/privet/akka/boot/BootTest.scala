package org.doxla.privet.akka.boot

import akka.actor.Actor._
import akka.util.TestKit
import akka.util.duration._
import org.scalatest.FlatSpec

class BootSpecification extends FlatSpec with TestKit {

  val status = actorOf[Status].start

  "Privet" should "boot successfully" in {
    within(1000 millis) {
      status ! GetStatus
      expectMsg(Ok)
    }
  }
}