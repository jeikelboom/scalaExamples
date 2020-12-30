package temporal3

import java.time.LocalDate

import cats.{Applicative, Order}
import cats.collections.{Discrete, Range}

import scala.annotation.tailrec

object TemporalData {

  trait TimeUnit[T] extends Order[T] with Discrete[T] {
    val MIN: T
    val MAX: T

  }


  class Time[T](implicit val timeUnit: TimeUnit[T]) {

    case class IntervalData[A](start: T, end: T, value: A)(implicit val timeUnit: TimeUnit[T]) {
      def this(range: Range[T], value:A)(implicit timeUnit: TimeUnit[T]) = this(range.start, range.end, value)(timeUnit)
      def range: Range[T] = Range(start, end)

      def append(that: IntervalData[A]): List[IntervalData[A]] = {
        if (this.value == that.value) {
          plus(this.range, that.range).map(range => IntervalData(range.start, range.end, value))
        } else {
          that :: minus(this.range, that.range)._1.toList.map(range => IntervalData(range.start, range.end, this.value))
        }
      }

      override def toString: String = s"$range => $value"


      private def plus (a:Range[T], b: Range[T]): List[Range[T]] = {
        val sum = a.+(b)
        sum._1 :: sum._2.toList
      }

    }
    def minus(a: Range[T], b: Range[T]): (Option[Range[T]], Option[Range[T]]) = {
      val left = if (timeUnit.gt(b.start, a.start)) Some(Range(a.start, timeUnit.min(a.end, timeUnit.pred(b.start)))) else None
      val right = if (timeUnit.lt(b.end, a.end)) Some(Range(timeUnit.max(a.start, timeUnit.succ(b.end)), a.end)) else None
      (left, right)
    }

    case class Timeline[A](history: List[IntervalData[A]] = List()) {

      def append(next: IntervalData[A]): Timeline[A] = {
        if (history.isEmpty) {
          Timeline(List(next))
        } else if (timeUnit.gt(next.range.start, timeUnit.succ(history.head.range.end))) {
          Timeline(next :: history)
        } else if (timeUnit.lteqv(next.range.start, history.head.range.start)) {
          Timeline(history.tail).append(next)
        } else {
          Timeline(history.head.append(next) ::: history.tail)
        }
      }

      override def toString: String = history.map(elt => s"$elt\n").fold("")((x, y) => x.concat(y))

      def map[B](f: A => B): Timeline[B] = timeLineApplicative.map(this)(f)

      def retroUpdate(timelineElement: IntervalData[A]) ={
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
            val ffrest: List[IntervalData[A => B]] = minus(ff.range, fa.range)._2.toList.map(range =>IntervalData(range.start, range.end, ff.value)) ::: fftail
            val farest: List[IntervalData[A]] = minus(fa.range, ff.range)._2.toList.map(range =>IntervalData(range.start, range.end, fa.value)) ::: fatail
            val accrest: Option[IntervalData[B]] = ff.range.&(fa.range).map(range =>IntervalData(range.start, range.end, ff.value(fa.value)))
            val accuNext: Timeline[B] = accrest.map(e => accu.append(e)).getOrElse(accu)
            accumulator(ffrest, farest, accuNext)
          case _ => accu
        }
      }

    }



  }

}
