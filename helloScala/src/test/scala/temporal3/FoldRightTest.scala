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
    pairs shouldEqual List((8,"bb"), (7,"bb"), (6,"bb"), (5,"aa"), (4,"aa"), (3,"aa"))
  }
  "foldRight" should "return dates" in {
    def op(ta:(Int, String), z: List[Int]): List[Int] = ta._1 :: z
    val dates: List[Int] = tl1.foldRight(List[Int]())(op)
    List(8, 7, 6, 5, 4, 3) shouldEqual dates
  }



}
