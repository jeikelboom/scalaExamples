package Temporal

import org.scalatest.{FlatSpec, Matchers}
import temporal.Temporal._
import cats.Applicative


class TimelineTest  extends FlatSpec with Matchers {
  val t1 = timestamp("2019-04-01T00:00:00")
  val t2 = timestamp("2019-04-03T00:00:00")
  val t3 = timestamp("2019-04-05T00:00:00")
  val t4 = timestamp("2019-04-07T00:00:00")

  "tl1 " should "apply 1" in {
    val afunc : Int => Int  = x  => {10 * x + 5 }
    val tl0: TimelineElement[Int => Int] = Applicative[TimelineElement].pure(afunc)
    val tl1 = TimelineElement(t1, 1)
    val tl4 = Applicative[TimelineElement].ap(tl0)(tl1) //Applicative[TimelineElement].ap(tl1, tl2, tl3)
    tl4 shouldEqual (TimelineElement(t1, 15))
  }
  "tl2 " should "apply 2" in {
    val afunc : (Int, Int) => Int  = (x1, x2) => {10 * x1 + x2 }
    val tl0: TimelineElement[(Int, Int) => Int] = Applicative[TimelineElement].pure(afunc)
    val tl1 = TimelineElement(t1, 3)
    val tl2 = TimelineElement(t2, 7)
    val tl4 = Applicative[TimelineElement].ap2(tl0)( tl1, tl2) //Applicative[TimelineElement].ap(tl1, tl2, tl3)
    tl4 shouldEqual (TimelineElement(t2, 37))
  }

  "tl3 " should "apply 3" in {
    val afunc : (Int, Int, Int) => Int  = (x1, x2, x3) => {100 * x1 + 10 * x2 + x3}
    val tl0: TimelineElement[(Int, Int, Int) => Int] = Applicative[TimelineElement].pure(afunc)
    val tl1 = TimelineElement(t1, 3)
    val tl2 = TimelineElement(t2, 7)
    val tl3 = TimelineElement(t3, 5)
    val tl4 = Applicative[TimelineElement].ap3(tl0)( tl1, tl2, tl3) //Applicative[TimelineElement].ap(tl1, tl2, tl3)
    tl4 shouldEqual (TimelineElement(t3, 375))
  }




}
