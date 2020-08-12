package workshops

import org.scalatest.{FlatSpec, Matchers}
import workshops.WS06BinTree.{BinTree, Node, Leaf}
import workshops.WS06BinTree.BinTree.{fmap, flatmap}


class WS06BinTreeSpec extends FlatSpec with Matchers {

  val na1: BinTree[Int] = Node(Leaf(3), Leaf(4))
  val na2: BinTree[Int] = Node(Leaf(30), Leaf(40))
  val na: BinTree[Int] = Node(na1, na2)
  val nb: BinTree[Int] = Leaf(100)
  val n: BinTree[Int] = Node(na, nb)

  "node n" should "map to String" in {
    def ts(i: Int): String = s"<${i}>"
    val  nstrings: BinTree[String] = fmap(ts)(n)
    nstrings shouldEqual Node(Node(Node(Leaf("<3>"),Leaf("<4>")),Node(Leaf("<30>"),Leaf("<40>"))),Leaf("<100>"))
  }

  def mf(i: Int): BinTree[String] = Leaf(s"<${i}>" )

  "Bintree N" should "flatmao to Strings" in {
    val flatmapped: BinTree[String] = flatmap(mf)(n)
    flatmapped shouldEqual Node(Node(Node(Leaf("<3>"),Leaf("<4>")),Node(Leaf("<30>"),Leaf("<40>"))),Leaf("<100>"))
  }




}
