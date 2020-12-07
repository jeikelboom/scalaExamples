package temporal2

import java.time.Instant

import cats.Applicative
import org.scalatest.{FlatSpec, Matchers}
import temporal2.Intervals.{Interval, read}
import temporal2.Temporal2._


class Temporal2Test  extends FlatSpec with Matchers {


  val t0 :Instant = Instant.MIN
  val t9 :Instant = Instant.MAX
  val t1 :Instant = read("2011-04-01 10:10")
  val t2 :Instant = read("2012-04-01 11:11")
  val t3 :Instant = read("2013-04-01 12:12")
  val t4 :Instant = read("2014-04-01 13:13")
  val t5 :Instant = read("2015-04-01 14:14")
  val i12 = Interval(t1, t2)
  val i13 = Interval(t1, t3)
  val i45 = Interval(t4, t5)
  val i23 = Interval(t2, t3)
  val i01 = Interval(t0, t1)
  val i04 = Interval(t0, t4)
  val i09 = Interval(t0, t9)
  val i19 = Interval(t1, t9)
  val i29 = Interval(t2, t9)
  val i34 = Interval(t3, t4)
  val i39 = Interval(t3, t9)

  "timeline" should "build from open Intervals" in {
    val timeline: TimeLine[String] = TimeLine(List())
      .append(i04,"Nul")
      .append(i19,"Een")
      .append(i29,"Twee")
      .append(i39, "Drie")
    val history = List(
      TimeLineElement(i39, "Drie"),
      TimeLineElement(i23, "Twee"),
      TimeLineElement(i12, "Een"),
      TimeLineElement(i01, "Nul"))
    timeline shouldEqual(TimeLine(history))
  }

  "timeline" should "build from Intervals" in {
    val timeline: TimeLine[String] = TimeLine(List()).append(i01, "Een").append(i12,"Een")
    val expected = TimeLine(List(TimeLineElement(Interval(t0,t2), "Een")))
    timeline shouldEqual expected
  }

  "timeline" should "contain 2 pieces adjacent" in {
    val timeline: TimeLine[String] = TimeLine(List()).append(i01, "Een").append(i12,"Twee")
    val expected = TimeLine(List(TimeLineElement(i12, "Twee"),TimeLineElement(i01, "Een")))
    timeline shouldEqual expected
  }

  "timeline" should "contain 2 pieces not adjacent" in {
    val timeline: TimeLine[String] = TimeLine(List()).append(i12, "Een").append(i34,"drie-vier")
    val expected = TimeLine(List(TimeLineElement(i34, "drie-vier"),TimeLineElement(i12, "Een")))
    timeline shouldEqual expected
  }


}
