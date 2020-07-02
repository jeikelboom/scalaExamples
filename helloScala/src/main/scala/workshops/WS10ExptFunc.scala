package workshops

object WS10ExptFunc {

  sealed trait Expr
  case class Plus(left: Expr, right: Expr) extends Expr
  case class Constant(i: Int) extends Expr

  def eval(e: Expr) :Int = {
    e match {
      case Plus(l, r) => eval(l) + eval(r)
      case Constant(c) => c
    }
  }

  def pretty(e: Expr) : String = {
    e match {
      case Plus(l,r) => s"(${pretty(l)} + ${pretty(r)})"
      case Constant(c) => s"${c}"
    }
  }

}
