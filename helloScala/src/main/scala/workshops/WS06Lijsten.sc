val talen2 = List("Java", "Groovy")
val talen = "Java" :: "Groovy" :: "Clojure" :: "Scala" :: "Python" :: Nil

val groovy = talen(2)

val lengtes = talen.map{_ length}
val lengtes2 = talen.map({(x) => x.length()})

val zeslang= talen.filter{_.length == 6}

def totaal(l: List[Int]): Int = {
  l match {
    case Nil => 0
    case xx :: yy => xx + totaal(yy)
  }
}

val totaleLengte1 = totaal(lengtes)
val totaleLengte2 = lengtes.fold(0)({(x,y) => x + y})

def int2List(n: Int): List[Int] = (1 to n).foldLeft(List[Int]())({(l,e) => l ++ (e :: Nil) })

val l4 = int2List(4)

val genest = lengtes.map(int2List)

val flattened = genest.flatten

val nietGenest = lengtes.flatMap(int2List)


