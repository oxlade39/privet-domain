package org.doxla.privet.test

import akka.util.TestKit
import akka.actor.ActorRef
import org.scalatest.{Suite, BeforeAndAfterEach}

trait ActorTest extends TestKit with BeforeAndAfterEach {
  this: Suite =>

  private var actor: ActorRef = null

  def initialiseActor: ActorRef

  override protected def beforeEach = {
    actor = initialiseActor
    actor.start
  }

  override protected def afterEach = {
    actor.stop
  }
}