package fare.backend.actors

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, Props}
import Geo.Init

/**
  * Created by patwhite on 7/14/16.
  */
class Geo(lat: Int, long: Int) extends Actor with ActorLogging {
  override def preStart() = {
    self ! Init
  }
  override def receive: Receive = {
    case Init =>
  }
}

object Geo {
  case object Init

  def props(lat: Int, long: Int) = Props(new Geo(lat, long))
}