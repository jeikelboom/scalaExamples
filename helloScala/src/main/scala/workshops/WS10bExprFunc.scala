package workshops

object WS10bExprFunc {

  sealed trait Expr[T] {

  }
  case class Plus(left: Expr[Int], right: Expr[Int]) extends Expr[Int]
  case class Constant(i: Int) extends Expr[Int]

  def eval(e: Expr[Int]) :Int = {
    e match {
      case Plus(l, r) => eval(l) + eval(r)
      case Constant(c) => c
    }
  }

  def pretty(e: Expr[Int]) : String = {
    e match {
      case Plus(l,r) => s"(${pretty(l)} + ${pretty(r)})"
      case Constant(c) => s"${c}"
    }
  }

}
