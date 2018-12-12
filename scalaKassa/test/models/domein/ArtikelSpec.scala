package models.domein

import org.scalatest.{FlatSpec, Matchers}

class ArtikelSpec extends FlatSpec with Matchers {

  "artikel" should "build from (String, String, String, Int)" in {
    val tuple = (1, "EAN01020", "Ham", "Vleeswaren", 195)
    val artikel = Artikel (1, "EAN01020", "Ham", "Vleeswaren", 195)
    artikel.artikelGroep shouldEqual(ArtikelGroep.Vleeswaren)
  }

  "matchen" should "naar " in {
    val artikel = Artikel(1, "EAN1","Ham", ArtikelGroep.Vleeswaren.toString, 195)
    val unapplied  = Artikel.unapply(artikel)
    unapplied.get shouldEqual((1, "EAN1", "Ham", "Vleeswaren",195))
  }

  "matchen" should "naar (String, String, String, Int)" in {
    val artikel = Artikel(1, "EAN1","Ham", ArtikelGroep.Vleeswaren.toString, 195)
    val ok = artikel match {
      case Artikel(1, "EAN1", "Ham", "Vleeswaren",195) => true
      case _ => false
    }
    ok shouldEqual(true)
  }

}
