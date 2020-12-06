package temporal2


import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


object Intervals {

  val fmtin: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV")
  val fmtout: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

  case class Interval(val begin: ZonedDateTime, val end: ZonedDateTime) {
    def min(a: ZonedDateTime, b: ZonedDateTime): ZonedDateTime = if (a < b)  a else b
    def max(a: ZonedDateTime, b: ZonedDateTime): ZonedDateTime = if (a > b)  a else b

    def before(other: Interval) : Boolean = end < other.begin
    def meets(other: Interval) : Boolean = end == other.begin
    def overlaps(other: Interval) : Boolean = other.end > begin && end > other.begin
    def contains(other: Interval) : Boolean = begin <= other.begin & end >= other.end
    def contains(timestamp: ZonedDateTime) = begin <= timestamp && end > timestamp

    def join(other: Interval) : Option[Interval] = {
      val f = min(begin, other.begin)
      val t = max(end, other.end)
      if (other.begin <= end && begin <= other.end)  Some(Interval(f, t)) else None
    }

    def intersection(other: Interval) : Option[Interval] =
      if (overlaps(other)) Option(Interval(max(begin, other.begin), min(end, other.end))) else None

    def truncateLeft(other: Interval) : Option[Interval] =
      if (begin < other.begin && contains(other.begin)) Some(Interval(begin, other.begin)) else None

    def truncateRight(other: Interval): Option[Interval] =
      if (end > other.end && other.contains(this.begin)) Some(Interval(other.end, end)) else None
    override def toString: String = s"[${show(begin)}, ${show(end)}]"

  }

  implicit class ZonedDateTimeTimeDimension(zonedDateTime: ZonedDateTime) extends Ordered[ZonedDateTime] {
    override def compare(that: ZonedDateTime): Int =
      if (zonedDateTime.isBefore(that))  {-1} else if (that.isBefore(zonedDateTime)) {1} else 0
  }

  def show(zonedDateTime: ZonedDateTime) = fmtout.format(zonedDateTime)

  def read(value: String): ZonedDateTime = ZonedDateTime.parse(value + " Europe/Amsterdam", fmtin)




}
