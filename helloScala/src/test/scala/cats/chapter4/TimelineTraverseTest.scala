package cats.chapter4

import cats.collections.Range
import org.scalatest.{FlatSpec, Matchers}
import temporal2.DiscreteTemporal._
import temporal2.TestData2._
import org.scalatest.{FlatSpec, Matchers}

class TimelineTraverseTest  extends FlatSpec with Matchers {

  "list of timelines traversed" should "timeline of list" in {
    val tlea1 = TimelineElement(d1, d2, "Hello")
    val tlea2 = TimelineElement(d2s, d3, "World")
    val tlea3 = TimelineElement(d3s, d7, "overall")
    val tla: DiscreteTimeline[String] = DiscreteTimeline(List(tlea3, tlea2, tlea1))
    val tleb1 = TimelineElement(d1, d3, "Good")
    val tleb2 = TimelineElement(d3s, d5, "Bye")
    val tleb3 = TimelineElement(d6, d9, "to All")
    val tlb: DiscreteTimeline[String] = DiscreteTimeline(List(tleb3, tleb2, tleb1))
    val aList: List[DiscreteTimeline[String]] = List(tla, tlb)

  }

}
