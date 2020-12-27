package temporal2

import java.time.LocalDate

import cats.Order
import cats.collections.{Discrete, Range}

object
DiscreteTemporal {

  implicit def localDateEnum: Discrete[LocalDate] = new Discrete[LocalDate] {
    override def succ(x: LocalDate): LocalDate = x.plusDays(1)

    override def pred(x: LocalDate): LocalDate = x.minusDays(1)
  }

  implicit val localDateOrdering: Ordering[LocalDate] = new Ordering[LocalDate] {
    override def compare(x: LocalDate, y: LocalDate): Int = x.compareTo(y)
  }

  implicit val localDateOrder: Order[LocalDate] = new Order[LocalDate] {
    override def compare(x: LocalDate, y: LocalDate): Int = x.compareTo(y)
  }

  def minus[A](a: Range[A], b: Range[A])(implicit enum: Discrete[A], order: Order[A]): (Option[Range[A]], Option[Range[A]])= {
    val left = if (order.gt(b.start, a.start)) Some(Range(a.start, order.min(a.end, enum.pred(b.start)))) else None
    val right = if (order.lt(b.end, a.end)) Some(Range(order.max(a.start, enum.succ(b.end)), a.end)) else None
    (left,right)
  }

  def date(year: Int, month: Int, day: Int) = LocalDate.of(year, month, day)

  case class TimelineElement[A](range: Range[LocalDate], value: A) {
    def append(that: TimelineElement[A]): List[TimelineElement[A]] = {
      if (this.value == that.value) {
        val diff =  this.range.+(that.range)
        (diff._1 :: diff._2.toList).map(TimelineElement(_, value))
      } else {
        val diff = minus(this.range, that.range)
        diff._2.toList.map(TimelineElement(_, that.value)) ::: diff._1.toList.map(TimelineElement(_, this.value))
      }
    }


  }

  case class DiscreteTimeline[A](history: List[TimelineElement[A]]) {

    def append(next: TimelineElement[A]) : DiscreteTimeline[A] = {
      val head = history.head
      if (localDateOrdering.gt(next.range.start, localDateEnum.succ(head.range.end))) {
        DiscreteTimeline(next:: history)
      } else if (localDateOrdering.lteq(next.range.start, head.range.start)) {
        DiscreteTimeline(history.tail).append(next)
      } else {
        DiscreteTimeline(head.append(next)::: history.tail)
      }
    }
  }


}
