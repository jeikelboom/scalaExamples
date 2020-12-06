package temporal2

import java.time.Instant

import cats.Applicative
import org.scalatest.{FlatSpec, Matchers}
import temporal2.Intervals.{Interval, read}
import temporal2.Temporal2._


class Temporal2Test  extends FlatSpec with Matchers {


  val t0 :Instant = Instant.MIN
  val t1 :Instant = read("2011-04-01 10:10")
  val t2 :Instant = read("2012-04-01 11:11")
  val t3 :Instant = read("2013-04-01 12:12")
  val t4 :Instant = read("2014-04-01 13:13")
  val t5 :Instant = read("2015-04-01 14:14")
  val i1 =  Interval(t0, t1)
  val i2 =  Interval(t1, t2)
  val i3 =  Interval(t1, t3)
  val i4 = Interval(t4, t5)

  "timeline t" should "build from Intervals" in {
    val tl1: TimeLine[String] = TimeLine(List())
    val tl2 = tl1.append(i1, "Een")
    val tl3 = tl2.append(i2,"Twee")
    val history = List(TimeLineElement(i2, "Twee"), TimeLineElement(i1, "Een"))
    println(tl3.show)
    tl3 shouldEqual(TimeLine(history))
  }

  "timeline t2" should "build from Intervals" in {
    val tl1: TimeLine[String] = TimeLine(List())
    val tl2 = tl1.append(i1, "Een")
    val tl3 = tl2.append(i2,"Een")
    val history = List(TimeLineElement(Interval(t0,t2), "Een"))
    println(tl3.show)
    val hlist: List[TimeLineElement[String]] = List(TimeLineElement(i1, "Een"))
    val tlexp = TimeLine(hlist)
    tl2 shouldEqual tlexp
    tl3 shouldEqual(TimeLine(history))
  }

}
