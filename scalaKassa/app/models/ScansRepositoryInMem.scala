package models

import models.domein._

trait ScansRepositoryInMem extends ScansRepo {
  val artRepo: ArtikelRepository
  val scansRepo: ScansRepo = this
  var bonRegels: Map[String, Scan] = Map.empty

  override def storeScan(ean: String): Either[FoutMelding, Scan] = {
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
            val scan = Scan(artikel, 1)
            bonRegels += ean -> scan
            Right(scan)
          }
        }

      }
    }
  }

  override def regels(): List[Scan] = bonRegels.to[List].map({_._2})

  override def reset(): Unit = {
    bonRegels = Map.empty
  }
}
