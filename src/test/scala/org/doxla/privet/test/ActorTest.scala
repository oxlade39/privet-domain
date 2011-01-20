package org.doxla.privet.test

import akka.util.TestKit
import akka.actor.ActorRef
import org.scalatest.{Suite, BeforeAndAfterEach}

trait ActorTest extends TestKit with BeforeAndAfterEach {
  this: Suite =>

  private var actors: List[ActorRef] = Nil

  def initialiseActors: List[ActorRef]

  override protected def beforeEach = {
    actors = initialiseActors
    for(actor <- actors) actor.start
  }

  override protected def afterEach = {
    for(actor <- actors) actor.stop
  }
}