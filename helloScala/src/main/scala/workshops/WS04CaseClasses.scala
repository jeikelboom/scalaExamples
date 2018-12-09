package workshops


object WS04ClassesObjectsTraits {

  /**
    * Case classes zijn handige kleine datastructuren
    */

  object Kleur extends Enumeration {
    val Harten = Value
    val Ruiten = Value
    val Schoppen = Value
    val Klaver = Value
  }

  object Waarde extends Enumeration {
    val K2 = Value(2)
    val K3 = Value(3)
    val K4 = Value(4)
    val K5 = Value(5)
    val K6 = Value(6)
    val K7 = Value(7)
    val K8 = Value(8)
    val K9 = Value(9)
    val K10 = Value(10)
    val Boer = Value(10)
    val Vrouw = Value(10)
    val Heer = Value(10)
    val Aas = Value(11)
  }

  // case classes eenvoudige datastructuren
  case class Speelkaart(val kleur: Kleur.Value, val waarde: Waarde.Value)

  import WS04ClassesObjectsTraits.Kleur._
  import WS04ClassesObjectsTraits.Waarde._

  val ruitenHeer = Speelkaart(Ruiten, Heer)


}