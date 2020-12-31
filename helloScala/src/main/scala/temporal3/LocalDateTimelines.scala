package temporal3

import java.time.LocalDate

import temporal3.TemporalData._

object LocalDateTimelines {

  private implicit def localDateTimeUnit: DiscreteTimeUnit[LocalDate] = new DiscreteTimeUnit[LocalDate] {
    override val MIN: LocalDate = LocalDate.MIN
    override val MAX: LocalDate = LocalDate.MAX

    override def compare(x: LocalDate, y: LocalDate): Int = x.compareTo(y)

    override def succ(x: LocalDate): LocalDate = x.plusDays(1)

    override def pred(x: LocalDate): LocalDate = x.minusDays(1)
  }

  def date(year: Int, month: Int, day: Int): LocalDate = LocalDate.of(year, month, day)

  object LocalDateTimed extends Time[LocalDate]

}
