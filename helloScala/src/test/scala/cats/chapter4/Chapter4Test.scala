package cats.chapter4

import cats.Applicative
import cats.chapter4.Chapter4Examples.{CalcState, _}
import cats.data.State
import cats.instances.all._
import cats.syntax.all._
import org.scalatest.{FlatSpec, Matchers}

class Chapter4Test extends FlatSpec with Matchers {

  "3 30 +" should "return 33" in {
    val expr = for {
      _ <- eval(IntValue(3))
      _ <- eval(IntValue(30))
      n <- eval(plus)
    } yield n
    val result = expr.run(List()).value
    result._1 shouldEqual List(IntValue(33))
    result._2 shouldEqual 33
  }

  "4 50 +" should "tediously return 54" in {
    val expr1 = for {
      n <- eval(IntValue(4))
    } yield ""
    val expr2 = for {
      i <- expr1
      n <- eval(IntValue(50))
    } yield ""
    val expr3 = for {
      j <- expr2
      n <- eval(plus)
    } yield n
    val result = expr3.run(List()).value
    result._1 shouldEqual List(IntValue(54))
    result._2 shouldEqual 54
  }

  "l1 add l2" should "add to l3" in {
    val l0: List[(Int, Int) => Int] = Applicative[List].pure(addThem)
    val l1: List[Int] = List(1,2,3)
    val l2: List[Int] = List(10,20)
    val l3 = Applicative[List].ap2(l0)(l1, l2)
    l3 shouldEqual List(11, 21, 12, 22, 13, 23)
  }

  "terms " should "applicative" in {
    val term1 = eval(IntValue(3))
    val term2 = eval(IntValue(60))
    val term3 = eval(plus)
  }

  def addThem(a:Int, b:Int): Int = a + b

  "list" should "sequenced" in {
    val terms: List[Term] = List(IntValue(10), IntValue(100), plus)
    val evals: List[CalcState[Int]] = terms.map(eval)
    val comp: CalcState[List[Int]] = evals.sequence
    val result = comp.run(List()).value
    result shouldEqual (List(IntValue(110)), List(10, 100, 110))
  }

  "list" should "traversed" in {
    val terms: List[Term] = List(IntValue(10), IntValue(100), plus)
    val comp: CalcState[List[Int]] = terms.traverse(eval)
    val result = comp.run(List()).value
    result shouldEqual (List(IntValue(110)), List(10, 100, 110))
  }

  def last[A](list: List[A]): Option[A] = if (list.isEmpty) None else Some(list.reverse.head)

  "cats" should "sequence" in {
    val aList: List[Int] = List(1, 2, 3)
    val seqCorrect: List[Option[Int]] = aList.map(Some(_))
    val sequenced: Option[List[Int]] = seqCorrect.sequence
    sequenced shouldEqual  Some(List(1, 2, 3))
    val traversed = seqCorrect.traverse(o => o.toList)
    traversed shouldEqual  List(List(1, 2, 3))
    (None::seqCorrect).sequence shouldEqual None
  }

  def toSome(i: Int): Option[Int] = Some(i)

  "cats" should "import a sequence2" in {
    val aList: List[Int] = List(1, 2, 3)
    val traversed = aList.traverse(toSome(_))
    traversed shouldEqual   Some(List(1, 2, 3))
  }



  "Monads" should "combine" in {
    case class Hlp(s: String)
    val step1: State[Int, String] = State[Int, String] {state => (state, s"the state is $state")}
    val step2: State[Int, String] = State[Int, String] (state => (state * 2, "mooi weertje"))
    def step3(hulp: String): State[Int, Hlp] = State[Int, Hlp] (state => (state*100, Hlp(hulp)))
    val both = for {
      _ <- step1
      _ <- step2
      c <- step3("SOS")
    } yield c
    val result = both.run(5).value
    result shouldEqual (1000,Hlp("SOS"))
  }

  "state" should "manipulate" in {
    val a: State[Int, String] = State[Int, String] {state => (state, s"the state is $state")}
    val (state, result) = a.run(10).value
    state shouldEqual 10
    result  shouldEqual("the state is 10")
    a.runS(10).value shouldEqual state
    a.runA(10).value shouldEqual result
  }


  "List[1,2,3]" should "follow Grahams example" in {
    def dec(n: Int): Option[Int] = if (n >0) Some(n) else None
    val lst = List(1, 2, 3)
    val traversed = lst.traverse(dec)
    traversed shouldEqual  Some(List(1, 2, 3))
    val emptyList: List[Int] = List()
    val emptyTraversed = emptyList.traverse(dec)
    emptyTraversed shouldEqual Some(List())
  }

}
