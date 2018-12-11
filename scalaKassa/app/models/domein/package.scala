package models

import play.api.libs.json.Json

package object domein {

  object ArtikelGroep extends Enumeration {
    val Zeep = Value
    val Vleeswaren = Value
    val Frisdrank = Value
  }

  case class Artikel(val id: Long, val ean: String, val omschrijving: String, val ag: String, pr: Int ) {
    val artikelGroep : ArtikelGroep.Value = ArtikelGroep.withName(ag)
    val prijs = Geld(pr)
  }

  object Artikel {
    implicit val artikelFormat = Json.format[Artikel]
  }


  class Kassa {
    val scans : List[String] = List()
  }


}
