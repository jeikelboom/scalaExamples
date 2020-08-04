import workshops.WS04ClassesObjectsTraits.{PlayingCard, Rank, Suit}
import workshops.WS04ClassesObjectsTraits.Suit._
import workshops.WS04ClassesObjectsTraits.Rank._

def analyseCard (card: PlayingCard) : String =
  card match {
    case PlayingCard(Spades, Queen)   => "card1"
    case PlayingCard(Clubs, K5)       => "card2"
    case PlayingCard(Hearts, _)       => "Hearts"
    case PlayingCard(suit, K6)        => s"${suit} 6"
    case _ => "some other"
  }


val card1 = PlayingCard(Spades, Queen)

val card2 = PlayingCard(Clubs, K5)


val s1 = analyseCard(card1)
val s2 = analyseCard(card2)
val s3 = analyseCard(PlayingCard(Hearts, K2))
val s4 = analyseCard(PlayingCard(Spades, K6))
val s5 = analyseCard(PlayingCard(Spades, Ace))

