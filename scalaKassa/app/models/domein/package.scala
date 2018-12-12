package models

import models.domein.{Artikel, ArtikelGroep, ErrorMessage}
import play.api.libs.json.Json

package object domein {

  object ArtikelGroep extends Enumeration {
    val Zeep = Value
    val Vleeswaren = Value
    val Frisdrank = Value
  }

  case class Artikel(val id: Long, val ean: String, val omschrijving: String, val ag: String, val pr: Int ) {
    val artikelGroep : ArtikelGroep.Value = ArtikelGroep.withName(ag)
    val prijs = Geld(pr)
  }

  object Artikel {
    implicit val artikelFormat = Json.format[Artikel]

    def artikel(id: Long, ean: String, omschrijving: String, ag: ArtikelGroep.Value, pr: Geld) = {
      Artikel(id, ean, omschrijving, ag.toString, pr.bedragInCenten)
    }

  }

  case class Geld(val bedragInCenten: Int) {
    val centen = bedragInCenten % 100
    val euros = bedragInCenten /100

    def +(bedrag: Geld) = Geld(bedragInCenten + bedrag.bedragInCenten)
    def -(bedrag: Geld) = Geld(bedragInCenten = bedrag.bedragInCenten)
    def /(door: Int) = Geld(bedragInCenten / door)
    def *(maal: Int) = Geld(bedragInCenten * maal)
    def roundedDown() = bedragInCenten - bedragInCenten % 5

    override def toString: String ={
      val euros6w = f"${euros.abs}%6d"
      val centen2w= f"${centen.abs}%02d"
      val sign = if (bedragInCenten < 0) "-" else " "
      s" € ${euros6w},${centen2w}${sign}"
    }
  }
  object Geld {
    def apply(euros: Int, centen: Int): Geld = new Geld(100 * euros + centen)
  }

  case class ErrorMessage (val errorCode: String)

  // Een gescanned artikel. We houden bij hoe vaak het gescanned is.
  case class Scan( val ean: String, val aantal: Int, val artikel: Artikel)

  abstract trait ArtikelenRepo {
    def findByEan(ean: String): Either[ErrorMessage, Artikel]
  }

  abstract trait ScansRepo {
    def storeScan (ean: String) :Either[ErrorMessage, Scan]
    def regels() : List[Scan]
  }

  trait Kassa {
    val scansRepo : ScansRepo

    def scan(ean: String): Either[ErrorMessage, Scan] = {
      scansRepo.storeScan(ean)
    }
    def totaalBedrag(): Geld = {
      scansRepo.regels().foldLeft(Geld(0))({(geld, scan) => geld + scan.artikel.prijs * scan.aantal})
    }
  }


}
