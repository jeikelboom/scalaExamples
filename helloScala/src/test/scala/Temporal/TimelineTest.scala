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
    val afunc : Int => Int  = x  => {2* x + 5 }
    val tl0: Timeline[Int => Int] = Applicative[Timeline].pure(afunc)
    val tl1 = Timeline(1,t1)
    val tl2 = Timeline(2, t2)
    val tl4 = Applicative[Timeline].ap(tl0)(tl1) //Applicative[Timeline].ap(tl1, tl2, tl3)
    tl4 shouldEqual (Timeline(7, t1))
  }
  "tl2 " should "apply 2" in {
    val afunc : (Int, Int) => Int  = (x, y) => {2* x + 5*y }
    val tl0: Timeline[(Int, Int) => Int] = Applicative[Timeline].pure(afunc)
    val tl1 = Timeline(1,t1)
    val tl2 = Timeline(2, t2)
    val tl4 = Applicative[Timeline].ap2(tl0)( tl1, tl2) //Applicative[Timeline].ap(tl1, tl2, tl3)
    tl4 shouldEqual (Timeline(12, t2))
  }



}
