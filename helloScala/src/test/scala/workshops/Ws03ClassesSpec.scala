package workshops

import WS03Classes._
import WS03Classes.Breuk.ggd

import org.scalatest.{FlatSpec, Matchers}

class Ws03ClassesSpec extends FlatSpec with Matchers {

  "ggd 24,18" should "equal 6" in {
    ggd(24,18) shouldEqual  6
  }

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
    val breuk = Breuk(5)
    breuk.noemer shouldEqual(1)
    breuk.teller shouldEqual(5)
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
}
