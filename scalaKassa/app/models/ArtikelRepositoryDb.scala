package models

import javax.inject.{Inject, Singleton}
import models.domein.{Artikel, ArtikelenRepository, FoutMelding}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

@Singleton
class ArtikelRepositoryDb @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends ArtikelenRepository {
  val TABLENAME= "artikelen"
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private  class ArtikelTable(tag: Tag) extends Table[Artikel](tag, "artikelen") {

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    /** The ID column, which is the primary key, and auto incremented */
    def ean = column[String]("ean", O.PrimaryKey)

    /** The name column */
    def omschrijving = column[String]("omschrijving")

    /** The age column */
    def artgroep = column[String]("artgroep")

    def prijs = column[Int]("prijs")

    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the Person object.
      *
      * In this case, we are simply passing the id, name and page parameters to the Person case classes
      * apply and unapply methods.
      */
    def * = (id, ean, omschrijving, artgroep, prijs) <> ((Artikel.apply _).tupled, Artikel.unapply)
  }

  private val artikelen = TableQuery[ArtikelTable]

  def create(ean: String, omschrijving: String, ag: String, pr: Int) : Future[Artikel] = db.run {
    (artikelen.map(p => (p.ean, p.omschrijving, p.artgroep, p.prijs))
      returning artikelen.map(_.id)
      into ((art, id ) => Artikel(id, art._1, art._2, art._3, art._4))
      ) += (ean, omschrijving, ag, pr)
  }

  def list(): Future[Seq[Artikel]] = db.run {
    artikelen.result
  }

  override def findByEan(ean: String): Either[FoutMelding, Artikel] = {
    val result: Future[Seq[Artikel]] = db.run {
      artikelen.filter(_.ean === ean).result
    }
    val waited: Try[Seq[Artikel]] = Await.ready(result, 100 seconds).value.get
    waited match {
      case Success(Seq(artikel)) => Right(artikel)
      case Failure(exception) => Left(FoutMelding("error.notfound"))
    }
  }


}
