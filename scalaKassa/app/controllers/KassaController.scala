package controllers

import javax.inject._

import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}



import javax.inject.Inject
import models.{ArtikelRepositoryDb, ScansRepositoryInMem}
import models.domein._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent._

class KassaController   @Inject()(repo: ArtikelRepositoryDb,
                                  cc: MessagesControllerComponents)
                                 (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  trait ArtikelRepoReference {
    val artRepo: ArtikelenRepository = repo
  }

  object kassa1 extends Kassa with ScansRepositoryInMem with ArtikelRepoReference

  val scanForm: Form[ScanFormData] = Form {
    mapping(
      "ean" -> nonEmptyText,
    )(ScanFormData.apply)(ScanFormData.unapply)
  }


  def scanArtikel = Action { implicit request =>
    scanForm.bindFromRequest.fold(
      errorForm => {
        Ok(views.html.kassa.kassa(errorForm))
      },
      scanData => {
        kassa1.scan(scanData.ean) match {
          case Right(scan) => Redirect(routes.KassaController.kassa).flashing(
            "success" -> "scan.ok",
            "omschrijving" -> scan.artikel.omschrijving,
            "prijs" -> scan.artikel.prijs.toString,
            "totaal" -> kassa1.totaalBedrag().toString)
          case _ => Redirect(routes.KassaController.kassa).flashing("success" -> "error.notfound")
        }
      }
    )
  }

  def kassa( )= Action { implicit request =>
    Ok(views.html.kassa.kassa(scanForm))
  }

}
case class ScanFormData(ean: String)
