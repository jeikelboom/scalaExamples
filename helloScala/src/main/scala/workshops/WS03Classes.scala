package workshops

// Een object is ook first class
object WS03Classes {

  class Breuk(t: Int, n: Int) {
    require(n != 0, "Noemer kan niet nul zijn")
    val g = Breuk.ggd(t.abs, n.abs)
    val teller = n.signum * (t / g)
    val noemer = n.signum * (n / g)

    def this(n: Int) = this(n, 1)

    override def equals(obj: Any): Boolean = {
      obj match {
        case m: Breuk => m.teller == this.teller && m.noemer == this.noemer
        case _ => false
      }
    }
  }


  object Breuk {
    def apply(t: Int, n: Int): Breuk = new Breuk(t, n)

    def apply(t: Int): Breuk = new Breuk(t, 1)

    def ggd(a: Int, b: Int): Int = {
      if (b == 0) a else ggd(b, a % b)
    }

  }

}
