package workshops

object WS12UnitDSL {

  case class Meter(val meter: Int) {
    override def toString: String = s"${meter} meter"
    def +(m: Meter) = Meter(meter + m.meter)
    def +(o: CentiMeter) = CentiMeter(100 * meter + o.centimeter)
  }

  case class CentiMeter(val centimeter: Int)  {
    override def toString: String = s"${centimeter} centimeter"
    def +(m: CentiMeter) = CentiMeter(centimeter + m.centimeter)
    def +(o: Meter) = CentiMeter(100 * o.meter + centimeter)
  }

  class Inch(val inch: Int)  {
    override def toString: String = s"${inch} inch"
  }

  object Inch{
    def apply(inch: Int): Inch = new Inch(inch)
  }

}
