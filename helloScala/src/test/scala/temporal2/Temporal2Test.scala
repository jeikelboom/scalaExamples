package temporal2

import org.scalatest.{FlatSpec, Matchers}
import temporal2.Temporal2._
import TestData._


class Temporal2Test  extends FlatSpec with Matchers {

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
    timeline shouldEqual TimeLine(history)
  }

  "timeline" should "build from Intervals" in {
    val timeline: TimeLine[String] = TimeLine(List()).append(i01, "Een").append(i12,"Een")
    val expected = TimeLine(List(TimeLineElement(t0,t2, "Een")))
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
