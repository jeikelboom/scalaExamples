package workshops

import workshops.WS05Zoo._

// Let op dat we een Object als een soort module gebruiken
object WS05Zoo {

  /**
    * Een trait is een eenheid van code.
    * Deze kan als mixin worden toegevoegd aan een class of object.
    * Het kan als abstracte trait (vergelijk klassieke Java interfaces).
    * Maar vaak zijn ze kant en klaar te gebruiken.
    */
  trait Kwaker {
    def kwaak() = {println("  Kwaak, kwaak")}
  }

  class Voedsel()
  case class Brokken() extends Voedsel
  case class Brood() extends Voedsel

  trait Eter {
    type GeschiktVoedsel <: Voedsel
    def eat(food: GeschiktVoedsel) = {println (s" eet ${food}" )}
  }


  sealed class Animal(val naam: String)
  case class Kikker(override val naam: String) extends Animal(naam) with Kwaker
  case class Eend(override val naam: String) extends Animal(naam) with Kwaker  with Eter {
    type GeschiktVoedsel = Brood
  }
  case class Hond(override val naam: String) extends Animal(naam)  with Eter {
    type GeschiktVoedsel = Brokken
  }

}

object runner extends App {
  val kwek: Animal = Eend("kwek")

  def geluid(dier: Animal) =  {
    print(dier.naam)
    dier match {
      case Hond(_) => println("Hond")
      case eendje @ Eend(_) => eendje.kwaak()  // gebruik match als typecast
      case Kikker("Kermit") => println("Its not easy being green")
      case Kikker(_) => println("A frog")
    }
  }

  geluid(kwek)

  // Je kunt een trait zelfs instantieren.
  val kwaker: Kwaker = new Kwaker {}
  kwaker.kwaak()

  val kwak: Eend = Eend("kwek")
  print("kwak")
  kwak.eat(Brood())

  val wodan: Hond = Hond("Wodan")
  print("wodan")
  wodan.eat(Brokken())
}
