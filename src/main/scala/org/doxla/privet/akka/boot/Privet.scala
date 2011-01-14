package org.doxla.privet.akka.boot

import akka.config._

class Privet {
  // val factory = Supervisor(
  //   SupervisorConfig(
  //     OneForOneStrategy(List(classOf[Exception]), 3, 100),
  //       //
  //       // in this particular case, just boot the built-in default root endpoint
  //       //
  //     Supervise(
  //       actorOf[Status],
  //       Permanent)
  //     :: Nil))
  // factory.newInstance.start
}