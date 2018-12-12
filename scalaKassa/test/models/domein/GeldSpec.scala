package models.domein

import org.scalatest.{FlatSpec, Matchers}

class GeldSpec extends FlatSpec with Matchers {

  "geld 23,05" should "print" in {
    val bedrag: Bedrag = Bedrag(2305)
    bedrag.centen shouldEqual(5)
    bedrag.euros shouldEqual(23)
    s"$bedrag" shouldEqual(" €     23,05 ")
  }

  "geld -23,05" should "print correctly" in {
    val bedrag = Bedrag(-12305)
    s"$bedrag" shouldEqual(" €    123,05-")
  }

  "bedragen" should "compensate" in {
    val bedrag1 = Bedrag(-12305)
    val bedrag2 = Bedrag(bedrag1.euros, bedrag1.centen)
    bedrag1.bedragInCenten shouldEqual bedrag2.bedragInCenten
    //bedrag1 shouldEqual(bedrag2)
   }
}
