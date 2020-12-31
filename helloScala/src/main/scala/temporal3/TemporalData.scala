package temporal3

import cats.collections.{Discrete, Range}
import cats.{Applicative, Order}

import scala.annotation.tailrec

object TemporalData {


  case class Interval[T](start: T, end: T)

  trait TimeUnit[T] extends Order[T]   with Discrete[T] {
    val MIN: T
    val MAX: T

    def contains(interval: Interval[T], timestamp: T): Boolean = lteqv(interval.start, timestamp) && gteqv(interval.end, timestamp)

    def plus (a:Interval[T], b: Interval[T]): List[Interval[T]] = {
      val (first, second) = if (lteqv(a.start, b.start)) (a,b) else (b, a)
      if (gteqv(succ(first.end), second.start)) List(Interval(first.start, max(first.end, second.end)))
      else List(first, second)
    }

    def minus(a: Interval[T], b: Interval[T]): (Option[Interval[T]], Option[Interval[T]]) = {
      val left = if (gt(b.start, a.start)) Some(Interval(a.start, min(a.end, pred(b.start)))) else None
      val right = if (lt(b.end, a.end)) Some(Interval(max(a.start, succ(b.end)), a.end)) else None
      (left, right)
    }

    def intersect(a: Interval[T], b: Interval[T]): Option[Interval[T]]= {
      val start: T = max(a.start, b.start)
      val end: T = min(a.end, b.end)
      if (lteqv(start, end)) Some(Interval(start, end)) else None
    }


  }


  class Time[T](implicit val timeUnit: TimeUnit[T]) {

    case class IntervalData[A](start: T, end: T, value: A)(implicit val timeUnit: TimeUnit[T]) {
      def this(interval: Interval[T], value:A)(implicit timeUnit: TimeUnit[T]) = this(interval.start, interval.end, value)(timeUnit)
      def interval: Interval[T] = Interval(start, end)

      def append(that: IntervalData[A]): List[IntervalData[A]] = {
        if (this.value == that.value) {
          timeUnit.plus(this.interval, that.interval).map(interval => IntervalData(interval.start, interval.end, value))
        } else {
          that :: timeUnit.minus(this.interval, that.interval)._1.toList.map(interval => IntervalData(interval.start, interval.end, this.value))
        }
      }

      override def toString: String = s"$interval => $value"



    }

    case class Timeline[A](history: List[IntervalData[A]] = List()) {

      def append(next: IntervalData[A]): Timeline[A] = {
        if (history.isEmpty) {
          Timeline(List(next))
        } else if (timeUnit.gt(next.interval.start, timeUnit.succ(history.head.interval.end))) {
          Timeline(next :: history)
        } else if (timeUnit.lteqv(next.interval.start, history.head.interval.start)) {
          Timeline(history.tail).append(next)
        } else {
          Timeline(history.head.append(next) ::: history.tail)
        }
      }

      override def toString: String = "\n" + history.map(elt => s"$elt\n").fold("")((x, y) => x.concat(y))

      def map[B](f: A => B): Timeline[B] = timeLineApplicative.map(this)(f)

      def retroUpdate(timelineElement: IntervalData[A]): Timeline[A] ={
        def retro[A](timelineElement: IntervalData[A]): Timeline[Option[A]] = {
          val tle1: IntervalData[Option[A]] = IntervalData(timeUnit.MIN, timeUnit.pred(timelineElement.start), None)
          val tle2: IntervalData[Option[A]] = IntervalData(timelineElement.start, timelineElement.end, Some(timelineElement.value))
          val tle3: IntervalData[Option[A]] = IntervalData(timeUnit.succ(timelineElement.end), timeUnit.MAX, None)
          Timeline[Option[A]]().append(tle1).append(tle2).append(tle3)
        }
        val retrotl: Timeline[Option[A]] = retro(timelineElement)
        val pr: Timeline[(Option[A], A) => A]= timeLineApplicative.pure((x,y) => x.getOrElse(y))
        timeLineApplicative.ap2(pr)(retrotl, this)
      }

    }





    implicit def timeLineApplicative(implicit  timeUnit: TimeUnit[T]): Applicative[Timeline] = new Applicative[Timeline] {

      override def pure[A](x: A): Timeline[A] = new Timeline[A](List(IntervalData(timeUnit.MIN, timeUnit.MAX, x)))

      override def ap[A, B](ff: Timeline[A => B])(fa: Timeline[A]): Timeline[B] = {
        accumulator(ff.history.reverse, fa.history.reverse, Timeline[B](List()))
      }

      @tailrec
      private def accumulator[A, B](ff: List[IntervalData[A => B]], fa: List[IntervalData[A]], accu: Timeline[B]): Timeline[B] = {
        (ff, fa) match {
          case ((ff@IntervalData(_, _, _)) :: fftail, (fa@IntervalData(_, _, _)) :: fatail) =>
            val ffrest: List[IntervalData[A => B]] = timeUnit.minus(ff.interval, fa.interval)._2.toList.map(interval =>IntervalData(interval.start, interval.end, ff.value)) ::: fftail
            val farest: List[IntervalData[A]] = timeUnit.minus(fa.interval, ff.interval)._2.toList.map(interval =>IntervalData(interval.start, interval.end, fa.value)) ::: fatail
            val accrest: Option[IntervalData[B]] = timeUnit.intersect(ff.interval, fa.interval).map(interval =>IntervalData(interval.start, interval.end, ff.value(fa.value)))
            val accuNext: Timeline[B] = accrest.map(e => accu.append(e)).getOrElse(accu)
            accumulator(ffrest, farest, accuNext)
          case _ => accu
        }
      }

    }



  }

}
