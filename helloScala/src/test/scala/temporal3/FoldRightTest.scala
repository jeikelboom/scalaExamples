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

  "get" should "return" in {
    val g = timeline2.get(16)
    g shouldEqual Some(45)
  }


}
