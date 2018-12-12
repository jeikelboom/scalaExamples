package models.domein



import org.scalatest.{FlatSpec, Matchers}

class KassaSpec extends FlatSpec with Matchers {
  val HAM = Artikel.artikel(1, "12", "Ham", ArtikelGroep.Vleeswaren, Geld(1, 95))
  val COLA = Artikel.artikel(1, "13", "Cola", ArtikelGroep.Frisdrank, Geld(2, 55))

  /**
    * Simuleer een database met map
    */
  trait RepoMock {
    val artRepo = new ArtikelenRepo {
      val artmap :Map[String, Artikel] = Map(
        HAM.ean -> HAM,
        COLA.ean -> COLA
      )
      override def findByEan(ean: String): Either[ErrorMessage, Artikel] = {
        artmap.get(ean) match {
          case None => Left(ErrorMessage("error.notfound"))
          case Some(art) => Right(art)
        }
      }

    }
  }

  trait ScansMock extends ScansRepo {
    val artRepo: ArtikelenRepo
    val scansRepo: ScansRepo = this
    var volgnummer: Int = 0
    var bonRegels : Map[String,Scan] = Map.empty

    override def storeScan (ean: String) :Either[ErrorMessage, Scan] = {
      bonRegels.get(ean) match {
        case Some(Scan(ean, aantal, art)) => {
            bonRegels += ean -> Scan(ean, aantal + 1, art)
            Right(bonRegels(ean))
          }
        case None => {
          //val result: Either[ErrorMessage, Artikel] =
          artRepo.findByEan(ean) match {
            case Left(msg) => Left(msg)
            case Right(artikel) => {
              val scan =Scan(ean, 1, artikel)
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
    rv shouldEqual(Right(Scan("12",1,HAM)))
    kassa.totaalBedrag() shouldEqual(Geld(1,95))
  }
  "Scan ???" should "return error" in {
    object kassa extends Kassa with ScansMock with RepoMock
    val rv= kassa.scan("???")
    rv.shouldEqual(Left(ErrorMessage("error.notfound")))
  }
  "Scan 12" should "totaal 1,95" in {
    object kassa extends Kassa with ScansMock with RepoMock
    kassa.scan(COLA.ean)
    kassa.scan(COLA.ean)
    kassa.scan(HAM.ean)
    kassa.totaalBedrag() shouldEqual(Geld(7, 5))
  }

}
