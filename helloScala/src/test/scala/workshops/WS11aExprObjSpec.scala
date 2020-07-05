package workshops

import org.scalatest.{FlatSpec, Matchers}
import workshops.WS11aExprObj.{Constant, Plus}

class WS11aExprObjSpec  extends FlatSpec with Matchers {

  "Constant 42 " should "evaluate to 42" in {
    val c42 =  Constant(42)
    c42.eval shouldEqual(42)
  }

  "20 + 22" should "be 42" in {
    val c20 = Constant(20)
    val c22 = Constant(22)
    val add = Plus(c20, c22)
    add.eval shouldEqual(42)
  }

}
