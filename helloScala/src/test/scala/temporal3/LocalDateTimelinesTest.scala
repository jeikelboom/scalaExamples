package temporal3

import org.scalatest.{FlatSpec, Matchers}
import temporal2.TestData2.{d1, d2, d2p, d2s, d3, d3s, d4s, d5}
import LocalDateTimelines.LocalDateTimed._
import LocalDateTimelines.date

class LocalDateTimelinesTest  extends FlatSpec with Matchers {
  val tlea1 = IntervalData(d1, d2, "Hello")
  val tlea2 = IntervalData(d2s, d3, "World")
  val tlea3 = IntervalData(d3s, d5, "overall")
  val tla: Timeline[String] = Timeline(List(tlea3, tlea2, tlea1))

  "simple timeline " should "multiply" in {
    val tlf = timeLineApplicative.pure((s: String) => s.length)
    val combi: Timeline[Int] = timeLineApplicative.ap(tlf)(tla)
    val expected = Timeline(List()).append(IntervalData(d1, d3, 5)).append(IntervalData(d3s, d5, 7))
    val mapped: Timeline[Int] = timeLineApplicative.map(tla)(_.length)
    combi shouldEqual expected
    mapped shouldEqual expected
    val mapped2: Timeline[Int]  = tla.map(_.length)
    mapped2 shouldEqual expected
    val mapped3 = for (
      a <- tla
    ) yield a.length
    mapped3 shouldEqual expected
  }

  "tla" should "retroUpdate" in {
    val update: IntervalData[String] = IntervalData(d2p,d4s, "Overwritten")
    val updated: Timeline[String] = tla.retroUpdate(update)
    val exp1 = IntervalData(date(2004,4,6), date(2005,5,5), "overall")
    val exp2 = IntervalData(date(2002,2,1), date(2004,4,5), "Overwritten")
    val exp3 = IntervalData(date(2001,1,1), date(2002,1,31), "Hello")
    val exp: Timeline[String] = Timeline().append(exp3).append(exp2).append(exp1)
    updated shouldEqual exp
  }

}
