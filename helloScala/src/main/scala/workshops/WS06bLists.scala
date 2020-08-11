package workshops

object WS06bLists {

  sealed abstract class DemoList[+T] {
    def map[B](f : (T) => B) :DemoList[B]
    def withFilter(p: (T) => Boolean) :DemoList[T]
    def flatMap[B](mf: (T) => DemoList[B]) :DemoList[B]
    def append[B >: T ](that: DemoList[B]): DemoList[B]
    def foldLeft[B](begin: B)(f: (B,T) => B): B
    def foldRight[B](end: B)(f: (T,B) => B): B
  }

  case object TheEnd extends DemoList[Nothing] {
    override def map[B](f: Nothing => B): DemoList[B] = TheEnd

    override def withFilter(p: Nothing => Boolean): DemoList[Nothing] = TheEnd

    override def flatMap[B](mf: Nothing => DemoList[B]): DemoList[B] = TheEnd

    override def append[B](that: DemoList[B]): DemoList[B] = that

    override def foldLeft[B](begin: B)(f: (B, Nothing) => B): B = begin

    override def foldRight[B](end: B)(f: (Nothing, B) => B): B = end
  }

  final case class Cons[T](head: T, tail: DemoList[T]) extends DemoList[T] {
    override def map[B](f: T => B): DemoList[B] = Cons(f(head), tail.map(f))

    override def withFilter(p: T => Boolean): DemoList[T] =
      if (p(head)) Cons(head, tail.withFilter(p)) else tail.withFilter(p)

    override def flatMap[B](mf: T => DemoList[B]): DemoList[B] =
      mf(head).append(tail.flatMap(mf))

    override def append[B >: T](that: DemoList[B]): DemoList[B] = {
      println("appended" )
      this match {
        case Cons(a, TheEnd) => Cons(a,that)
        case Cons(a, t) => Cons(a,t.append(that))
      }
    }

    override def foldLeft[B](begin: B)(f: (B, T) => B): B = f(tail.foldLeft(begin)(f), head)
    override def foldRight[B](end: B)(f: (T, B) => B): B = f(head, tail.foldRight(end)(f))

  }

  object DemoList {
    def lengthOf[A](list: DemoList[A]) : Long = list match {
      case TheEnd => 0
      case Cons(_, rest) => 1 + lengthOf(rest)
    }
  }
}


