package org.doxla.privet.akka.session

import org.doxla.privet.akka.conf.{Logging, BetfairUserConfig}
import dk.bot.betfairservice.{BetFairServiceImpl, BetFairService}
import akka.actor.Actor

trait Session {
  def login: Unit
  def loggedIn: Boolean
}

trait BetfairSession extends Session with BetfairUserConfig with Logging {
  private[this] var sessionEstablished = false

  lazy val betfairService: BetFairService = {
    val betfairServiceFactoryBean = new dk.bot.betfairservice.DefaultBetFairServiceFactoryBean();
    betfairServiceFactoryBean.setUser(username)
    betfairServiceFactoryBean.setPassword(password)
    betfairServiceFactoryBean.setProductId(productId)
    betfairServiceFactoryBean.getObject.asInstanceOf[BetFairService]
  }

  def login = {
    log.debug("logging into betfair using %s:%s", username, password)
    val response = betfairService.login
    log.debug("log in success: %s, response: %s:%s", response.isSuccess, response.getApiStatusCode, response.getExceptionMessage)
    if(response.isSuccess) {
      sessionEstablished = true
    }

  }

  def loggedIn = sessionEstablished
}

//class BetfairSessionActor extends Actor {
//
//}