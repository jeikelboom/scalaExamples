package models.domein

import models.ScansRepositoryInMem
import models.domein.Constants._
import org.scalatest.{FlatSpec, Matchers}

class KassaSpec extends FlatSpec with Matchers {

  /**
    * Simuleer een database met behulp van een map
    */
  trait RepoMock {
    val artRepo = new ArtikelRepository {
      val artmap :Map[String, Artikel] = Map(
        HAM.ean -> HAM,
        COLA.ean -> COLA
      )
      override def findByEan(ean: String): Either[FoutMelding, Artikel] = {
        artmap.get(ean) match {
          case None => Left(ARTIKEL_NIET_GEVONDEN)
          case Some(art) => Right(art)
        }
      }

    }
  }

  trait ScansMock extends ScansRepositoryInMem {
    val artRepo: ArtikelRepository
  }


  "Scan 12" should "return Ham" in {
    object kassa extends Kassa with ScansMock with RepoMock
    val rv = kassa.scan(HAM.ean)
    rv shouldEqual(Right(Scan(HAM,1 )))
    kassa.totaalBedrag() shouldEqual(Bedrag(1,95))
  }
  "Scan ??? (niet bestaand ean)" should "return error" in {
    object kassa extends Kassa with ScansMock with RepoMock
    val rv= kassa.scan("???")
    rv.shouldEqual(Left(ARTIKEL_NIET_GEVONDEN))
  }
  "Scan 12" should "totaal 1,95" in {
    object kassa extends Kassa with ScansMock with RepoMock
    kassa.scan(COLA.ean)
    kassa.scan(COLA.ean)
    kassa.scan(HAM.ean)
    kassa.totaalBedrag() shouldEqual(Bedrag(7, 5))
  }

}
