package temporal2

import cats.collections.Range
import org.scalatest.{FlatSpec, Matchers}
import temporal2.DiscreteTemporal._
import temporal2.TestData2._

class DiscreteTimelineTest extends FlatSpec with Matchers{

  "simple timeline " should "multiply" in {
    val tlea1 = TimelineElement(d1, d2, "Hello")
    val tlea2 = TimelineElement(d2s, d3, "World")
    val tlea3 = TimelineElement(d3s, d4, "overall")
    val tla = DiscreteTimeline(List(tlea3, tlea2, tlea1))
    val tlf = timeLineApplicative.pure((s: String) => s.length)
    val combi: DiscreteTimeline[Int] = timeLineApplicative.ap(tlf)(tla)
    val expected = DiscreteTimeline(List()).append(TimelineElement(Range(d1,d3), 5)).append(TimelineElement(Range(d3s,d4), 7))
    val mapped = timeLineApplicative.map(tla)(_.length)
    combi shouldEqual expected
    mapped shouldEqual expected
  }


}
