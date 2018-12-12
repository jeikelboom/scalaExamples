package models.domein



import org.scalatest.{FlatSpec, Matchers}

class KassaSpec extends FlatSpec with Matchers {
  val HAM = Artikel.artikel(1, "12", "Ham", ArtikelGroep.Vleeswaren, Bedrag(1, 95))
  val COLA = Artikel.artikel(1, "13", "Cola", ArtikelGroep.Frisdrank, Bedrag(2, 55))

  /**
    * Simuleer een database met map
    */
  trait RepoMock {
    val artRepo = new ArtikelenRepository {
      val artmap :Map[String, Artikel] = Map(
        HAM.ean -> HAM,
        COLA.ean -> COLA
      )
      override def findByEan(ean: String): Either[FoutMelding, Artikel] = {
        artmap.get(ean) match {
          case None => Left(FoutMelding("error.notfound"))
          case Some(art) => Right(art)
        }
      }

    }
  }

  trait ScansMock extends ScansRepo {
    val artRepo: ArtikelenRepository
    val scansRepo: ScansRepo = this
    var volgnummer: Int = 0
    var bonRegels : Map[String,Scan] = Map.empty

    override def storeScan (ean: String) :Either[FoutMelding, Scan] = {
      bonRegels.get(ean) match {
        case Some(Scan(art, aantal)) => {
            bonRegels += ean -> Scan(art, aantal + 1)
            Right(bonRegels(ean))
          }
        case None => {
          //val result: Either[ErrorMessage, Artikel] =
          artRepo.findByEan(ean) match {
            case Left(msg) => Left(msg)
            case Right(artikel) => {
              val scan =Scan(artikel, 1)
              bonRegels += ean -> scan
              Right(scan)
            }
          }

        }
      }
    }


    override def regels(): List[Scan] = bonRegels.to[List].map({_._2})
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
    rv.shouldEqual(Left(FoutMelding("error.notfound")))
  }
  "Scan 12" should "totaal 1,95" in {
    object kassa extends Kassa with ScansMock with RepoMock
    kassa.scan(COLA.ean)
    kassa.scan(COLA.ean)
    kassa.scan(HAM.ean)
    kassa.totaalBedrag() shouldEqual(Bedrag(7, 5))
  }

}