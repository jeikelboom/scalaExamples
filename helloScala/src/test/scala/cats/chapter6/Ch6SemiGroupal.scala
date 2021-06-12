package cats.chapter6

import org.scalatest.{FlatSpec, Matchers}
import cats.syntax.apply._
import cats.instances.option._

class Ch6SemiGroupal  extends FlatSpec with Matchers {

  case class Cat(name: String, yearBorn: Int, color: String)
  val tupleOfOptions: (Option[String], Option[Int], Option[String])= (Option("garfield"), Option(2012), Option("black"))

  "tupled" should "yield an Option" in {
    val tuple :Option[(String, Int, String)] = tupleOfOptions.tupled
    tuple shouldEqual Option("garfield", 2012, "black")
  }

  "mapN" should "map it to a Cat" in {
    val garfield: Option[Cat] = tupleOfOptions.mapN(Cat.apply)
    garfield shouldEqual Some(Cat("garfield",2012, "black"))
  }



}
