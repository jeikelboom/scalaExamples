val talen1 = "Java" :: "Groovy" :: "Clojure" :: "Scala" :: "Python" :: Nil
//
val talen2 = List("Java", "Groovy","Clojure", "Scala", "Python")

val groovy = talen1(1)
val lengtes = talen1.map({_ length})
val lengtes2 = talen1.map({ (x) => x.length()})

val onsFilter: String => Boolean ={(str) => str.length == 6}

val zeslang= talen1.filter(onsFilter)


def totaal(l: List[Int]): Int = {
  l match {
    case Nil => 0
    case xx :: yy => xx + totaal(yy)
  }
}

val totaleLengte1 = totaal(lengtes)

val totaleLengte2 = lengtes.fold(0)({(x,y) => x + y})


def f(i:Int, j:Int) = i to j

def int2List(n: Int): List[Int] = (1 to n).foldLeft(List[Int]())({(l,e) => l ++ (e :: Nil) })

val eenint2list = int2List(4)

val l4 = int2List(4)

val genest = lengtes.map(int2List)

val flattened = genest.flatten

val nietGenest = lengtes.flatMap(int2List)

val ontnest = for {
  x <- genest
  y <- x
} yield (y)


