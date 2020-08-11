package workshops

class WS06BinTree {

  sealed abstract class BinTree[A]
  final case class Node[A](left: BinTree[A], right: BinTree[A])  extends BinTree[A]
  final case class Leaf[A](value: A) extends BinTree[A]

  object BinTree {
    def fmap[A, B](f: A => B)(tree: BinTree[A]) : BinTree[B] = tree match {
      case Leaf(a) => Leaf(f(a))
      case Node(a, b) => Node(fmap(f)(a), fmap(f)(b))
    }
  }

}
