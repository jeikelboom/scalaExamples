package workshops

object WS06bLists {

  sealed abstract class DemoList[+T] {
    def map[B](f : (T) => B) :DemoList[B]
  }
  case object TheEnd extends DemoList[Nothing] {
    override def map[B](f: Nothing => B): DemoList[B] = TheEnd
  }
  final case class Cons[T](head: T, tail: DemoList[T]) extends DemoList[T] {
    override def map[B](f: T => B): DemoList[B] = Cons(f(head), tail.map(f))
  }


  object DemoList {
    def lengthOf[A](list: DemoList[A]) : Long = list match {
      case TheEnd => 0
      case Cons(_, rest) => 1 + lengthOf(rest)
    }

    def ++[A](n: A, a: DemoList[A]) = Cons(n, a)
  }
}


