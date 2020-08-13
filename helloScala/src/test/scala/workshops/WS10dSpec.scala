package workshops

import org.scalatest.{FlatSpec, Matchers}
import WS10d_Expr._
import WS10d_TypeClasses._


class WS10dSpec extends FlatSpec with Matchers {

  val anexpr: Expr = Plus(Plus(Literal(1),Literal(2)), Literal(3))

  "anexpr" should "eval to 6" in {
    anexpr.eval shouldEqual(6)
  }

}
