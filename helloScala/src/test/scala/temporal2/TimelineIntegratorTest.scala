package temporal2

import java.time.LocalDate

import cats.collections.Range
import org.scalatest.{FlatSpec, Matchers}
import temporal2.DiscreteTemporal._
import temporal2.TestData2._
import org.scalatest.{FlatSpec, Matchers}

class TimelineIntegratorTest  extends FlatSpec with Matchers {
  val tle1 = TimelineElement(d1, d2, 10)
  val tle2 = TimelineElement(d3, d4, 100)
  val tle :DiscreteTimeline[Int] = DiscreteTimeline().append(tle1).append(tle2)

  def rangeSize(range: Range[LocalDate]): Int = {
    range.start.datesUntil(localDateEnum.succ(range.end)).count().toInt
  }

  "rangesize" should "work" in {
    val aRange = Range(date(2020,3,5), date(2020,3,10))
    rangeSize(aRange) shouldEqual 6
    rangeSize(Range(date(2003,1,1), date(2003,12,31))) shouldEqual 365
    rangeSize(Range(date(2004,3,4), date(2004,4,4))) shouldEqual 32
    rangeSize(Range(date(2003,3,3), date(2004,4,4))) shouldEqual 399

  }

  "tle" should "integrate" in {
    def size(tle: TimelineElement[Int]) = rangeSize(tle.range) * tle.value
    val total = tle.history.foldLeft(0)((accu, next) => {
      accu + size(next)
    })
    total shouldEqual 43880
  }


}
