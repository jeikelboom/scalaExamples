package temporal2

import cats.Applicative
import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId, ZonedDateTime}
import java.util.Locale

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

    override def toString: String = s"[${format(begin)}, ${format(end)}]"

  }

  implicit class ZonedDateTimeOrdering(ZonedDateTime: ZonedDateTime) extends Ordered[ZonedDateTime] {
    override def compare(that: ZonedDateTime): Int =
      if (ZonedDateTime.isBefore(that))  {-1} else if (that.isBefore(ZonedDateTime)) {1} else 0
    def max(other: ZonedDateTime): ZonedDateTime = if (ZonedDateTime > other) {ZonedDateTime} else other
    def min(other: ZonedDateTime): ZonedDateTime = if (ZonedDateTime < other) {ZonedDateTime} else other
  }



  case class HistoryRecord[A](interval: Interval, value: A) {
    override def toString: String = s"[${interval}] ${value}"
  }
  case class CurrentRecord[A](since: ZonedDateTime, value: A) {
    def until(until: ZonedDateTime) :HistoryRecord[A] = HistoryRecord(Interval(since, until), value)
    override def toString: String = s"[${format(since)}] ${value}"
  }

  case class Timeline[A](current: CurrentRecord[A], history: List[HistoryRecord[A]]= List()) {
    def + (newCurrent: CurrentRecord[A]) : Timeline[A] = {
      if (newCurrent.value != current.value)
        Timeline(newCurrent, current.until(newCurrent.since) :: history)
      else
        this
    }

    override def toString: String = {
      val s1: List[String] = history.map(_.toString)
      val s2 = s1.fold("")( (a, b) => s"${a}\n${b}")
      s"${current}${s2}"
    }
  }
  val fmtin: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV")
  val fmtout: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
  def format(zonedDateTime: ZonedDateTime) = fmtout.format(zonedDateTime)
  def timestamp(value: String): ZonedDateTime = ZonedDateTime.parse(value + " Europe/Amsterdam", fmtin)


  implicit def timelineApplicative: Applicative[Timeline] = new Applicative[Timeline] {
    override def pure[A](x: A): Timeline[A] = Timeline(CurrentRecord(timestamp("1900-01-01 00:00"), x))

    override def ap[A, B](ff: Timeline[A => B])(fa: Timeline[A]): Timeline[B] = {
      Timeline(CurrentRecord(ff.current.since max fa.current.since, ff.current.value(fa.current.value)))
    }
  }



}
