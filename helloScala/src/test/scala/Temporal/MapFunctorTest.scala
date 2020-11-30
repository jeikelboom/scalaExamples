package Temporal

import cats.Functor
import cats.instances.list._
import cats.instances.map._
import cats.Applicative


import org.scalatest.{FlatSpec, Matchers}

class MapFunctorTest  extends FlatSpec with Matchers {

  "list string " should "map to list of length" in {
    val aList: List[String] = List("Hello", "brave", "new", "world")
    val blist: List[Int] = Functor[List].map(aList)(_.length)
    blist shouldEqual(List(5, 5 ,3 ,5))
    val abAdd: (String, Int) => String = (a, b) =>  a + b
    val clist : List[String] = Applicative[List].ap2(Applicative[List].pure(abAdd))(aList, blist)
  }

  "unijoin" should "yield joined List" in {
    val aList: List[String] = List("bread", "milk", "meat", "jam")
    val priceList: List[Double] = List(1.52, 1.03, 3.54, 1.76)
    val nr : List[Int] = (1 to 4).toList
    val anr : List[(Int, String)]= nr zip aList
    val priceNr : List[(Int, Double)] = nr zip priceList
    val joined = for (
      (n1, art) <- anr;
      (n2, pr) <- priceNr;
      if (n1 == n2)
    ) yield (art, pr)
  }

  "a map" should "be functor" in {
    val aMap: Map[Int, String] = Map(1 -> "a", 2 -> "b")
    println(aMap)
    val bMap: Map[Int, String] = for (
      (i, s) <- aMap
    ) yield ((2 * i) -> (s"${s} - ${s}"))
    println(bMap)
  }


}
