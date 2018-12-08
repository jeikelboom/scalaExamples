package workshops
import example.Hello
import org.scalatest._

class Ws01Test extends FlatSpec with Matchers {
  "The Hello object" should "say hello" in {
    Hello.greeting shouldEqual "hello"
  }
}
