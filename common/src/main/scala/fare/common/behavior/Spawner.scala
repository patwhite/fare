package fare.common.behavior

/**
  * Created by patwhite on 7/13/16.
  */
case class Spawner(frequency: Double, behaviorFactory: () => Behavior)
