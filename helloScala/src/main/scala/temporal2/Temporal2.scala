package temporal2

import cats.Applicative
import java.time.format.DateTimeFormatter
import java.time.ZonedDateTime
import Intervals._


object Temporal2 {

//  type TimeInterval = Interval[ZonedDateTime]

//  def helper =
//
//  sealed trait TimelineElt[A] {
//    def since: ZonedDateTime
//    def value: A
//
//    def overlaps[B](other: TimelineElt[B]): Boolean =
//      (this, other) match {
//        case (CurrentRecord(_,_), CurrentRecord(_,_)) => true
//        case (HistoryRecord(TimeInterval(_, e1), v1), CurrentRecord(b2, _)) => (b2 < e1)
//        case (CurrentRecord(b2, _), HistoryRecord(TimeInterval(_, e1), v1)) => (b2 < e1)
//        case (HistoryRecord(i1, _), HistoryRecord(i2, _)) => i1.overlaps(i2)
//      }
//
//    def combine[B](other: TimelineElt[A => B]) : Option[CurrentRecord[B]] = {
//      val newBegin = since.max(other.since)
//      val newValue = other.value(value)
//      if (overlaps(other)) Some(CurrentRecord(newBegin, newValue)) else None
//    }
//
//    def restOther[B](other: TimelineElt[B]): Option[TimelineElt[B]] = {
//      (this,other) match {
//        case (_, CurrentRecord(s2, v)) =>
//          if (overlaps(other)) Some(CurrentRecord(since.max(s2), v)) else None
//        case (CurrentRecord(_, _ ), HistoryRecord(_, _)) =>
//          None
//        case (HistoryRecord(i1,_), HistoryRecord(i2,v)) =>
//          i2.truncate(i1).map( HistoryRecord(_,v))
//      }
//    }
//
//    def restThis[B](other: TimelineElt[B]): Option[TimelineElt[A]] = {
//      val newBegin = since.max(other.since)
//      (this, other) match {
//        case (CurrentRecord(_, v), _) =>
//          if (overlaps(other)) Some(CurrentRecord(newBegin, value)) else None
//        case (HistoryRecord(i, v), CurrentRecord(_,_)) => None
//        case (HistoryRecord(i1, v), HistoryRecord(i2,_)) =>
//          i1.truncate(i2).map(HistoryRecord(_, v))
//      }
//    }
//  }
//
//  case class HistoryRecord[A](TimeInterval: Interval, value: A) extends TimelineElt[A] {
//    override def toString: String = s"[${interval}] ${value}"
//
//    override def since: ZonedDateTime = interval.begin
//  }
//
//  case class CurrentRecord[A](override val since: ZonedDateTime, value: A) extends TimelineElt[A] {
//    def until(until: ZonedDateTime) :HistoryRecord[A] = HistoryRecord(TimeInterval(since, until), value)
//    override def toString: String = s"[${format(since)}] ${value}"
//  }
//
//
//
//  case class Timeline[A](current: CurrentRecord[A], history: List[TimelineElt[A]]= List()) {
//
//    def + (newCurrent: CurrentRecord[A]) : Timeline[A] = {
//      if (newCurrent.value != current.value)
//        Timeline(newCurrent, current.until(newCurrent.since) :: history)
//      else
//        this
//    }
//
//    override def toString: String = {
//      val s1: List[String] = history.map(_.toString)
//      val s2 = s1.fold("")( (a, b) => s"${a}\n${b}")
//      s"${current}${s2}"
//    }
//  }
//  val fmtin: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV")
//  val fmtout: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//  def format(zonedDateTime: ZonedDateTime) = fmtout.format(zonedDateTime)
//
//
//
//
//  implicit def timelineApplicative: Applicative[Timeline] = new Applicative[Timeline] {
//    override def pure[A](x: A): Timeline[A] = Timeline(CurrentRecord(timestamp("1900-01-01 00:00"), x))
//
//    override def ap[A, B](ff: Timeline[A => B])(fa: Timeline[A]): Timeline[B] = {
//      Timeline(CurrentRecord(ff.current.since max fa.current.since, ff.current.value(fa.current.value)))
//    }
//  }
//


}
