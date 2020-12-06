package temporal2

import cats.Applicative
import java.time.format.DateTimeFormatter
import java.time.Instant
import Intervals._


object Temporal2 {

  val ALWAYS = Interval(Instant.MIN, Instant.MAX)

  case class TimeLineElement[A](interval: Interval, value: A){
    def result[B](other: TimeLineElement[A => B]) : Option[TimeLineElement[B]] = {
      interval.intersection(other.interval).map(TimeLineElement(_,other.value(value)))
    }
    def leftOverFromThis[B](other: TimeLineElement[A => B]) : Option[TimeLineElement[A]] =
      interval.truncateLeft(other.interval).map(TimeLineElement(_, value))

    def leftOverFromOther[B](other: TimeLineElement[A => B]) : Option[TimeLineElement[A => B]] =
      other.interval.truncateLeft(this.interval).map(TimeLineElement(_, other.value))

    def show: String = s"${interval.show} - ${value}"


  }

  case class TimeLine[A] (history: List[TimeLineElement[A]])  {
    def append(interval: Interval, value: A) : TimeLine[A] = {
      val theNewOne: TimeLineElement[A] = TimeLineElement(interval, value)
      val newHistory = history match {
        case Nil => List(theNewOne)
        case head::tail => head match {
          case TimeLineElement(i, theNewOne.value) => head.interval.join(theNewOne.interval) match {
            case Some(joined) => TimeLineElement(joined, head.value):: (tail)
            case None => theNewOne :: history
          }
          case _ => theNewOne :: history
        }
      }
      TimeLine(newHistory)
    }

    def show: String = {
      history.map(elt => s"${elt.show}\n").fold("")((x, y)=> x.concat(y))
    }

  }


  implicit def timeLineApplicative: Applicative[TimeLine] = new Applicative[TimeLine] {
    override def pure[A](value: A): TimeLine[A] = {
      val timeLineElement = TimeLineElement(ALWAYS, value)
      TimeLine(List(timeLineElement))
    }

    override def ap[A, B](ff: TimeLine[A => B])(fa: TimeLine[A]): TimeLine[B] = {
      val value = ff.history.head.value(fa.history.head.value)
      TimeLine(List(TimeLineElement(ALWAYS, value)))
    } }






}
