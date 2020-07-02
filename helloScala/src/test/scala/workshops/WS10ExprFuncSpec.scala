package workshops

import org.scalatest.{FlatSpec, Matchers}
import workshops.WS10ExptFunc.eval
import workshops.WS10ExptFunc.Plus
import workshops.WS10ExptFunc.Constant
import workshops.WS10ExptFunc.pretty



class WS10ExprFuncSpec extends FlatSpec with Matchers {

  "1 + 1 " should "be 2" in {
    val plusExpr = Plus(Constant(1), Constant(1))
    eval (plusExpr) shouldEqual(2)
   }

  "(1+2) + (3+4)"  should "be" in {
    val anExpr = Plus(Plus(Constant(1),Constant(2)),Plus(Constant(3), Constant(4)))
    val output = pretty (anExpr)
    output shouldEqual("((1 + 2) + (3 + 4))")
  }
}
