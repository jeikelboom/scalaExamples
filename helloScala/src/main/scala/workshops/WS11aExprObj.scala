package workshops

import workshops.WS10aExprFunc.Constant

object WS11aExprObj {


  abstract class Expr {
    def eval () :Int
  }

  class Constant(val value: Int) extends Expr {
    def eval = value
  }
  object Constant {
    def apply(value: Int): Constant = new Constant(value)
  }

  class Plus(val e1: Expr, e2: Expr) extends Expr{
    def eval = e1.eval() + e2.eval()
  }
  object Plus {
    def apply(e1: Expr, e2: Expr): Plus = new Plus(e1, e2)
  }

  class Mult(val e1: Expr, e2: Expr) extends Expr{
    def eval = e1.eval() * e2.eval()
  }
  object Mult {
    def apply(e1: Expr, e2: Expr): Plus = new Plus(e1, e2)
  }

}
