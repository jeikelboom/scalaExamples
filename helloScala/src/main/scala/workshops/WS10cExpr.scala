package workshops

import workshops.WS10cExpr.Expression

object WS10cExpr {

  trait Expression[O] {
    def pretty : String
    def eval : O
  }

  trait Literal[T] extends Expression[T]

  trait Unop[O,I1] extends Expression[O]

  trait Binop[O, I1, I2] extends Expression[O]

  case class IntegerLiteral(i :Int) extends Literal[Int] {
    override def pretty: String = s"${i}"

    override def eval: Int = i
  }

  case class IntegerPlus(left: Expression[Int], right :Expression[Int]) extends Binop[Int, Int, Int] {
    override def pretty: String = s"(${left.pretty} + ${right.pretty})"

    override def eval: Int = left.eval + right.eval
  }


}
