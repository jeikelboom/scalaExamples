package temporal2

import java.time.LocalDate

import cats.collections.{Range, _}
import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}
import temporal2.DiscreteTemporal._
import TestData2._

class DiscreteTemporalTest extends FlatSpec with Matchers {

  val range = Range(date(2020,3,15), date(2020,4,15))
  val range1 = Range(date(2020,2,1), date(2020,2,2))
  val range2 = Range(date(2020,2,1), date(2020,3,16))
  val range3 = Range(date(2020,3,16), date(2020,9,2))
  val range4 = Range(date(2020,2,1), date(2020,9,2))

  "range" should "contain " in {
    range.contains(date(2020, 4,10)) shouldEqual true
    val m1: LocalDate = date(2000, 3, 1)
    val f29= localDateEnum.pred(m1)
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



  "timeline eq future"  should "append" in {
    val tle1 = TimelineElement(Range(d1, d2), v1)
    val tle2 = TimelineElement(Range(d3, d5), v1)
    val tl1: DiscreteTimeline[String]  = DiscreteTimeline(List(tle1))
    val tl2 = tl1.append(tle2)
    tl2 shouldEqual DiscreteTimeline(List(tle2, tle1))
  }

  "timeline neq future"  should "append" in {
    val tle1 = TimelineElement(Range(d1, d2), v1)
    val tle2 = TimelineElement(Range(d3, d5), v2)
    val tl1: DiscreteTimeline[String]  = DiscreteTimeline(List(tle1))
    val tl2 = tl1.append(tle2)
    tl2 shouldEqual DiscreteTimeline(List(tle2, tle1))
  }

  "timeline eq meeting"  should "append" in {
    val tle1 = TimelineElement(Range(d1, d2), v1)
    val tle2 = TimelineElement(Range(d2.plusDays(1), d5), v1)
    val tl1: DiscreteTimeline[String]  = DiscreteTimeline(List(tle1))
    val tl2 = tl1.append(tle2)
    tl2 shouldEqual DiscreteTimeline(List(TimelineElement(Range(d1,d5), v1)))
  }

  "timeline neq meeting"  should "append" in {
    val tle1 = TimelineElement(Range(d1, d2), v1)
    val tle2 = TimelineElement(Range(d2.plusDays(1), d5), v2)
    val tl1: DiscreteTimeline[String]  = DiscreteTimeline(List(tle1))
    val tl2 = tl1.append(tle2)
    tl2 shouldEqual DiscreteTimeline(List(tle2, tle1))
  }

  "timeline neq overlapping"  should "append" in {
    val tle1 = TimelineElement(Range(d1, d3), v1)
    val tle2 = TimelineElement(Range(d2, d5), v2)
    val tl1: DiscreteTimeline[String]  = DiscreteTimeline(List(tle1))
    val tl2 = tl1.append(tle2)
    tl2 shouldEqual DiscreteTimeline(List(TimelineElement(Range(d2,d5), v2),
      TimelineElement(Range(d1, localDateEnum.pred(d2)), v1)))
  }

  "timeline neq before"  should "append" in {
    val tle1 = TimelineElement(Range(d1, d3), v1)
    val tle2 = TimelineElement(Range(d4, d5), v2)
    val tle3 = TimelineElement(Range(d6, d7), v3)
    val tle4 = TimelineElement(Range(d2, d5), v4)
    val tl1: DiscreteTimeline[String]  = DiscreteTimeline(List(tle3, tle2, tle1))
    val tl2 = tl1.append(tle4)
    tl2 shouldEqual DiscreteTimeline(List(tle4, TimelineElement(Range(d1, localDateEnum.pred(d2)), v1)))
  }



}
