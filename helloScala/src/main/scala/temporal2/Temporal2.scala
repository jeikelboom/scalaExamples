package temporal2

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId, ZonedDateTime}

object Temporal2 {

  case class Interval(begin: ZonedDateTime, end: ZonedDateTime) {
    assert (begin < end)

    def meetsLeft(other: Interval) : Boolean = end == other.begin
    def meetsRight(other: Interval) : Boolean = other.end == begin
    def meets(other: Interval) : Boolean = meetsLeft(other) || meetsRight(other)
    def overlaps(other: Interval) : Boolean = other.end > begin && end > other.begin
    def before(other: Interval) : Boolean = other.begin >= end
    def after(other: Interval) : Boolean = begin >= other.end
    def contains(timestamp: ZonedDateTime) = begin <= timestamp && end > timestamp
    def canJoin(other: Interval) : Boolean = other.begin<= end && begin <= other.end
    def join(other: Interval) : Option[Interval] =
      if (canJoin(other))  Some(Interval(begin min other.begin, end max other.end)) else None
    def intersection(other: Interval) : Option[Interval] =
      if (overlaps(other)) Option(Interval(begin max other.begin, end min other.end)) else Option.empty
  }

  implicit class ZonedDateTimeOrdering(ZonedDateTime: ZonedDateTime) extends Ordered[ZonedDateTime] {
    override def compare(that: ZonedDateTime): Int =
      if (ZonedDateTime.isBefore(that))  {-1} else if (that.isBefore(ZonedDateTime)) {1} else 0
    def max(other: ZonedDateTime): ZonedDateTime = if (ZonedDateTime > other) {ZonedDateTime} else other
    def min(other: ZonedDateTime): ZonedDateTime = if (ZonedDateTime < other) {ZonedDateTime} else other
  }

  def timestamp(value: String): ZonedDateTime = ZonedDateTime.of(LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneId.of("UTC"))


}
