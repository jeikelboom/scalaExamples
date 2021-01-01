package temporal3
import org.scalatest.{FlatSpec, Matchers}
import IntegerTimeUnit.IntegerTimelines._
import IntegerTimeUnit.IntegerTimelines.timeLineApplicative

class IntegerTimelineApTest   extends FlatSpec with Matchers {


  "tl1" should "equal itself" in {
    val tl1: Timeline[String] = Timeline()
      .append(3,5, "aa")
      .append(6,8, "bb")
      .append(10, 12, "cc")
      .append(13,17, "dd")
    val p: Timeline[(String, String) => Boolean] = timeLineApplicative.pure((x,y) => x == y)
    val actual: Timeline[Boolean] = timeLineApplicative.ap2(p)(tl1, tl1)
    val expected: Timeline[Boolean]  = Timeline().append(3,8, true).append(10,17,true)
    actual shouldEqual expected
  }

  val tl2a: Timeline[String] = Timeline()
    .append(3,5, "aa")
    .append(6,8, "bb")
    .append(10, 12, "cc")
    .append(13,17, "dd")
    .append(20,22, "ee")
    .append(30,32, "ee")
    .append(40,42, "ee")
    .append(50,52, "ee")
    .append(60,64, "ee")

  val tl2b: Timeline[String] = Timeline()
    .append(3,4, "WW")
    .append(5,8, "XX")
    .append(8, 13, "YY")
    .append(13,18, "ZZ")
    .append(20,22, "EE")
    .append(29,33, "EE")
    .append(38,39, "EE")
    .append(53,54, "EE")
    .append(62,63, "EE")

  "tl1" should "append from tl2" in {
    val p: Timeline[(String, String) => String] = timeLineApplicative.pure((x,y) => s"$x$y" )
    val actual: Timeline[String] = timeLineApplicative.ap2(p)(tl2a, tl2b)
    val expected: Timeline[String]  = Timeline()
      .append(3,4, "aaWW")
      .append(5,5, "aaXX")
      .append(6,8, "bbXX")
      .append(8, 8,"bbYY")
      .append(10, 12, "ccYY")
      .append(13,17, "ddZZ")
      .append(20, 22, "eeEE")
      .append(32, 32, "eeEE")
      .append(20, 22, "eeEE")
      .append(30, 32, "eeEE")
      .append(62,63, "eeEE")
    actual shouldEqual expected
  }
}
