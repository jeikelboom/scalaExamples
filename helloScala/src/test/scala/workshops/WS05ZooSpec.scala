package workshops

import org.scalatest.{FlatSpec, Matchers}
import workshops.WS05Zoo.{Animal, Eend, Hond, Kikker}

class WS05ZooSpec extends FlatSpec with Matchers {

  val lijst: List[Animal] = List(Kikker("Kermit"), Eend("Donald"), Hond("Fido"))

  "list" should "contain fido" in {
    lijst.contains(Hond("Fido")) shouldBe true
  }

}
