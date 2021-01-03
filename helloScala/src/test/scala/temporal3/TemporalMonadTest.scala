package temporal3


import cats.instances.all._
import cats.syntax.all._
import org.scalatest.{FlatSpec, Matchers}
import temporal3.IntegerTimeUnit.IntegerTimelines._




class TemporalMonadTest  extends FlatSpec with Matchers {

  val timeline1: Timeline[Int]= Timeline()
    .append(10,20, 1000)
    .append(21,30, 321)

  val timeline2: Timeline[Int]= Timeline()
    .append(10,13, 20)
    .append(14, 18, 45)
    .append(21,30, 321)

  "sequence" should "work" in {
    val lst = List(timeline1, timeline2)
    val timeline3 = lst.sequence
  }


}
