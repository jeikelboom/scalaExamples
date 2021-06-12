package temporal3

import IntegerTimeUnit.IntegerTimelines._
import cats.instances.all._
import cats.syntax.all._


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

  val timeline1: Timeline[Int]= Timeline()
    .append(Int.MinValue,9,0)
    .append(10,20, 1000)
    .append(21,30, 321)
    .append(31, Int.MaxValue, 0)

  val timeline2: Timeline[Int]= Timeline()
    .append(Int.MinValue,9,0)
    .append(10,13, 20)
    .append(14, 18, 45)
    .append(19,20, 0)
    .append(21,30, 321)
    .append(31, Int.MaxValue, 0)


  val timeline3: Timeline[List[Int]] = Timeline()
    .append(Int.MinValue,9, List(0, 0))
    .append(10,13, List(1000, 20))
    .append(14, 18, List(1000, 45))
    .append(19,20, List(1000, 0))
    .append(21, 30, List(321, 321))
    .append(31, Int.MaxValue, List(0, 0))

  "sequence list of timelines" should "transform to timeline of lists" in {
    val lst = List(timeline1, timeline2)
    val combined = lst.sequence
    combined shouldEqual  timeline3
    println(timeline3)
  }

  "get" should "return" in {
    val g = timeline2.get(16)
    g shouldEqual Some(45)
  }


}
