package temporal2

import cats.Applicative
import org.scalatest.{FlatSpec, Matchers}
import temporal2.Temporal2._


class Temporal2Test  extends FlatSpec with Matchers {

//  val t1 = timestamp("2011-04-01 00:00")
//  val t2 = timestamp("2012-04-03 00:00")
//  val t3 = timestamp("2013-04-05 00:00")
//  val t4 = timestamp("2014-04-07 00:00")
//  //println(timestamp2String(t1))
//
//
//  "01 " should "before 03 (month)" in {
//    (t1 < t2) shouldEqual true
//    (t1 < t1) shouldEqual false
//    (t1 > t2) shouldEqual false
//    (t1 <= t2) shouldEqual true
//  }
//
//  "1-5" should "overlap 3-7" in {
//    val a: Interval  = Interval(t1, t3)
//    val b: Interval  = Interval(t2, t4)
//    val inter: Interval = (a intersection  b).get
//    a overlaps b shouldEqual  true
//    a intersection b shouldEqual Option(Interval(t2, t3))
//    a join b shouldEqual Option(Interval(t1, t4))
//  }
//
//  "timeline" should "build from events" in {
//
//    val tl: Timeline[String] = Timeline(CurrentRecord(t1, "Almere")) +
//      CurrentRecord(t2, "Utrecht") +
//      CurrentRecord(t3, "Utrecht") +
//      CurrentRecord(t4, "Amersfoort")
//    println(tl)
//  }
//
//  "timelines with only Current" should "ap" in {
//    val tl1: Timeline[String] = Timeline(CurrentRecord(t1, "Arnhem"))
//    val tl2: Timeline[String] = Timeline(CurrentRecord(t2, "Programmer"))
//    def worked(city: String, profession: String) = s"worked in ${city} as ${profession}"
//    val tl0: Timeline[(String, String) => String] = Applicative[Timeline].pure(worked)
//    val tl3: Timeline[String] = Applicative[Timeline].ap2(tl0)(tl1, tl2)
//    tl3 shouldEqual(Timeline(CurrentRecord(t2, "worked in Arnhem as Programmer")))
//  }
//
//  "History records" should "split in None History rest" in {
//    val record1: HistoryRecord[(String) => Int] = HistoryRecord(Interval(t1, t3), _.length)
//    val record2: HistoryRecord[String] = HistoryRecord(Interval(t2,t4), "Hello world")
//    val combined = record2.combine(record1)
//    val restOther = record2.restOther(record1)
//    val restThis = record2.restThis(record1)
//    combined shouldEqual Some(CurrentRecord(t2, 11))
//    restOther.map(_.asInstanceOf[HistoryRecord[_]].interval) shouldEqual Some(Interval(t2,t3))
//    restThis shouldEqual Some(HistoryRecord(Interval(t2,t4), "Hello world"))
//  }

}
