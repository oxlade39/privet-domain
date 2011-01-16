package org.doxla.privet.akka.demoapi.session

import org.doxla.privet.akka.conf.{Logging, BetfairUserConfig}
import demo.handler.GlobalAPI
import demo.util.APIContext
import org.doxla.privet.akka.session.Session

trait DemoAPIBetfairSession extends Session with BetfairUserConfig with Logging {
  private var sessionEstablished = false
  private lazy val apiCtx = new APIContext()

  def login = {
    log.debug("logging into betfair using %s:*****", username)
    GlobalAPI.login(apiCtx, username, password)
    log.debug("log in success")
    sessionEstablished = true
  }

  def loggedIn = sessionEstablished

  def logout = {
    GlobalAPI.logout(apiCtx)
    log.debug("Logged out of Betfair")
  }
}