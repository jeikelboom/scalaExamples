package workshops

import workshops.WS10d_Expr.{Expr, Literal, Plus}

object WS10d_TypeClasses {

  trait Evaluator[A] {
    def evaluate(value: A): Int
  }

  implicit class evaluatable[A](value: A) {
    def eval(implicit e:Evaluator[A]): Int = e.evaluate(value)
  }

  implicit val PlusEvaluator: Evaluator[Plus] =
    new Evaluator[Plus] {
      override def evaluate(value: Plus): Int = value.a.eval + value.b.eval
    }
  implicit val LiteralEvaluator: Evaluator[Literal] =
    new Evaluator[Literal] {
      override def evaluate(value: Literal): Int = value.value
    }

  implicit val ExprEvaluator: Evaluator[Expr] =
    new Evaluator[Expr] {
      override def evaluate(value: Expr): Int = value match {
        case a@Literal(_) => a.eval
        case p@Plus(_, _) => p.eval
      }
    }

}


