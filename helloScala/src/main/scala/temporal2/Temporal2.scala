package temporal2

import cats.Applicative
import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneId, ZonedDateTime}
import java.util.Locale

import temporal2.Temporal2.TimelineElt

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

    def truncate(other: Interval) : Option[Interval] =
      if (other.begin < end ) Some(Interval(begin.max(other.begin), end)) else None

    override def toString: String = s"[${format(begin)}, ${format(end)}]"

  }

  implicit class ZonedDateTimeOrdering(ZonedDateTime: ZonedDateTime) extends Ordered[ZonedDateTime] {
    override def compare(that: ZonedDateTime): Int =
      if (ZonedDateTime.isBefore(that))  {-1} else if (that.isBefore(ZonedDateTime)) {1} else 0
    def max(other: ZonedDateTime): ZonedDateTime = if (ZonedDateTime > other) {ZonedDateTime} else other
    def min(other: ZonedDateTime): ZonedDateTime = if (ZonedDateTime < other) {ZonedDateTime} else other
  }


  sealed trait TimelineElt[A] {
    def since: ZonedDateTime
    def value: A

    def overlaps[B](other: TimelineElt[B]): Boolean =
      (this, other) match {
        case (CurrentRecord(_,_), CurrentRecord(_,_)) => true
        case (HistoryRecord(Interval(_, e1), v1), CurrentRecord(b2, _)) => (b2 < e1)
        case (CurrentRecord(b2, _), HistoryRecord(Interval(_, e1), v1)) => (b2 < e1)
        case (HistoryRecord(i1, _), HistoryRecord(i2, _)) => i1.overlaps(i2)
      }

    def combine[B](other: TimelineElt[A => B]) : Option[CurrentRecord[B]] = {
      val newBegin = since.max(other.since)
      val newValue = other.value(value)
      if (overlaps(other)) Some(CurrentRecord(newBegin, newValue)) else None
    }

    def restOther[B](other: TimelineElt[B]): Option[TimelineElt[B]] = {
      (this,other) match {
        case (_, CurrentRecord(s2, v)) =>
          if (overlaps(other)) Some(CurrentRecord(since.max(s2), v)) else None
        case (CurrentRecord(_, _ ), HistoryRecord(_, _)) =>
          None
        case (HistoryRecord(i1,_), HistoryRecord(i2,v)) =>
          i2.truncate(i1).map( HistoryRecord(_,v))
      }
    }

    def restThis[B](other: TimelineElt[B]): Option[TimelineElt[A]] = {
      val newBegin = since.max(other.since)
      (this, other) match {
        case (CurrentRecord(_, v), _) =>
          if (overlaps(other)) Some(CurrentRecord(newBegin, value)) else None
        case (HistoryRecord(i, v), CurrentRecord(_,_)) => None
        case (HistoryRecord(i1, v), HistoryRecord(i2,_)) =>
          i1.truncate(i2).map(HistoryRecord(_, v))
      }
    }
  }

  case class HistoryRecord[A](interval: Interval, value: A) extends TimelineElt[A] {
    override def toString: String = s"[${interval}] ${value}"

    override def since: ZonedDateTime = interval.begin
  }

  case class CurrentRecord[A](override val since: ZonedDateTime, value: A) extends TimelineElt[A] {
    def until(until: ZonedDateTime) :HistoryRecord[A] = HistoryRecord(Interval(since, until), value)
    override def toString: String = s"[${format(since)}] ${value}"
  }



  case class Timeline[A](current: CurrentRecord[A], history: List[TimelineElt[A]]= List()) {

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
