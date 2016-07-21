package controllers

import com.typesafe.scalalogging.LazyLogging
import play.api._
import play.api.mvc._

class Application extends Controller with LazyLogging {

  def index = Action {
    logger.info("Got Index Request")
    Ok(views.html.index("Your new application is ready."))
  }

}