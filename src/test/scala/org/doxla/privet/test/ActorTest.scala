package org.doxla.privet.test

import akka.util.TestKit
import org.scalatest.{Suite, BeforeAndAfterEach}
import akka.actor.{Actor, ActorRef}

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

class TestEchoActor extends Actor {
  def receive = {
    case msg => self.reply(msg)
  }
}