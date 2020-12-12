package temporal2

import Intervals._
import org.scalatest.{FlatSpec, Matchers}
import TestData._

class Intervals2Test extends FlatSpec with Matchers {

  "t1" should "show" in {
    showInstant(t1) shouldEqual "2011-04-01 10:10"
  }

  "i14 and i23" should "overlap" in {
    i13.overlaps(i24) shouldEqual true
    i23.overlaps(i23) shouldEqual true
    i23.overlaps(i14) shouldEqual true
    i14.overlaps(i23) shouldEqual true
  }
  "i12 and i34" should "not overlap or meet" in {
    i12.overlaps(i23) shouldEqual false
    i12.meets(i34) shouldEqual false
    i12.before(i34) shouldEqual true
  }

  "i12 and I23" should "meet" in {
    i12.meets(i23) shouldEqual true
  }

  "intersections" should "be i23" in {
    i13.intersection(i24) shouldEqual Some(i23)
    i24.intersection(i13) shouldEqual Some(i23)
    i13.intersection(i14) shouldEqual Some(i13)
    i12.intersection(i34) shouldEqual None
  }

}
