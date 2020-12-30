package temporal3

import java.time.LocalDate

import cats.Order
import cats.collections.Discrete

object LocalDateTimelines {

  implicit def localDateEnum: Discrete[LocalDate] = new Discrete[LocalDate] {
    override def succ(x: LocalDate): LocalDate = x.plusDays(1)

    override def pred(x: LocalDate): LocalDate = x.minusDays(1)
  }

  implicit def localDateOrder: Order[LocalDate] = (x: LocalDate, y: LocalDate) => x.compareTo(y)
  def date(year: Int, month: Int, day: Int): LocalDate = LocalDate.of(year, month, day)

}
