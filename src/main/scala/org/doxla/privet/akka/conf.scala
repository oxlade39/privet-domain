package org.doxla.privet.akka.conf

import akka.config.{Config}
import akka.util.Logger

trait BetfairUserConfig {
  val config = Config.config

  lazy val username: String = config("betfair.username")
  lazy val password: String = config("betfair.password")
  lazy val productId: Int = config.getInt("betfair.productId", 82)
}

trait Logging {
  lazy val log = Logger("org.doxla.privet." + this.getClass.getSimpleName)
}