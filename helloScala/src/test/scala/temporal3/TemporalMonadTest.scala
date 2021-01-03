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

  "get" should "return" in {
    val g = timeline2.get(16)
    g shouldEqual Some(45)
  }


  "flatmap" should "work with for" in {
    def pure(i: Int): Timeline[Int => Int] = timeLineApplicative.pure(j => i + j)
    def monadFun2(i: Int):Timeline[Int] = timeLineApplicative.ap(pure(i))(timeline2)
    println (monadFun2(300))
    val flatmapped  = timeline1.flatmap(i => monadFun2(i))
    println(s"fm = $flatmapped")
//    for {
//      a <- timeline1
//      b <- monadFun2(a)
//    } yield "a"
  }


}
