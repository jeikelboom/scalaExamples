package workshops

import org.scalatest.{FlatSpec, Matchers}
import WS12UnitDSL.{CentiMeter, Meter}

class WS12UnitDSLSpec extends FlatSpec with Matchers with MyDsl{

  val v =  3.0 meter 23.0 centiMeter


  "drie plus zesmeter " should "be 9 Meter" in {
    val result = Meter(6) + Meter(3)
    result shouldEqual(Meter(9))
  }

  "twee meter plus 3 centimeter" should "be 203 centimeter" in {
    val result = Meter(2) + 3.0
    result shouldEqual(Meter(5))
  }

  "twee meter Plus 23 centimeter"  should "be Meter(1.23)" in {
    val result = Meter(2) + CentiMeter(23.0)
    result shouldEqual(Meter(2.23))
  }

  "len 2.0" should "Meter(2.0)" in {
    v shouldEqual(Meter(3.23))
  }

}
