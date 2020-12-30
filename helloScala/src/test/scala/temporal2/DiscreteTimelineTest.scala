package temporal2

import cats.collections.Range
import org.scalatest.{FlatSpec, Matchers}
import temporal2.DiscreteTemporal._
import temporal2.TestData2._

class DiscreteTimelineTest extends FlatSpec with Matchers{

  val tlea1 = TimelineElement(d1, d2, "Hello")
  val tlea2 = TimelineElement(d2s, d3, "World")
  val tlea3 = TimelineElement(d3s, d5, "overall")
  val tla: DiscreteTimeline[String] = DiscreteTimeline(List(tlea3, tlea2, tlea1))

  "simple timeline " should "multiply" in {
    val tlf = timeLineApplicative.pure((s: String) => s.length)
    val combi: DiscreteTimeline[Int] = timeLineApplicative.ap(tlf)(tla)
    val expected = DiscreteTimeline(List()).append(TimelineElement(Range(d1,d3), 5)).append(TimelineElement(Range(d3s,d5), 7))
    val mapped: DiscreteTimeline[Int] = timeLineApplicative.map(tla)(_.length)
    combi shouldEqual expected
    mapped shouldEqual expected
    val mapped2: DiscreteTimeline[Int]  = tla.map(_.length)
    mapped2 shouldEqual expected
    val mapped3 = for (
      a <- tla
    ) yield a.length
    mapped3 shouldEqual expected
  }

  "tla" should "retroUpdate" in {
    val update: TimelineElement[String] = TimelineElement(d2p,d4s, "Overwritten")
    val updated: DiscreteTimeline[String] = retroUpdate(tla, update)
    val exp1 = TimelineElement(date(2004,4,6), date(2005,5,5), "overall")
    val exp2 = TimelineElement(date(2002,2,1), date(2004,4,5), "Overwritten")
    val exp3 = TimelineElement(date(2001,1,1), date(2002,1,31), "Hello")
    val exp: DiscreteTimeline[String] = DiscreteTimeline().append(exp3).append(exp2).append(exp1)
    updated shouldEqual exp
  }


}
