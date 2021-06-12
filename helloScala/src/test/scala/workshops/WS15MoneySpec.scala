package workshops

import org.scalatest.{FlatSpec, Matchers}

class WS15MoneySpec extends FlatSpec with Matchers{

  "Float" should "not be used for money" in {
    val cent: Float = 0.01F
    val lijst = 1 to 10
    val totaal: Float = lijst.foldLeft(0.0F)((accu, _) => accu + cent)
    println(totaal)
  }

}
