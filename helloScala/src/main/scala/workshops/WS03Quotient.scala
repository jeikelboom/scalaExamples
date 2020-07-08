package workshops

// Een object is ook first class
object WS03Quotient {

  class Q(val t: Int, val n: Int) {
    require(n != 0, "Cannot divide by zero")
    val g = Q.ggd(t.abs, n.abs)
    val teller = n.signum * (t / g)
    val noemer = n.signum * (n / g)

    // auxilary constructor
    def this(n: Int) = this(n, 1)

    def + (andere: Q): Q = {
      val t1 = teller * andere.noemer + andere.teller * noemer
      val n1 = noemer * andere.noemer
      Q(t1, n1)
    }

    override def equals(obj: Any): Boolean = {
      obj match {
        case m: Q => m.teller == this.teller && m.noemer == this.noemer
        case _ => false
      }
    }

    override def toString: String = s" ${teller}/${noemer} "

 //   implicit def intToRational(i: Int) = Breuk(i)
    def asDouble () :Double = teller.asInstanceOf[Double] / noemer.asInstanceOf[Double]
  }

  /**
    * Companion object for Breuk
    *
    */
  object Q {

    def apply(t: Int, n: Int): Q = new Q(t, n)

    def unapply(arg: Q): Option[(Int, Int)] = Some(arg.t, arg.n)

    implicit def apply(t: Int): Q = new Q(t, 1)

    def ggd(a: Int, b: Int): Int = {
      if (b == 0) a else ggd(b, a % b)
    }
  }

  trait DslQ {

    implicit def toDsl(t: Int) = new DslQHlp1(t)

    class DslQHlp1(t: Int){
      def %/(n: Int) = Q(t, n)
    }
  }

}
