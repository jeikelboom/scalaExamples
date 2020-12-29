package temporal2

import java.time.LocalDate

import cats.collections.{Discrete, Range}
import cats.{Applicative, Order}

import scala.annotation.tailrec

object
DiscreteTemporal {

  case class TimelineElement[A](start: LocalDate, end: LocalDate, value: A) {
    val range: Range[LocalDate] = Range(start, end)

    def append(that: TimelineElement[A]): List[TimelineElement[A]] = {
      if (this.value == that.value) {
        plus(this.range, that.range).map(TimelineElement(_, value))
      } else {
        that :: minus(this.range, that.range)._1.toList.map(TimelineElement(_, this.value))
      }
    }

    override def toString: String = s"$range => $value"

  }

  object TimelineElement {
    def apply[A](range: Range[LocalDate], value: A): TimelineElement[A] = TimelineElement(range.start, range.end, value)

  }

  case class DiscreteTimeline[A](history: List[TimelineElement[A]]) {

    def append(next: TimelineElement[A]): DiscreteTimeline[A] = {
      if (history.isEmpty) {
        DiscreteTimeline(List(next))
      } else if (localDateOrder.gt(next.range.start, localDateEnum.succ(history.head.range.end))) {
        DiscreteTimeline(next :: history)
      } else if (localDateOrder.lteqv(next.range.start, history.head.range.start)) {
        DiscreteTimeline(history.tail).append(next)
      } else {
        DiscreteTimeline(history.head.append(next) ::: history.tail)
      }
    }

    override def toString: String = history.map(elt => s"$elt\n").fold("")((x, y) => x.concat(y))
  }

  object DiscreteTimeline{
    def apply[A](): DiscreteTimeline[A] = new DiscreteTimeline(List())
  }


  implicit def timeLineApplicative: Applicative[DiscreteTimeline] = new Applicative[DiscreteTimeline] {

    override def pure[A](x: A): DiscreteTimeline[A] = new DiscreteTimeline[A](List(TimelineElement(Range(LocalDate.MIN, LocalDate.MAX), x)))

    override def ap[A, B](ff: DiscreteTimeline[A => B])(fa: DiscreteTimeline[A]): DiscreteTimeline[B] = {
      accumulator(ff.history.reverse, fa.history.reverse, DiscreteTimeline[B](List()))
    }

    @tailrec
    private def accumulator[A, B](ff: List[TimelineElement[A => B]], fa: List[TimelineElement[A]], accu: DiscreteTimeline[B]): DiscreteTimeline[B] = {
      (ff, fa) match {
        case ((ff@TimelineElement(_, _, _)) :: fftail, (fa@TimelineElement(_, _, _)) :: fatail) =>
          val ffrest: List[TimelineElement[A => B]] = minus(ff.range, fa.range)._2.toList.map(TimelineElement(_, ff.value)) ::: fftail
          val farest: List[TimelineElement[A]] = minus(fa.range, ff.range)._2.toList.map(TimelineElement(_, fa.value)) ::: fatail
          val accrest: Option[TimelineElement[B]] = ff.range.&(fa.range).map(TimelineElement(_, ff.value(fa.value)))
          val accuNext: DiscreteTimeline[B] = accrest.map(e => accu.append(e)).getOrElse(accu)
          accumulator(ffrest, farest, accuNext)
        case _ => accu
      }
    }

  }

  implicit def localDateEnum: Discrete[LocalDate] = new Discrete[LocalDate] {
    override def succ(x: LocalDate): LocalDate = x.plusDays(1)

    override def pred(x: LocalDate): LocalDate = x.minusDays(1)
  }

  implicit def localDateOrder: Order[LocalDate] = (x: LocalDate, y: LocalDate) => x.compareTo(y)

  def minus[A](a: Range[A], b: Range[A])(implicit enum: Discrete[A], order: Order[A]): (Option[Range[A]], Option[Range[A]]) = {
    val left = if (order.gt(b.start, a.start)) Some(Range(a.start, order.min(a.end, enum.pred(b.start)))) else None
    val right = if (order.lt(b.end, a.end)) Some(Range(order.max(a.start, enum.succ(b.end)), a.end)) else None
    (left, right)
  }

  def plus[A] (a:Range[A], b: Range[A])(implicit enum: Discrete[A], order: Order[A]): List[Range[A]] = {
    val sum = a.+(b)
    sum._1 :: sum._2.toList
  }

  def date(year: Int, month: Int, day: Int): LocalDate = LocalDate.of(year, month, day)


}
