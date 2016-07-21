package fare.common.util

/**
  * Created by patwhite on 7/20/16.
  */
object StringUtils {
  implicit class ExtendedString(s: String) {
    def toIntOpt: Option[Int] = {
      try {
        Some(s.toInt)
      }
      catch {
        case n: NumberFormatException => None
      }
    }
  }
}
