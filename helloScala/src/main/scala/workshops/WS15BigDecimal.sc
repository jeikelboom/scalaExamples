import scala.math.BigDecimal.RoundingMode

val nihil = BigDecimal.apply("0.00")
val cent:BigDecimal = BigDecimal.apply("0.01")
val tenCent = (1 to 10).foldLeft(nihil)((accu, _) => accu + cent)

val divideByThree = BigDecimal.apply("10.00")/ BigDecimal.apply("3.00")
val divideBySeven = BigDecimal.apply("10.00")/ BigDecimal.apply("7.00")


val conversion = BigDecimal.apply("1.2612")
val lunchGBP = BigDecimal.apply("12.85")
val dinnerGBP = BigDecimal.apply("25.76")

val lunchEUR = (lunchGBP * conversion).setScale(2, RoundingMode.HALF_DOWN)
val dinnerEUR = (dinnerGBP * conversion).setScale(2, RoundingMode.HALF_DOWN)
val totalEUR1= lunchEUR + dinnerEUR

val totalGBP = lunchGBP + dinnerGBP
val totalEUR2 = (totalGBP * conversion).setScale(2, RoundingMode.HALF_DOWN)