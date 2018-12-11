package workshops

import workshops.WS04ClassesObjectsTraits.{Kleur, Waarde}


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
    val K2 = Value
    val K3 = Value
    val K4 = Value
    val K5 = Value
    val K6 = Value
    val K7 = Value
    val K8 = Value
    val K9 = Value
    val K10 = Value
    val Boer = Value
    val Vrouw = Value
    val Heer = Value
    val Aas = Value
  }

  // case classes eenvoudige datastructuren
  case class Speelkaart(val kleur: Kleur.Value, val waarde: Waarde.Value)

  import WS04ClassesObjectsTraits.Kleur._
  import WS04ClassesObjectsTraits.Waarde._

  val ruitenHeer = Speelkaart(Ruiten, Heer)
  val nogEen = ruitenHeer.copy(kleur = Schoppen)
  val kl: Int= Kleur.Schoppen.id


}
object runner22 extends App {
  val xxxx: Kleur.Value = Kleur.apply(2)
  println (xxxx)
  val eenKaart = (Kleur.Schoppen, Waarde.Aas)
  val mytuple: (Int, String) = (1, "hallo")
  val (x,y) = mytuple
  println(x)
  println(y)
  println(mytuple._1)
  val een: String = mytuple._2
}