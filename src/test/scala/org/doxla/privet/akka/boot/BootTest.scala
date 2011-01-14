package org.doxla.privet.akka.boot

import org.specs.Specification
import org.specs.runner.JUnit4
import akka.actor.Actor._
import akka.util.TestKit
import akka.util.duration._

//class BootSpecificationTest extends JUnit4(BootSpecification)
class BootSpecification extends Specification with TestKit {
	
	val status = actorOf[Status].start
	
	"Privet" should {
		"boot successfully" in {
			within (1000 millis) {
	        	status ! GetStatus
	        	expectMsg(Ok)
	    	}
		}
	}
}