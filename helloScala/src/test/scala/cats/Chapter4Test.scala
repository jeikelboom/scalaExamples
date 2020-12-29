package cats

import cats.chapter4.Chapter4Examples._
import cats.data.State
import cats.instances.all._
import cats.syntax.all._
import org.scalatest.{FlatSpec, Matchers}



class Chapter4Test extends FlatSpec with Matchers {


  "3 3 +" should "return 6" in {
    val expr = for {
      _ <- eval(IntValue(3))
      _ <- eval(IntValue(3))
      n <- eval(plus)
    } yield s"result = $n"
    val result = expr.run(List()).value
    println(result)
  }

  "4 5 +" should "return 9" in {
    val expr1 = for {
      n <- eval(IntValue(4))
    } yield n
    val expr2 = for {
      _ <- expr1
      n <- eval(IntValue(10))
    } yield n
    val expr3 = for {
      _ <- expr2
      n <- eval(times)
    } yield n
    val result = expr3.run(List()).value
    println(s"45+ $result")
  }



  "list" should "sequenced" in {
    val terms: List[Term] = List(IntValue(3), IntValue(7), plus)
    val evals: List[CalcState[Int]] = terms.map(eval)


  }

  "state" should "manipulate" in {
    val a: State[Int, String] = State[Int, String] {state => (state, s"the state is $state")}
    val (state, result) = a.run(10).value
    state shouldEqual 10
    result  shouldEqual("the state is 10")
    a.runS(10).value shouldEqual state
    a.runA(10).value shouldEqual result
  }

  "Monads" should "combine" in {
    case class Hlp(s: String)
    val step1: State[Int, String] = State[Int, String] {state => (state, s"the state is $state")}
    val step2: State[Int, String] = State[Int, String] (state => (state * 2, "mooi weertje"))
    def step3(hulp: String): State[Int, Hlp] = State[Int, Hlp] (state => (state*100, Hlp(hulp)))
    val both = for {
      a <- step1
      b <- step2
      c <- step3("SOS")
    } yield c
    val result = both.run(5).value
    println(result)
  }

  "cats" should "sequence" in {
    val seqCorrect: List[Option[Int]] = List(Some(1), Some(2), Some(3))
    println(seqCorrect.sequence)
    println(seqCorrect.traverse(o => o.toList))
    println((None::seqCorrect).sequence)
  }

}
