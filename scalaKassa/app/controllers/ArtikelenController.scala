package controllers

import javax.inject._
import models._
import models.domein._
import models.domein.Constants._
import models.domein.Artikel
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent._
import scala.concurrent.duration._


class ArtikelenController  @Inject()(repo: ArtikelRepositoryDb,
                                     cc: MessagesControllerComponents)
                                    (implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {


  /**
    * The mapping for the artikel form.
    */
  val artikelForm: Form[CreateArtikelForm] = Form {
    mapping(
      "ean" -> nonEmptyText,
      "omschrijving" -> nonEmptyText,
      "ag" -> nonEmptyText,
      "prijs" -> number()
    )(CreateArtikelForm.apply)(CreateArtikelForm.unapply)
  }

  def index = Action { implicit request =>
    Ok(views.html.artikelen.artikel(artikelForm))
  }

  def addArtikel = Action.async { implicit request =>
    // Bind the form first, then fold the result, passing a function to handle errors, and a function to handle succes.
    artikelForm.bindFromRequest.fold(
      // The error function. We return the index page with the error form, which will render the errors.
      // We also wrap the result in a successful future, since this action is synchronous, but we're required to return
      // a future because the artikel creation function returns a future.
      errorForm => {
        Future.successful(Ok(views.html.artikelen.artikel(artikelForm)))
      },
      // There were no errors in the from, so create the artikel.
      artikel => {
        repo.create(artikel.ean, artikel.omschrijving, artikel.ag, artikel.prijs).map { _ =>
          // If successful, we simply redirect to the index page.
          Redirect(routes.ArtikelenController.index).flashing("success" -> "artikel.created")
        }
      }
    )
  }

  def reset = Action {request =>
    Await.ready(repo.reset(), 100 seconds)
    ARTIKELEN_LIJST.foreach(artikel => repo.create(artikel.ean, artikel.omschrijving, artikel.ag, artikel.pr))
    Redirect(routes.ArtikelenController.artikelenOverzicht).flashing("success" -> "artikel.created")
  }
  /**
    * A REST endpoint that gets all the people as JSON.
    */
  def getArtikelen = Action.async { implicit request =>
    repo.list().map { artikel =>
      Ok(Json.toJson(artikel))
    }
  }

  def artikelenOverzicht = Action.async { implicit request =>
    repo.list().map { lijst  =>
      Ok(views.html.artikelen.artikelen(lijst.to[List]))
    }
  }

  def getArtikelbyEAN( ean: String) = Action.async { implicit request =>
    repo.findByEanF(ean).map((lst: Seq[Artikel]) => lst match  {
      case (Seq(art)) => Ok(views.html.artikelen.artikel(artikelForm.fill(CreateArtikelForm(art))))
      case _ => Ok(views.html.artikelen.artikel(artikelForm))
    }
    )
  }


}
case class CreateArtikelForm(ean: String, omschrijving: String, ag: String, prijs: Int)

object CreateArtikelForm {
  def apply(a: Artikel): CreateArtikelForm = {
    CreateArtikelForm(a.ean, a.omschrijving, a.ag, a.pr)
  }
}