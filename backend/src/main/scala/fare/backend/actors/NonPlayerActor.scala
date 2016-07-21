package fare.backend.actors

import java.util.UUID

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.actor.Actor.Receive
import NonPlayerActor.{Start, Wake, WokeUp}
import fare.common.behavior._

import scala.concurrent.duration._
import scala.util.Random

/**
  * Created by patwhite on 7/13/16.
  */
class NonPlayerActor(name: String, behavior: Behavior, monitor: ActorRef) extends Actor with ActorLogging {
  import context.dispatcher

  val id = UUID.randomUUID().toString

  var lat: Double = Random.nextInt(180) - 90
  var long: Double = Random.nextInt(360) - 180

  override def preStart(): Unit = self ! Start

  override def receive: Receive = {
    case Start =>
      log.info(s"[$id] [$name] Starting Up")
      scheduleWakeup()

    case Wake =>
      log.debug(s"[$id] [$name] Woke up")

      val distance: Double = Random.nextInt(20).toDouble / 100

      val direction = Random.nextInt(4) match {
        case 0 => North
        case 1 => East
        case 2 => South
        case 3 => West
      }

      direction match {
        case North => lat = lat + distance
        case East  => long = long + distance
        case South => lat = lat - distance
        case West  => long = long - distance
      }

      monitor ! WokeUp(name, distance, direction)
      scheduleWakeup()
  }

  private def scheduleWakeup() = context.system.scheduler.scheduleOnce(randTime, self, Wake)

  private def randTime = {
    Random.nextInt(30).seconds
  }
}

object NonPlayerActor {
  case object Start
  case object Wake
  case class WokeUp(name: String, moveDistance: Double, moveDirection: MoveDirection)

  def props(name: String, behavior: Behavior, monitor: ActorRef) = Props(new NonPlayerActor(name, behavior, monitor))
}