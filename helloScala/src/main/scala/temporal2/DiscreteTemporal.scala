package temporal2

import java.time.LocalDate

import cats.{Functor, Applicative, Order}
import cats.collections.{Discrete, Range}
import temporal2.Temporal2.TimeLine

object
DiscreteTemporal {

  implicit def localDateEnum: Discrete[LocalDate] = new Discrete[LocalDate] {
    override def succ(x: LocalDate): LocalDate = x.plusDays(1)
    override def pred(x: LocalDate): LocalDate = x.minusDays(1)
  }

  implicit def localDateOrder: Order[LocalDate] = (x: LocalDate, y: LocalDate) => x.compareTo(y)

  def minus[A](a: Range[A], b: Range[A])(implicit enum: Discrete[A], order: Order[A]): (Option[Range[A]], Option[Range[A]])= {
    val left = if (order.gt(b.start, a.start)) Some(Range(a.start, order.min(a.end, enum.pred(b.start)))) else None
    val right = if (order.lt(b.end, a.end)) Some(Range(order.max(a.start, enum.succ(b.end)), a.end)) else None
    (left,right)
  }

  def date(year: Int, month: Int, day: Int): LocalDate  = LocalDate.of(year, month, day)

  case class TimelineElement[A](range: Range[LocalDate], value: A) {


    def append(that: TimelineElement[A]): List[TimelineElement[A]] = {
      if (this.value == that.value) {
        val diff =  this.range.+(that.range)
        (diff._1 :: diff._2.toList).map(TimelineElement(_, value))
      } else {
        val diff = minus(this.range, that.range)
        that :: diff._1.toList.map(TimelineElement(_, this.value))
      }
    }

    override def toString: String = s"${range} => $value"

  }
   object TimelineElement {
     def apply[A](start: LocalDate, end: LocalDate, value: A): TimelineElement[A] = TimelineElement(Range(start, end), value)

   }

  case class DiscreteTimeline[A](history: List[TimelineElement[A]]) {

    def append(next: TimelineElement[A]) : DiscreteTimeline[A] = {
      if (history.isEmpty) {
        DiscreteTimeline(List(next))
      } else if (localDateOrder.gt(next.range.start, localDateEnum.succ(history.head.range.end))) {
        DiscreteTimeline(next:: history)
      } else if (localDateOrder.lteqv(next.range.start, history.head.range.start)) {
        DiscreteTimeline(history.tail).append(next)
      } else {
        DiscreteTimeline(history.head.append(next)::: history.tail)
      }
    }

    override def toString: String = history.map(elt => s"${elt}\n").fold("")((x, y) => x.concat(y))
  }


  implicit def timeLineApplicative: Applicative[DiscreteTimeline] = new Applicative[DiscreteTimeline]{

    override def pure[A](x: A): DiscreteTimeline[A] = new DiscreteTimeline[A](List(TimelineElement(Range(LocalDate.MIN, LocalDate.MAX), x)))

    override def ap[A, B](ff: DiscreteTimeline[A => B])(fa: DiscreteTimeline[A]): DiscreteTimeline[B] = {
      val ffList = ff.history.reverse;
      val faList = fa.history.reverse
      accumulator(ffList, faList, DiscreteTimeline[B](List()))
    }

    private def accumulator[A, B](ff: List[TimelineElement[A => B]], fa: List[TimelineElement[A]], accu: DiscreteTimeline[B]): DiscreteTimeline[B] = {
      (ff, fa) match {
        case (TimelineElement(ffr, ffv)::fftail, TimelineElement(far,fav)::fatail) => {
        val ffrest: List[TimelineElement[A => B]] = minus(ffr, far)._2.toList.map(TimelineElement(_, ffv))::: fftail
        val farest: List[TimelineElement[A]] = minus(far,ffr)._2.toList.map((TimelineElement(_, fav))):::fatail
        val accrest: Option[TimelineElement[B]] = ffr.&(far).map(TimelineElement(_, ffv(fav)))
        val accuNext : DiscreteTimeline[B] = accrest.map(e => accu.append(e)).getOrElse(accu)
        accumulator(ffrest, farest, accuNext)
      }
        case _ => accu
      }
    }

  }

}
