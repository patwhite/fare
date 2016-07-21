package fare.backend.actors

import akka.actor.{Actor, ActorLogging, Props}
import akka.actor.Actor.Receive
import org.joda.time.DateTime
import NonPlayerActor.WokeUp
import World.{Count, Start}
import fare.common.behavior.Behavior

import scala.concurrent.duration._
import fare.common.util.StringUtils._

/**
  * Created by patwhite on 7/13/16.
  */
class World(npaCount: Int) extends Actor with ActorLogging {
  import context.dispatcher

  var wakeUpPerSecond: Int = 0
  var finishedStartUp = false

  override def preStart() = {
    self ! Start
    context.system.scheduler.schedule(0 seconds, 1 second, self, Count)
  }

  override def receive: Receive = {
    case Start =>
      val start = DateTime.now.getMillis

      for (i <- 0 to npaCount) {
        context.actorOf(NonPlayerActor.props(i.toString, new Behavior, self))
      }
      val totalTime = DateTime.now().getMillis - start
      log.info(s"Took ${totalTime}ms to start up")
      finishedStartUp = true

    case w: WokeUp =>
      if (finishedStartUp) {
        wakeUpPerSecond = wakeUpPerSecond + 1
      }

    case Count =>
      if (finishedStartUp) {
        log.info(s"$wakeUpPerSecond woke up in past second")
        wakeUpPerSecond = 0
      }
  }
}

object World {
  case object Start
  case object Count

  def props(npaCount: Int) = Props(new World(npaCount))
}
