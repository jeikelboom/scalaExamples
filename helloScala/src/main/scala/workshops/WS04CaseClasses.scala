package workshops

import workshops.WS04ClassesObjectsTraits.Rank.{K5, King}
import workshops.WS04ClassesObjectsTraits.Suit.{Diamond, Hearts}
import workshops.WS04ClassesObjectsTraits.{PlayingCard, Rank, Suit}


object WS04ClassesObjectsTraits {

  /**
    * Case classes zijn handige kleine datastructuren
    */

  object Rank extends Enumeration {
    val Ace = Value
    val K2 = Value
    val K3 = Value
    val K4 = Value
    val K5 = Value
    val K6 = Value
    val K7 = Value
    val K8 = Value
    val K9 = Value
    val K10 = Value
    val Jack = Value
    val Queen = Value
    val King = Value
  }

  object Suit extends Enumeration {
    val Hearts = Value
    val Diamond = Value
    val Spades = Value
    val Clubs = Value
  }


  sealed trait Cards
  // case classes Cards is a Sum type
  // PlayingCard is a product type
  //
  case class PlayingCard(val kleur: Suit.Value, val waarde: Rank.Value) extends Cards
  case class Joker() extends Cards
  import WS04ClassesObjectsTraits.Suit._
  import WS04ClassesObjectsTraits.Rank._

  object PlayingCard {
    val randomCard = PlayingCard(Spades, K4)
    val c = PlayingCard(Hearts, K5)
  }


  val ruitenHeer = PlayingCard(Diamond, King)
  val nogEen = ruitenHeer.copy(kleur = Spades)
  val kl: Int= Suit.Spades.id



}
object cardeckPlayer1 {
  val cards = List(PlayingCard(Diamond, King), PlayingCard(Hearts, K5))
}
object cardeckPlayer2 {
  val cards = List(PlayingCard(Diamond, K5), PlayingCard(Hearts, King))
}

object runner22 extends App {
  val xxxx: Suit.Value = Suit.apply(2)
  println (xxxx)
  val eenKaart = (Suit.Spades, Rank.Ace)
  val mytuple: (Int, String) = (1, "hallo")
  val (x,y) = mytuple
  println(x)
  println(y)
  println(mytuple._1)
  val een: String = mytuple._2
}
