package cats.chapter1

import cats.chapter1.Introduction.Person
import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json

class JsonExamplesSpec  extends FlatSpec with Matchers {

  val fred = Person("Fred", "Piano playing")
  val ernie = Person("Ernie", "Paperclips collecting")
  val fredJson: String =
    """{
      |  "name" : "Fred",
      |  "hobby" : "Piano playing"
      |}""".stripMargin

  "Person" should "yield JSON" in {
    val json = Json.toJson(fred)
    Json.prettyPrint(json) shouldEqual(fredJson)
  }

  "Pesron" should "have a toJson" in {
    val json = fred.toJson
    Json.prettyPrint(json) shouldEqual(fredJson)
  }

}
