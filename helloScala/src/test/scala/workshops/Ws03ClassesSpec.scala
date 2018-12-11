package workshops

import WS03Classes._
import WS03Classes.Breuk.ggd

import org.scalatest.{FlatSpec, Matchers}

class Ws03ClassesSpec extends FlatSpec with Matchers {

  "Breuk 4/3 teller" should "have teller 4 and noemer 3" in {
    val breuk = Breuk(4, 3)
    breuk.teller shouldEqual 4
    breuk.noemer shouldEqual 3
  }
  "Breuk 1/2" should "not equal 1/3" in {
    Breuk(1, 2)  should !== (Breuk(1, 3))
  }

  "Breuk 5/2" should "not equal 7/2" in {
    Breuk(5, 2) should !==  (Breuk(7, 2))
  }

  "Breuk 5" should "equals 5/1" in {
    val vijf: Int = 5
    val breuk: Breuk = vijf
    // dit is val breuk = Breuk.apply(vijf) of Breuk(5)
    breuk.noemer shouldEqual(1)
    breuk.teller shouldEqual(5)
  }

  "Breuk 5" should "work via auxiliary constructor" in {
    val breuk = new Breuk(5)
    val breuk5: Breuk = 5
    breuk shouldEqual( breuk5)
  }

  "Breuk 24/18" should "equal 4/3" in {
    Breuk(24, 18) shouldEqual Breuk(4, 3)
    Breuk(24, 18).teller shouldEqual 4
    Breuk(24, 18).noemer shouldEqual 3
  }

  "Breuk -24 /18"should "equal -4/3" in {
    val breuk = Breuk(-24, 18)
    breuk shouldEqual Breuk(-4, 3)
    breuk.teller shouldEqual -4
    breuk.noemer shouldEqual 3
  }

  "Breuk 24 / -18"should "equal -4/3" in {
    val breuk = Breuk(24, -18)
    breuk shouldEqual Breuk(-4, 3)
    breuk.teller shouldEqual -4
    breuk.noemer shouldEqual 3
  }

  "Breuk -24 / -18"should "equal 24/18" in {
    val breuk = Breuk(-24, -18)
    breuk shouldEqual Breuk(24,18)
  }

  "Breuk" should "implicit vanuit integer geconverteerd " in {
    val vijf: Int = 5
    val breuk: Breuk = vijf
    breuk.teller shouldEqual(5)
    breuk.noemer shouldEqual(1)
  }

  "2/5 + 1/3" should "equal 11/15" in {
    Breuk(2,5) + Breuk(1,3) shouldEqual(Breuk(11, 15))
  }
  "1 + 1/3" should "equal 4/3" in {
    1 + Breuk(1,3) shouldEqual(Breuk(4, 3))
  }

  "apply string" should "return string" in {
    val breukje = Breuk(17, 42)
    val string = breukje.apply("antwoord:")
    string shouldEqual "antwoord: 17/42"
  }
}
