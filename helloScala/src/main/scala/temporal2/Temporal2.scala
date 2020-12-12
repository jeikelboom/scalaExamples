package temporal2

import java.time.Instant

import cats.{Functor, Applicative}
import temporal2.Intervals._


object Temporal2 {

  val ALWAYS: Interval = Interval(Instant.MIN, Instant.MAX)

  case class TimeLineElement[A](interval: Interval, value: A) {

    def this(begin: Instant, end: Instant, value: A) = this(Interval(begin, end), value)

    def begin: Instant = interval.begin

    def end: Instant = interval.end

    def join[B](other: TimeLineElement[A => B]): Option[TimeLineElement[B]] = {
      interval.intersection(other.interval).map(TimeLineElement(_, other.value(value)))
    }

    def leftOverFromThis[B](other: TimeLineElement[A => B]): Option[TimeLineElement[A]] =
      interval.minusOtherRightpart(other.interval).map(TimeLineElement(_, value))

    def leftOverFromOther[B](other: TimeLineElement[A => B]): Option[TimeLineElement[A => B]] =
      other.interval.minusOtherRightpart(this.interval).map(TimeLineElement(_, other.value))

    def show: String = s"${interval.show} - $value"

    def append(other: TimeLineElement[A]): List[TimeLineElement[A]] = {
      assert(other.begin > this.begin)
      if (value == other.value)
        interval.join(other.interval) match {
          case Some(i) => List(TimeLineElement(i, value))
          case _ => List(other, this)
        }
      else
        if (end > other.begin) List(other, TimeLineElement(Interval(begin, other.begin), value))
        else List(other, this)
    }
  }

  object TimeLineElement {
    def apply[A](begin: Instant, end: Instant, value: A): TimeLineElement[A] = new TimeLineElement(begin, end, value)
  }

  case class TimeLine[A](history: List[TimeLineElement[A]]) {

    def this() = this(List())

    def append(interval: Interval, value: A): TimeLine[A] = {
      val theNewOne: TimeLineElement[A] = TimeLineElement(interval, value)
      val newHistory = history match {
        case Nil => List(theNewOne)
        case head :: tail => head.append(theNewOne) ::: tail
      }
      TimeLine(newHistory)
    }

    def appendSomeElement(element: Option[TimeLineElement[A]]): TimeLine[A] = element match {
      case Some(e) => append(e.interval, e.value)
      case _ => this
    }

    def show: String = "\n" +
      history.reverse.map(elt => s"${elt.show}\n").fold("")((x, y) => x.concat(y)) + "\n"


    override def toString: String = history.map(elt => s"${elt.show}\n").fold("")((x, y) => x.concat(y))

  }


  implicit def timeLineApplicative: Applicative[TimeLine] = new Applicative[TimeLine] {
    override def pure[A](value: A): TimeLine[A] = {
      val timeLineElement = TimeLineElement(ALWAYS, value)
      TimeLine(List(timeLineElement))
    }

    override def ap[A, B](ff: TimeLine[A => B])(fa: TimeLine[A]): TimeLine[B] = {
      val ffList: List[TimeLineElement[A => B]] = ff.history.reverse
      val faList: List[TimeLineElement[A]] = fa.history.reverse
      apAccumulator(ffList, faList, TimeLine(List()))
    }


    private def apAccumulator[A, B]( ff: List[TimeLineElement[A => B]],
                                     fa: List[TimeLineElement[A]],
                                     accu: TimeLine[B]): TimeLine[B] = {
      (ff, fa) match {
        case (hff::tailff, hfa::tailfa) => {
          val ffleftover: List[TimeLineElement[A => B]] = consOpt(hfa.leftOverFromOther(hff), tailff)
          val faLeftover: List[TimeLineElement[A]] = consOpt(hfa.leftOverFromThis(hff), tailfa)
          val accuAdd: TimeLine[B] = accu.appendSomeElement(hfa.join(hff))
          apAccumulator(ffleftover, faLeftover, accuAdd)
        }
        case _ => accu
      }
    }
    private def consOpt[A](option: Option[A], aList: List[A]): List[A] = option match {
      case Some(a) => a::aList
      case _ => aList
    }
  }


}
