package temporal2

import java.time.{Instant, ZonedDateTime}

import Intervals._
import org.scalatest.{FlatSpec, Matchers}

class Intervals2Test extends FlatSpec with Matchers {

  val t0 :Instant = Instant.MIN
  val t1 :Instant = read("2011-04-01 16:45")
  val t2 :Instant = read("2012-04-03 11:10")
  val t3 :Instant = read("2013-04-05 10:30")
  val t4 :Instant = read("2014-04-07 14:00")
  val i12 =  Interval(t1, t2)
  val i13 =  Interval(t1, t3)
  val i14 =  Interval(t1, t4)
  val i23 = Interval(t2, t3)
  val i24 = Interval(t2, t4)
  val i34 = Interval(t3, t4)

  "t1" should "show" in {
    show(t1) shouldEqual "2011-04-01 16:45"
  }

  "i14 and i23" should "overlap" in {
    i13.overlaps(i24) shouldEqual (true)
    i23.overlaps(i23) shouldEqual(true)
    i23.overlaps(i14) shouldEqual(true)
    i14.overlaps(i23) shouldEqual(true)
  }
  "i12 and i34" should "not overlap or meet" in {
    i12.overlaps(i23) shouldEqual(false)
    i12.meets((i34)) shouldEqual(false)
    i12.before(i34) shouldEqual(true)
  }

  "i12 and I23" should "meet" in {
    i12.meets(i23) shouldEqual(true)
  }

  "intersections" should "be i23" in {
    i13.intersection(i24) shouldEqual Some(i23)
    i24.intersection(i13) shouldEqual Some(i23)
    i13.intersection(i14) shouldEqual Some(i13)
    i12.intersection(i34) shouldEqual None
  }

}
