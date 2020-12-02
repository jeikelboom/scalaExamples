package temporal2

import org.scalatest.{FlatSpec, Matchers}
import temporal2.Temporal2._


class Temporal2Test  extends FlatSpec with Matchers {

  val t1 = timestamp("2019-04-01T00:00:00")
  val t2 = timestamp("2019-04-03T00:00:00")
  val t3 = timestamp("2019-04-05T00:00:00")
  val t4 = timestamp("2019-04-07T00:00:00")
  //println(timestamp2String(t1))


  "01 " should "before 03 (month)" in {
    (t1 < t2) shouldEqual true
    (t1 < t1) shouldEqual false
    (t1 > t2) shouldEqual false
    (t1 <= t2) shouldEqual true
  }

  "1-5" should "overlap 3-7" in {
    val a: Interval  = Interval(t1, t3)
    val b: Interval  = Interval(t2, t4)
    val inter: Interval = (a intersection  b).get
    a overlaps b shouldEqual  true
    a intersection b shouldEqual Option(Interval(t2, t3))
    a join b shouldEqual Option(Interval(t1, t4))
  }

}
