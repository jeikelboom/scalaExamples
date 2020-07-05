package workshops


object WS10bExprFunc {

  sealed trait Expr[T] {
    def pretty() : String
  }
  case class Plus(left: Expr[Int], right: Expr[Int]) extends Expr[Int] {
    override def pretty(): String = s"(${left.pretty} + ${right.pretty})"
  }

  case class Constant(i: Int) extends Expr[Int] {
    override def pretty(): String = s"${i}"
  }

  def eval(e: Expr[Int]) :Int = {
    e match {
      case Plus(l, r) => eval(l) + eval(r)
      case Constant(c) => c
    }
  }


}
