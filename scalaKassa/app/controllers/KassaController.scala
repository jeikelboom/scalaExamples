package controllers


import javax.inject.Inject
import models.{ArtikelRepositoryDb, ScansRepositoryInMem}
import models.domein._
import models.domein.Constants._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.concurrent._



class KassaController   @Inject()(repo: ArtikelRepositoryDb,
                                  cc: MessagesControllerComponents)
                                 (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {

  trait ArtikelRepoReference {
    val artRepo: ArtikelRepository = repo
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
        Ok(views.html.kassa.kassa(errorForm,List.empty, Bedrag(0)))
      },
      scanData => {
        kassa1.scan(scanData.ean) match {
          case Right(scan) => Redirect(routes.KassaController.kassa).flashing(
            "success" -> "scan.ok",
            "omschrijving" -> scan.artikel.omschrijving,
            "prijs" -> scan.artikel.prijs.toString,
            "totaal" -> kassa1.totaalBedrag().toString)
          case Left(message) => Redirect(routes.KassaController.kassa).flashing("success" -> message.errorCode)
        }
      }
    )
  }

  def kassa() = Action { implicit request =>
    Ok(views.html.kassa.kassa(scanForm, kassa1.regels(), kassa1.totaalBedrag()))
  }

  def bon() = Action{ implicit request =>
    val regels =kassa1.regels()
    val bedrag =kassa1.totaalBedrag()
    kassa1.nieuweKlant()
    Ok(views.html.kassa.bon(regels, bedrag))
  }

}
case class ScanFormData(ean: String)
