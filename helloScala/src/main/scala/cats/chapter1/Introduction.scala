package cats.chapter1

import play.api.libs.json._

object Introduction {

  case class Person(name: String, hobby: String)

  implicit val personWrites: Writes[Person] =
    new Writes[Person] {
      override def writes(person: Person): JsValue = Json.obj(
        "name" -> person.name,
        "hobby" -> person.hobby
      )
    }

  implicit class PersonToJson[A](val value: A) {
    def toJson(implicit w: Writes[A]) = w.writes(value)
  }

}
