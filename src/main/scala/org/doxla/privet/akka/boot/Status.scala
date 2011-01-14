package org.doxla.privet.akka.boot

import akka.actor.Actor

class Status extends Actor {
	def receive = {
		case GetStatus => self.reply(Ok)
	}
}

abstract sealed class StatusMessage
case object GetStatus extends StatusMessage
case class StatusResponse(code: Int) extends StatusMessage {
	def isOk = code == 200
}

case object Ok extends StatusResponse(200)
