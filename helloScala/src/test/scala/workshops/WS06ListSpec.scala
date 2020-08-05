package workshops

import org.scalatest.{FlatSpec, Matchers}
import workshops.WS06bLists.{Cons, DemoList, TheEnd}
import workshops.WS06bLists.DemoList.lengthOf


class WS06ListSpec extends FlatSpec with Matchers {
  val aList = Cons(1, Cons(2, Cons(3, Cons(4, TheEnd))))

  "List " should " have length 0" in {
    val l1: DemoList[Int] = TheEnd
    lengthOf(l1) shouldEqual  0
  }

  "List 1,2,3,4" should "have lenth 4" in {
    lengthOf(aList) shouldEqual 4
  }

  def makeString(a: Int) : String = s"m=${a}"

  "List 1,2,3,4" should "map to strings" in {
    aList.map(makeString) shouldEqual Cons("m=1",Cons("m=2",Cons("m=3",Cons("m=4",TheEnd))))
  }

  "List 1,2,3,4" should "map to strings with for" in {
    val list2 = for (
      m <- aList
    ) yield makeString(m)
    list2 shouldBe  Cons("m=1",Cons("m=2",Cons("m=3",Cons("m=4",TheEnd))))
  }

  def odd(i: Int) :Boolean = i % 2 == 1

  "List 1,2,3,4" should "filter odd" in {
    val list2 = for (
      m <- aList
      if odd(m)
    ) yield makeString(m)
    list2 shouldEqual Cons("m=1",Cons("m=3",TheEnd))
  }

  def fm(i: Int) = Cons(i, Cons(i,TheEnd))

  "flatmap" should "be flat" in {
    val flatmapped: DemoList[Int] = aList.flatMap(fm)
    flatmapped shouldEqual Cons(1,Cons(1,Cons(2,Cons(2,Cons(3,Cons(3,Cons(4,Cons(4,TheEnd))))))))
  }

  "map" should "not be flat" in {
    val mapped: DemoList[DemoList[Int]]   = aList.map(fm)
    mapped shouldEqual Cons(Cons(1,Cons(1,TheEnd)),Cons(Cons(2,Cons(2,TheEnd)),Cons(Cons(3,Cons(3,TheEnd)),Cons(Cons(4,Cons(4,TheEnd)),TheEnd))))
  }
}
