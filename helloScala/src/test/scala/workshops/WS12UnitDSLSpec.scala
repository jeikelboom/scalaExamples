package workshops

import org.scalatest.{FlatSpec, Matchers}
import WS12UnitDSL.{CentiMeter, Meter}
import workshops.WS10aExprFunc.{Constant, Plus, eval}

class WS12UnitDSLSpec extends FlatSpec with Matchers {

  val zesmeter =  Meter(6)
  val drieMeter = Meter(3)

  "drie plus zesmeter " should "be 9 Meter" in {
    val result = zesmeter + drieMeter
    result shouldEqual(Meter(9))
  }

  "twee meter plus 3 centimeter" should "be 203 centimeter" in {
    val result = Meter(2) + CentiMeter(3)
    result shouldEqual(CentiMeter(203))
  }


}
