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

  "1,34 + 2,65" should "equal 3,99" in {
    (Bedrag(134) + Bedrag(2, 65)) shouldEqual(Bedrag(399))
  }
  "1,34 - 2,65" should "equal -1,31" in {
    (Bedrag(134) - Bedrag(2, 65)) shouldEqual(Bedrag(-131))
  }

  "1,34 x 2" should "eq 2,68" in {
    (Bedrag(134) * 2) shouldEqual(Bedrag(2, 68))
  }
  "1,34 / 3" should "eq 0,44" in {
    (Bedrag(134) / 3) shouldEqual(Bedrag(0, 44))
  }
  "1,34 rounded" should "eq 1,30" in {
    (Bedrag(134).roundedDown()) shouldEqual(Bedrag(1, 30))
  }
}
