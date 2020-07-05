/*
objectives:
understand val var def types
firstclass functions
imperatief while
control stuctures if and for as expressions
 */

1 + 4
res0 + 3

// val (values) are immutable
// The type maybe specified otherwise it is inferred by the compiler
val wereld: String = "wereld"
// String interpolation
val greeting = s"Hallo ${wereld}"

// with def functions are defined
// the last expression is the return value.
def increment(i: Int) = {i.+(1)}
// variables are mutable
var teller = 0;
teller = increment(3)

val someList= List("Java", "Scala", "Kotlin", "Groovy", "Clojure")

val anArray = Array("Java", "Scala", "Kotlin")
val scala = someList(1)

teller = 0
while (teller < someList.length) {
  println(someList(teller))
  teller = increment(teller)
}
// shorter
someList.foreach({ str => println(s"  $str")})

val lengtes = someList.map({ str => str.length()})

// if is een expressie
// isLang is van het type functie van string naar String
// We gebruiken hier dus een functie literal
val isLang: String => String = {x =>if (x.length > 5) "lang" else "kort"}

val lijstLang = someList.map(isLang)
// for is ook een expressie
val lengtes2 = for {
    x <- someList
  } yield isLang(x)




  // Functies zijn firstclass, ze kunnen ook als argument of returwaarde worden gebruikt
val isOokLang: String => String = identity(isLang)

println(workshops.aString)

