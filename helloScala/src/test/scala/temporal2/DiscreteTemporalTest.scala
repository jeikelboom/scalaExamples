package temporal2

import java.time.LocalDate

import cats.collections._
import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}
import temporal2.DiscreteTemporal._

class DiscreteTemporalTest extends FlatSpec with Matchers {

  val range = Range(date(2020,3,15), date(2020,4,15))
  val range1 = Range(date(2020,2,1), date(2020,2,2))
  val range2 = Range(date(2020,2,1), date(2020,3,16))
  val range3 = Range(date(2020,3,16), date(2020,9,2))
  val range4 = Range(date(2020,2,1), date(2020,9,2))

  "range" should "contain " in {
    range.contains(date(2020, 4,10)) shouldEqual true
    val m1: LocalDate = date(2000, 3, 1)
    val f29= localDateDiscrete.pred(m1)
    f29 shouldEqual date(2000,2,29)
  }

  "range = range1" should "(None, Some(range)" in {
    minus(range, range1) shouldEqual (None, Some(range))
    minus(range, range2) shouldEqual (None, Some(Range(date(2020,3,17),range.end)))
    minus(range, range3) shouldEqual (Some(Range(date(2020,3,15), date(2020,3,15))), None)
    minus(range, range4) shouldEqual (None, None)
    minus(range, range) shouldEqual (None, None)
    minus(range4, range) shouldEqual (Some(Range(date(2020,2,1), date(2020,3,14))),
                                      Some(Range(date(2020,4,16), date(2020,9,2))))
  }

}
