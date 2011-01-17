package org.doxla.privet.akka.session

import akka.actor.Actor

trait Session {
  def login: Unit
  def logout: Unit
  def loggedIn: Boolean
}

sealed trait SessionAction
case object Login extends SessionAction
case object Logout extends SessionAction
case object GetSessionStatus extends SessionAction
case class SessionStatus(loggedIn: Boolean) extends SessionAction

trait SessionActor extends Actor {
  val session: Session

  def respondWithStatus: Unit = {
    self.reply(SessionStatus(session.loggedIn))
  }

  protected def receive = {
    case Login => session.login; respondWithStatus
    case Logout => session.logout; respondWithStatus
    case GetSessionStatus => respondWithStatus
  }
}