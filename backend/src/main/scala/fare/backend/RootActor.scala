package fare.backend

import akka.actor.{Props, ActorLogging, Actor}
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.duration._

class RootActor extends Actor with ActorLogging with LazyLogging {
  import context.dispatcher

  override def preStart() = {
    self ! Go
  }
  override def receive: Receive = {
    case Go =>
      log.info("Go!")
      logger.info("Blah!")
      context.system.scheduler.scheduleOnce(5 seconds, self, Go)

  }
}

object RootActor {
  def props = Props(new RootActor)
}