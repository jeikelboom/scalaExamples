package temporal2


import java.time.{Instant, LocalDateTime, ZoneId, ZoneOffset, ZonedDateTime}
import java.time.format.DateTimeFormatter


object Intervals {

  val FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

  implicit def ordering: Ordering[Instant] = new Ordering[Instant] {
    override def compare(x: Instant, y: Instant): Int = x.compare(y)
  }


  case class Interval(val begin: Instant, val end: Instant) {

    def before(other: Interval) : Boolean = end < other.begin
    def meets(other: Interval) : Boolean = end == other.begin
    def overlaps(other: Interval) : Boolean = other.end > begin && end > other.begin
    def contains(other: Interval) : Boolean = begin <= other.begin & end >= other.end
    def contains(timestamp: Instant) = begin <= timestamp && end > timestamp

    def join(other: Interval) : Option[Interval] = {
      val f = ordering.min(begin, other.begin)
      val t = ordering.max(end, other.end)
      if (other.begin <= end && begin <= other.end)  Some(Interval(f, t)) else None
    }

    def intersection(other: Interval) : Option[Interval] =
      if (overlaps(other)) Option(Interval(ordering.max(begin, other.begin), ordering.min(end, other.end))) else None

    // leafs the right part of this minus the other
    def minusOtherRightpart(other: Interval) : Option[Interval] =
      if (end > other.end) Some(Interval(other.end, end)) else None

    // leafs the left part of this minus the other
    def minusOtherLeftPart(other: Interval): Option[Interval] =
      if (begin < other.begin) Some(Interval(begin, other.begin)) else None

    override def toString: String = s"[${showInstant(begin)}, ${showInstant(end)}]"

    def show: String = s"[${showInstant(begin)}, ${showInstant(end)})"
  }

  implicit class ZonedDateTimeTimeDimension(zonedDateTime: Instant) extends Ordered[Instant] {
    override def compare(that: Instant): Int =
      if (zonedDateTime.isBefore(that))  {-1} else if (that.isBefore(zonedDateTime)) {1} else 0
  }

  //def show(zonedDateTime: ZonedDateTime) = FORMAT.format(zonedDateTime)
  def showInstant(instant: Instant) =
    if (instant == Instant.MAX)  "--------------->"
    else if (instant == Instant.MIN) "<---------------"
    else LocalDateTime.ofInstant(instant, ZoneId.of("UTC")).format(FORMAT)
  def read(value: String): Instant = LocalDateTime.parse(value,  FORMAT).toInstant(ZoneOffset.UTC)




}
