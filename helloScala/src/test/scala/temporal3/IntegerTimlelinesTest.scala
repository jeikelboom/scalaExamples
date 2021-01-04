package temporal3

import org.scalatest.{FlatSpec, Matchers}
import IntegerTimeUnit.IntegerTimelines._

class IntegerTimlelinesTest  extends FlatSpec with Matchers {

  val tl1: Timeline[String] = Timeline()
    .append(3,100, "aa")
    .append(6,8, "bb")
    .append(10, 100, "ccc")
    .append(13,17, "CCC")

  "map length" should "glue intervals " in {
    val actual = tl1.map(_.length)
    val actual2 = for (
      va <- tl1
    ) yield va.length
    val expected: Timeline[Int] = Timeline()
      .append(3, 8, 2)
      .append(10,17, 3)
    actual shouldEqual expected
    actual2 shouldEqual expected
  }

  "map uppercase" should "glue intervals " in {
    val actual = tl1.map(_.toUpperCase)
    val actual2 = for(
      v1 <- tl1
    ) yield v1.toUpperCase
    val expected: Timeline[String] = Timeline()
      .append(3,5, "AA")
      .append(6,8, "BB")
      .append(10,17, "CCC")
    actual shouldEqual expected
    actual2 shouldEqual expected
    actual.get(13) shouldEqual Some("CCC")
    actual.get(3000) shouldEqual None
  }

  "append" should "glue and overwrite intervals" in {
    (6 to 8).foreach(start => {
      val expected = Timeline().append(3,5,"aa").append(6,start,"bb")
      val actual = tl1.append(start,start, "bb")
      actual shouldEqual expected
    })
  }

  "append" should " overwrite interval" in {
    val actual = tl1.append(9,9, "bb")
    val expected = Timeline().append(3,5,"aa").append(6,9,"bb")
    actual shouldEqual expected
  }

}
