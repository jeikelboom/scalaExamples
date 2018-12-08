/*
objectives:
understand val var def types
firstclass functions
imperatief while
control stuctures if and for as expressions
 */

// val (values) are immutable
// The type maybe specified otherwise it is inferred by the compiler
val wereld: String = "wereld"
// String substitution
val greeting = s"Hallo ${wereld}"

// with def functions are defined
// the last expression is the return value.
def increment(i: Int): Int = {i + 1}
// variables are mutable
var teller = 0;
teller = increment(teller)

val eenLijst= List("Java", "Scala", "Kotlin", "Groovy", "Clojure")

teller = 0
while (teller < eenLijst.length) {
  println(eenLijst(teller))
  teller = increment(teller)
}
// shorter
eenLijst.foreach{str => println(s"  $str")}

val lengtes = eenLijst.map({str => str.length()})

// if is een expressie
// isLang is van het type functie van string naar String
// We gebruiken hier dus een functie literal
val isLang: String => String = {x =>if (x.length > 5) "lang" else "kort"}
// for is ook een expressie
val lengtes2 = for {
    x <- eenLijst
  } yield isLang(x)




  // Functies zijn firstclass, ze kunnen ook als argument of returwaarde worden gebruikt
val isOokLang = identity(isLang)

println(workshops.aString)

