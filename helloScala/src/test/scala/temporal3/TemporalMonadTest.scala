package temporal3


import cats.instances.all._
import cats.syntax.all._
import org.scalatest.{FlatSpec, Matchers}
import temporal3.IntegerTimeUnit.IntegerTimelines._

import scala.collection.generic.CanBuildFrom
import scala.collection.parallel.{Combiner, ParIterable}
import scala.collection.{GenIterable, GenMap, GenSeq, GenSet, GenTraversable, GenTraversableOnce, immutable, mutable}
import scala.reflect.ClassTag




class TemporalMonadTest  extends FlatSpec with Matchers {

  val timeline1: Timeline[Int]= Timeline()
    .append(9,15,  10)
    .append(16, 17, 20)
    .append(18,35, 30)
    .append(36,39,40)
    .append(40, 49, 50)

  val timeline2: Timeline[Int]= Timeline()
    .append(9,13,  100)
    .append(14, 18, 200)
    .append(19,35, 300)
    .append(36,39,400)
    .append(40, 49, 500)

  val timeline3: Timeline[Int]= Timeline()
    .append(10,19, 1000)
    .append(20,29, 2000)
    .append(30, 39, 3000)
    .append(40,49,4000)

  val expected : Timeline[Int] = Timeline()
    .append(10, 13, 1110)
    .append(14,15, 1210)
    .append(16,17, 1220)
    .append(18,18, 1230)
    .append(19,19,1330)
    .append(20,29,2330)
    .append(30,35,3330)
    .append(36,39, 3440)
    .append(40,49,4550)

  "map" should "equal" in {
    reset()
    val pured: Timeline[(Int, Int, Int) => Int] = timeLineApplicative.pure((i,j,k) => i + j + k)
    val actual: Timeline[Int] = timeLineApplicative.ap3(pured)(timeline1, timeline2, timeline3)
    actual shouldEqual  expected
    println("mapped")
    reset()
  }

  "flatMap" should "equal for" in {
    reset()
    val result1 = timeline1.flatMap(x1 => timeline2.flatMap(x2 => timeline3.map(x3 =>x3 + x2 + x1)))
    val result2 = for (
      f1 <- timeline1;
      f2 <- timeline2;
      f3 <- timeline3
    ) yield f1 + f2 + f3
    result1 shouldEqual( result2)
    expected shouldEqual  result1
    reset()
  }


}
