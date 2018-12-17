package models

import models.domein._

trait ScansRepositoryInMem extends ScansRepo {
  val scansRepo: ScansRepo = this
  var bonRegels: Map[String, Scan] = Map.empty

  override def storeScan(scan: Scan): Unit = bonRegels += (scan.artikel.ean -> scan)

  override def findScan(ean: String): Option[Scan] = bonRegels.get(ean)

  override def regels(): List[Scan] = bonRegels.to[List].map({_._2})

  override def reset(): Unit = {
    bonRegels = Map.empty
  }
}
