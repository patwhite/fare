package fare.backend

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging
import fare.backend.actors.World

import scala.concurrent.duration._

case object Go

object Root extends App with LazyLogging {
  val system = ActorSystem("main")

  val defaultConfig = Config(100000)
  Config.parser.parse(args, defaultConfig) match {
    case Some(config) => system.actorOf(World.props(config.npaCount))
    case None =>
      logger.error("Exiting with arguments")
      sys.exit(1)
  }
}
