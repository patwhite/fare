package fare.common.behavior

/**
  * Created by patwhite on 7/14/16.
  */
sealed trait MoveDirection
case object North extends MoveDirection
case object East extends MoveDirection
case object South extends MoveDirection
case object West extends MoveDirection
