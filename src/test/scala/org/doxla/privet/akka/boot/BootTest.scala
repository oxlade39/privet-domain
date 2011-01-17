package org.doxla.privet.akka.boot

import akka.actor.Actor._
import akka.util.TestKit
import akka.util.duration._
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FlatSpec}

class BootSpecification extends FlatSpec with TestKit with BeforeAndAfterEach {

  val status = actorOf[Status]

  "Privet" should "boot successfully" in {
    within(1000 millis) {
      status ! GetStatus
      expectMsg(Ok)
    }
  }

  override protected def beforeEach = {
    status.start
  }

  override protected def afterEach = {
    status.stop
  }
}