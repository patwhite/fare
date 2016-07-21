package fare.backend

/**
  * Created by patwhite on 7/20/16.
  */
case class Config(npaCount: Int)

object Config {
  val parser = new scopt.OptionParser[Config]("fare") {
    head("fare", "0.1")

    opt[Int]('n', "npa").action((x, c) =>
      c.copy(npaCount = x)).text("Count of Non Player Actors to spin up. Defaults to 100,000")

    help("help").text("Prints this usage text.")
  }
}
