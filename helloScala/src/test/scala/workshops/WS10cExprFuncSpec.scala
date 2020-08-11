package workshops

import org.scalatest.{FlatSpec, Matchers}
import workshops.WS10cExpr.{IntegerLiteral,IntegerPlus}


class WS10cExprFuncSpec extends FlatSpec with Matchers {

  "(1+2) + (3+4)"  should "be" in {
    val anExpr = IntegerPlus(IntegerPlus(IntegerLiteral(1),IntegerLiteral(2)),IntegerPlus(IntegerLiteral(3), IntegerLiteral(4)))
    val output = anExpr.pretty
    output shouldEqual("((1 + 2) + (3 + 4))")
  }

  "1 + 1 " should "be 2" in {
    val plusExpr = IntegerPlus(IntegerLiteral(1), IntegerLiteral(1))
    val result: Int = plusExpr.eval
    result shouldEqual(2)
  }



}
