package models

import models.domein.{Artikel, ArtikelGroep, FoutMelding}
import play.api.libs.json.Json

package object domein {

  object ArtikelGroep extends Enumeration {
    val Zeep = Value
    val Vleeswaren = Value
    val Frisdrank = Value
  }

  case class Artikel(val id: Long, val ean: String, val omschrijving: String, val ag: String, val pr: Int ) {
    val artikelGroep : ArtikelGroep.Value = ArtikelGroep.withName(ag)
    val prijs = Bedrag(pr)
  }

  object Artikel {
    implicit val artikelFormat = Json.format[Artikel]

    def apply(id: Long, ean: String, omschrijving: String, ag: ArtikelGroep.Value, pr: Bedrag): Artikel = {
      Artikel(id, ean, omschrijving, ag.toString, pr.bedragInCenten)
    }

    def apply3( id: Long, ean: String, omschrijving: String,  ag: String,  pr: Int ): Artikel = {
      Artikel(id, ean, omschrijving, ag, pr)
    }

  }

  case class Bedrag(val bedragInCenten: Int) {
    val centen = bedragInCenten % 100
    val euros = bedragInCenten /100

    def +(bedrag: Bedrag) = Bedrag(bedragInCenten + bedrag.bedragInCenten)
    def -(bedrag: Bedrag) = Bedrag(bedragInCenten = bedrag.bedragInCenten)
    def /(door: Int) = Bedrag(bedragInCenten / door)
    def *(maal: Int) = Bedrag(bedragInCenten * maal)
    def roundedDown() = bedragInCenten - bedragInCenten % 5

    override def toString: String ={
      val euros6w = f"${euros.abs}%6d"
      val centen2w= f"${centen.abs}%02d"
      val sign = if (bedragInCenten < 0) "-" else " "
      s" € ${euros6w},${centen2w}${sign}"
    }
  }
  object Bedrag {
    def apply(euros: Int, centen: Int): Bedrag = new Bedrag(100 * euros + centen)
  }

  case class FoutMelding(val errorCode: String)

  // Een gescanned artikel. We houden bij hoe vaak het gescanned is.
  case class Scan(val artikel: Artikel,val aantal: Int) {
    def verkoopPrijs() = artikel.prijs * aantal
  }

  abstract trait ArtikelRepository {
    def findByEan(ean: String): Either[FoutMelding, Artikel]
  }

  abstract trait ScansRepo {
    def storeScan (ean: String) :Either[FoutMelding, Scan]
    def regels() : List[Scan]
    def reset() : Unit
  }

  trait Kassa {
    val scansRepo : ScansRepo

    def scan(ean: String): Either[FoutMelding, Scan] = {
      scansRepo.storeScan(ean)
    }
    def totaalBedrag(): Bedrag = {
      scansRepo.regels().foldLeft(Bedrag(0))({ (geld, scan) => geld + scan.verkoopPrijs()})
    }
    def nieuweKlant() :Unit = {
      scansRepo.reset()
    }
  }


}
