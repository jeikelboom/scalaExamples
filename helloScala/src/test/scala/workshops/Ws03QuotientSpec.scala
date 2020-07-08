package workshops

import WS03Quotient._
import WS03Quotient.Q.ggd

import org.scalatest.{FlatSpec, Matchers}

class Ws03QuotientSpec extends FlatSpec with Matchers with DslQ {

  "Breuk 4/3 teller" should "have teller 4 and noemer 3" in {
    val breuk = 4 %/ 3
    breuk.teller shouldEqual 4
    breuk.noemer shouldEqual 3
  }
  "Breuk 1/2" should "not equal 1/3" in {
    Q(1, 2)  should !== (Q(1, 3))
  }

  "Breuk 5/2" should "not equal 7/2" in {
    Q(5, 2) should !==  (Q(7, 2))
  }

  "Breuk 5" should "equals 5/1" in {
    val vijf: Int = 5
    val breuk: Q = vijf
    // dit is val breuk = Breuk.apply(vijf) of Breuk(5)
    breuk.noemer shouldEqual(1)
    breuk.teller shouldEqual(5)
  }

  "Breuk 5" should "work via auxiliary constructor" in {
    val breuk = new Q(5)
    val breuk5: Q = 5
    breuk shouldEqual( breuk5)
  }

  "Breuk 24/18" should "equal 4/3" in {
    Q(24, 18) shouldEqual Q(4, 3)
    Q(24, 18).teller shouldEqual 4
    Q(24, 18).noemer shouldEqual 3
  }

  "Breuk -24 /18"should "equal -4/3" in {
    val breuk = Q(-24, 18)
    breuk shouldEqual Q(-4, 3)
    breuk.teller shouldEqual -4
    breuk.noemer shouldEqual 3
  }

  "Breuk 24 / -18"should "equal -4/3" in {
    val breuk = Q(24, -18)
    breuk shouldEqual Q(-4, 3)
    breuk.teller shouldEqual -4
    breuk.noemer shouldEqual 3
  }

  "Breuk -24 / -18"should "equal 24/18" in {
    val breuk = Q(-24, -18)
    breuk shouldEqual Q(24,18)
  }

  "Breuk" should "implicit vanuit integer geconverteerd " in {
    val vijf: Int = 5
    val breuk: Q = vijf
    breuk.teller shouldEqual(5)
    breuk.noemer shouldEqual(1)
  }

  "2/5 + 1/3" should "equal 11/15" in {
    2 %/ 5 + 1 %/ 3 shouldEqual(Q(11, 15))
  }

  "1 + 1/3" should "equal 4/3" in {
    1 + Q(1, 3) shouldEqual(Q(4, 3))
  }

  "dsl 3 + Breuk(3/4)" should "Breuk(15/4)" in {
    val br = 3 + Q(3, 4)
    Q(15,4) shouldEqual(br)
  }

  "dsl Breuk(3/4) + 3 "should "Breuk(15/4)" in {
    val br = Q(3, 4) + 3
    br shouldEqual (Q(15 , 4))
  }

  "Conversion to double" should "work" in {
    val v : Double = 1.0 + Q(7,4).asDouble()
    val d: Double = Q(7,4).asDouble() + 1.0
    v  shouldEqual(d)
  }

  "pattern matching" should "return (t,n)" in {
    val b = Q(3, 4)
    val antwoord = b match  {
      case Q(t, n) => s"${t}/${n}"
      case  _ =>  "Not recognized"
    }
    antwoord shouldEqual("3/4")
  }

}
