val languages1 = "Java" :: "Groovy" :: "Clojure" :: "Scala" :: "Python" :: Nil
//
val languages2 = List("Java", "Groovy","Clojure", "Scala", "Python")

val groovy = languages1(1)
val lengths = languages1.map({_ length})
val lengths2 = languages1.map({ (x) => x.length()})

val length6Filter: String => Boolean ={ (str) => str.length == 6}

val zeslang= languages1.filter(length6Filter)


def calculateSumOf(alist: List[Int]): Int = {
  alist match {
    case Nil => 0
    case xx :: yy => xx + calculateSumOf(yy)
  }
}

val totaleLengte1 = calculateSumOf(lengths)

val totaleLengte2 = lengths.fold(0)({ (x, y) => x + y})


def f(i:Int, j:Int) = i to j

def int2List(n: Int): List[Int] = (1 to n).foldLeft(List[Int]())({(l,e) => l ++ (e :: Nil) })

val eenint2list = int2List(4)

val l4 = int2List(4)

val genest = lengths.map(int2List)

val flattened = genest.flatten

val nietGenest = lengths.flatMap(int2List)

val ontnest = for {
  x <- genest
  y <- x
} yield (y)


