package workshops

object WS10d_Expr {
  sealed trait Expr
  case class Plus(a: Expr, b: Expr) extends Expr
  case class Literal(value: Int) extends Expr
}
