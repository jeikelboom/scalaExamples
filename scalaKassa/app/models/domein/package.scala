package models

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


  }

  case class Bedrag(val bedragInCenten: Int) {
    val centen: Int = bedragInCenten % 100
    val euros = bedragInCenten /100

    def +(bedrag: Bedrag) = Bedrag(bedragInCenten + bedrag.bedragInCenten)
    def -(bedrag: Bedrag) = Bedrag(bedragInCenten - bedrag.bedragInCenten)
    def /(door: Int) = Bedrag(bedragInCenten / door)
    def *(maal: Int) = Bedrag(bedragInCenten * maal)
    def roundedDown() = Bedrag(bedragInCenten - bedragInCenten % 5)

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

  trait ArtikelRepository {
    def findByEan(ean: String): Either[FoutMelding, Artikel]
  }

  trait ScansRepo {
    def storeScan (scan : Scan) : Unit
    def findScan(ean: String): Option[Scan]
    def regels() : List[Scan]
    def reset() : Unit
  }

  trait Kassa {
    val scansRepo: ScansRepo
    val artRepo: ArtikelRepository

    /**
      * Als kassier wil ik een artikel kunnen scannen en dat het dan bewaard wordt.
      * @param ean
      * @return
      */
    def scan(ean: String): Either[FoutMelding, Scan] = {
      scansRepo.findScan(ean) match {
        case Some(Scan(art, aantal)) => {
          val newScan = Scan(art, aantal + 1)
          scansRepo.storeScan(newScan)
          Right(newScan)
        }
        case None => {
          //val result: Either[ErrorMessage, Artikel] =
          artRepo.findByEan(ean) match {
            case Left(msg) => Left(msg)
            case Right(artikel) => {
              val scan = Scan(artikel, 1)
              scansRepo.storeScan(scan)
              Right(scan)
            }
          }
        }
      }
    }

    /**
      * Als kassier wil ik het totaal bedrag kunnen afrekenene
      * @return
      */
    def totaalBedrag(): Bedrag = {
      scansRepo.regels().foldLeft(Bedrag(0))({ (geld, scan) => geld + scan.verkoopPrijs()})
    }

    /**
      * Als kassier begin ik met een nieuwe klant
      *
      */
    def nieuweKlant() :Unit = {
      scansRepo.reset()
    }
  }


}
