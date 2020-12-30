package temporal3


import cats.{Applicative, Order}
import cats.collections.{Discrete, Range}
import scala.annotation.tailrec

class TemporalData {
  case class IntervalData[A, T](start: T, end: T, value: A)(implicit val enum: Discrete[T], implicit val aorder: Order[T]) {
    def this(range: Range[T], value:A)(implicit enum: Discrete[T], order: Order[T]) = this(range.start, range.end, value)(enum, order)
    def range: Range[T] = Range(start, end)

    def append(that: IntervalData[A, T]): List[IntervalData[A, T]] = {
      if (this.value == that.value) {
        plus(this.range, that.range).map(range => IntervalData(range.start, range.end, value))
      } else {
        that :: minus(this.range, that.range)._1.toList.map(range => IntervalData(range.start, range.end, this.value))
      }
    }

    override def toString: String = s"$range => $value"

    private def minus[A](a: Range[A], b: Range[A])(implicit enum: Discrete[A], order: Order[A]): (Option[Range[A]], Option[Range[A]]) = {
      val left = if (order.gt(b.start, a.start)) Some(Range(a.start, order.min(a.end, enum.pred(b.start)))) else None
      val right = if (order.lt(b.end, a.end)) Some(Range(order.max(a.start, enum.succ(b.end)), a.end)) else None
      (left, right)
    }

    private def plus[A] (a:Range[A], b: Range[A])(implicit enum: Discrete[A], order: Order[A]): List[Range[A]] = {
      val sum = a.+(b)
      sum._1 :: sum._2.toList
    }

  }




  case class Timeline[A, T](history: List[IntervalData[A, T]])(implicit val enum: Discrete[T], implicit val order: Order[T]) {

    def append(next: IntervalData[A, T]): Timeline[A, T] = {
      if (history.isEmpty) {
        Timeline(List(next))
      } else if (order.gt(next.range.start, enum.succ(history.head.range.end))) {
        Timeline(next :: history)
      } else if (order.lteqv(next.range.start, history.head.range.start)) {
        Timeline(history.tail).append(next)
      } else {
        Timeline(history.head.append(next) ::: history.tail)
      }
    }

    override def toString: String = history.map(elt => s"$elt\n").fold("")((x, y) => x.concat(y))

   // def map[B](f: A => B): DiscreteTimeline[B] = timeLineApplicative.map(this)(f)
  }



}
