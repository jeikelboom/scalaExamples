package temporal

import java.time.{OffsetDateTime, ZonedDateTime}

object Temporal {

  // describes interval begin <= x < end (semi-open)
  case class Interval(val begin: ZonedDateTime, val end: ZonedDateTime) {

    def overlaps(other: Interval) : Boolean = other.end.isAfter(begin) && end.isAfter(other.begin)

  }



}
