package temporal2

import java.time.LocalDate

import cats.Order
import cats.collections.{Discrete, Range}

object
DiscreteTemporal {

  implicit def localDateDiscrete: Discrete[LocalDate] = new Discrete[LocalDate] {
    override def succ(x: LocalDate): LocalDate = x.plusDays(1)

    override def pred(x: LocalDate): LocalDate = x.minusDays(1)
  }

  implicit val localDateOrdering: Ordering[LocalDate] = new Ordering[LocalDate] {
    override def compare(x: LocalDate, y: LocalDate): Int = x.compareTo(y)
  }

  implicit def localDateOrder: Order[LocalDate] = new Order[LocalDate] {
    override def compare(x: LocalDate, y: LocalDate): Int = x.compareTo(y)
  }

  def minus[A](a: Range[A], b: Range[A])(implicit enum: Discrete[A], order: Order[A]): (Option[Range[A]], Option[Range[A]])= {
    val left = if (order.gt(b.start, a.start)) Some(Range(a.start, order.min(a.end, enum.pred(b.start)))) else None
    val right = if (order.lt(b.end, a.end)) Some(Range(order.max(a.start, enum.succ(b.end)), a.end)) else None
    (left,right)
  }

  def date(year: Int, month: Int, day: Int) = LocalDate.of(year, month, day)

  case class TimelineElement[A](range: Range[LocalDate], value: A) {


  }

  case class DiscreteTimeline[A](since: LocalDate, value: A)(implicit enum: Discrete[LocalDate], order: Order[LocalDate]) {

  }

}
