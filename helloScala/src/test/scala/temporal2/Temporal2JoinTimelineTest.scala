package temporal2

import temporal2.Temporal2._
import TestData._
import cats.Applicative
import org.scalatest.{FlatSpec, Matchers}

class Temporal2JoinTimelineTest extends FlatSpec with Matchers{

  val timeline2: TimeLine[String] = TimeLine(List()).append(i02,"Hello").
    append(i23, "there").append(i35,"goodbye")

  "tl1 app tl2" should "join" in {
    val timeLine1 : TimeLine[String => Int] = Applicative[TimeLine].pure(_.length)
    val timeLine3: TimeLine[Int] = Applicative[TimeLine].ap(timeLine1)(timeline2)
    val expected: TimeLine[Int] = TimeLine(List()).append(i03, 5).append(i35, 7)
    println(timeLine3)
    timeLine3 shouldEqual expected
  }



}
