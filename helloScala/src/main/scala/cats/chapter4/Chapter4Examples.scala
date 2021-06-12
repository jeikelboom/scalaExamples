package cats.chapter4

import cats.data.State

object Chapter4Examples {

  sealed trait Term
  final case class IntValue(value: Int) extends Term
  final case class Binop(symbol:String, eval: (Int, Int) => Int) extends Term
  final case class Error()
  val plus: Term = Binop("+", _ + _)
  val times: Term = Binop("*", _ * _)



  //def calculator(expression: List[Term]): Int = 1

  type CalcState[A] = State[List[Term], A]
  def eval(term:Term): CalcState[Int] = State[List[Term], Int] { stack =>
    (term, stack) match {
      case (IntValue(n),_) => (IntValue(n) :: stack, n)
      case (Binop(s, op),IntValue(v1) :: IntValue(v2):: tail) => (IntValue(op(v1, v2))::tail, op(v1, v2))
    }
  }

}
