package temporal2

import temporal2.Temporal2._
import TestData._
import org.scalatest.{FlatSpec, Matchers}

class Temporal2JoinElementsTest extends FlatSpec with Matchers{

  "[t2,t4] a join [t3,t5] f" should "[t3,t4] f a" in {
    val elt1: TimeLineElement[String] = TimeLineElement(i24, "Hello")
    val elt2: TimeLineElement[String => Int] = TimeLineElement(i35, _.length)

    val joined = elt1.join(elt2)
    val leftOver1 = elt1.leftOverFromThis(elt2)
    val leftOver2 = elt1.leftOverFromOther(elt2)

    joined shouldEqual Some(TimeLineElement(t3,t4, 5))
    leftOver1 shouldEqual None
    leftOver2.get.interval shouldEqual i45
  }

  "[t3,t5] a join [t2,t4] f" should "[t3,t4] f a" in {
    val elt1: TimeLineElement[String] = TimeLineElement(i35, "Hello")
    val elt2: TimeLineElement[String => Int] = TimeLineElement(i24, _.length)

    val joined = elt1.join(elt2)
    val leftOver1 = elt1.leftOverFromThis(elt2)
    val leftOver2 = elt1.leftOverFromOther(elt2)

    joined shouldEqual Some(TimeLineElement(t3,t4, 5))
    leftOver1 shouldEqual Some(TimeLineElement(t4,t5, "Hello"))
    leftOver2 shouldEqual None
  }

  "[t2,t4] a join [t3,t4] f" should "[t3,t4] f a and no leftovers" in {
    val elt1: TimeLineElement[String] = TimeLineElement(i24, "Hello")
    val elt2: TimeLineElement[String => Int] = TimeLineElement(i34, _.length)

    val joined = elt1.join(elt2)
    val leftOver1 = elt1.leftOverFromThis(elt2)
    val leftOver2 = elt1.leftOverFromOther(elt2)

    joined shouldEqual Some(TimeLineElement(t3,t4, 5))
    leftOver1 shouldEqual None
    leftOver2 shouldEqual None
  }

}
