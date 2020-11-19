package workshops

import java.time.Instant

import org.scalatest.{FlatSpec, Matchers}
import WS14implicitly._;

class WS14implicitlyTest extends FlatSpec with Matchers {

  "implicitly" should "yield max and min" in {
    val mx: Instant = getMax[Instant]
    mx shouldEqual(Instant.MAX)
  }

}
