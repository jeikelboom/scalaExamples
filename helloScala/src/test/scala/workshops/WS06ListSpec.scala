package workshops

import org.scalatest.{FlatSpec, Matchers}
import workshops.WS06bLists.{Cons, DemoList, TheEnd}
import workshops.WS06bLists.DemoList.lengthOf


class WS06ListSpec extends FlatSpec with Matchers {
  val aList = Cons(1, Cons(2, Cons(3, Cons(4, TheEnd))))
  val bList = Cons("1", Cons("22", Cons("333", TheEnd)))

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

  "alist and blist" should "for combined with for or flatmap" in {
    val forred = for (
      v1 <- aList;
      v2 <- bList
    ) yield s"$v1  $v2"
    val flatMapped = aList.flatMap(v1 => bList.map(v2 => s"$v1  $v2"))
    val expected = Cons("1  1",Cons("1  22",Cons("1  333",Cons("2  1",Cons("2  22",Cons("2  333",Cons("3  1",Cons("3  22",Cons("3  333"
      ,Cons("4  1",Cons("4  22",Cons("4  333",TheEnd))))))))))))
    flatMapped shouldEqual expected
    forred shouldEqual expected
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

  "(((20 - 10) - 2) - 3)" should "5" in {
    val lst: DemoList[Int] = Cons(10, Cons(2, Cons(3, TheEnd)))
    val subtr = lst.foldLeft(20)(_ - _)
    subtr shouldEqual(5)
  }

  "(((20 - 10) - 2) - 3)" should "print" in {
    val lst: DemoList[Int] = Cons(10, Cons(2, Cons(3, TheEnd)))
    val printed = lst.foldLeft("begin")((x, y) => s"(${x} op ${y})")
    printed shouldEqual("(((begin op 3) op 2) op 10)")
  }



  "(10 - (2 - (3 - 1)))" should "10" in {
    val lst: DemoList[Int] = Cons(10, Cons(2, Cons(3, TheEnd)))
    val printed = lst.foldRight("end")((x, y) => s"(${x} op ${y})")
    printed shouldEqual "(10 op (2 op (3 op end)))"
    lst.foldRight(1)(_ - _) shouldEqual 10
  }


}
