/*
Doelstelling basis type leren kennen
 */

val aByte: Byte = -100
s"Bytes hebben een range van ${Byte.MinValue}..${Byte.MaxValue}"

val short: Short = -100
s"Short heeft een range van ${Short.MinValue}..${Short.MaxValue}"

val anInt: Int = -100
s"Int heeft een range van ${Int.MinValue}..${Int.MaxValue}"

val aLong:Long = -100
s"Long heeft een range van ${Long.MinValue}..${Long.MaxValue}"

val aFloat: Float = -100.123F
s"Float heeft een range van ${Float.MinValue}..${Float.MaxValue}"

val aDouble:Double = -100.123
s"Double heeft een range van ${Double.MinValue}..${Double.MaxValue}"

val aBoolean: Boolean = 2 > 4

val aChar: Char = 'a'
s"Char heeft een range van ${Char.MinValue.toInt}..${Char.MaxValue.toInt}"


val aString: String = "hi"

// Literal Long hexadecimal
val answer = 0X2AL

//Operators zijn eigenlijk methods
// En methodes (met 1 argument) zijn operators
val drie = 1.+(2)
val x = "mooiweer" compare "lelijk weer"
val lower = "HEY DAAR" toLowerCase



