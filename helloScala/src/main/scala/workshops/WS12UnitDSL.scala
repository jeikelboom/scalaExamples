package workshops

import workshops.WS12UnitDSL.Meter

object WS12UnitDSL {

  case class Meter(val meter: Double) {
    override def toString: String = s"${meter} meter"

    def +(m: Meter) = Meter(meter + m.meter)
  }

  object Meter {
    implicit def doubleToMeter(x: Double) = Meter(x)

    implicit def doubleToMeter(x: CentiMeter) = Meter(x.centimeter * 0.01)

    implicit def doubleToMeter(x: Inch) = Meter(x.inch * 0.0254)
  }

  case class CentiMeter(val centimeter: Double) {
    override def toString: String = s"${centimeter} centimeter"

    def +(m: CentiMeter) = CentiMeter(centimeter + m.centimeter)
  }

  object CentiMeter {
    implicit def doubleToCentimeter(x: Double) = CentiMeter(x)
  }

  case class Inch(val inch: Double) {
    override def toString: String = s"${inch} inch"

    def +(o: Inch) = Inch(inch + o.inch)
  }

  object Inch {
    implicit def doubleToInch(x: Double) = Inch(x)
  }

}

trait MyDsl{
  implicit def intToSZ(m: Double) = new SizeUnit1(m)

  class SizeUnit1(m: Double)  {
    def meter(cm: Double) = new SizeUnit2 (m + 0.01 * cm)
  }

  class SizeUnit2(m: Double)  {
    def centiMeter =  Meter(m)
  }
}

