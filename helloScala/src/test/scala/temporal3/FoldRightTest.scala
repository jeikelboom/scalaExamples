package temporal3

import IntegerTimeUnit.IntegerTimelines._
import IntegerTimeUnit.IntegerTimelines.timeLineApplicative

import org.scalatest.{FlatSpec, Matchers}

class FoldRightTest  extends FlatSpec with Matchers {
  val tl1: Timeline[String] = Timeline()
    .append(3,5, "aa")
    .append(6,8, "bb")

  "foldRight" should "return values" in {
    val pairs: List[(Int, String)] = tl1.foldRight(List[(Int, String)]())((ta, z) => ta :: z)
    pairs shouldEqual List((3, "aa"), (4, "aa"), (5, "aa"), (6, "bb"), (7, "bb"), (8, "bb"))
  }
  "foldRight" should "return dates" in {
    val dates: List[Int] = tl1.foldRight(List[Int]())((ta, z) => ta._1 :: z)
    List(3, 4, 5, 6, 7, 8) shouldEqual dates
  }



}
