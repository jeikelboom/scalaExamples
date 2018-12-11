package workshops

// Een object is ook first class
object WS03Classes {

  class Breuk(val t: Int, val n: Int) {
    require(n != 0, "Noemer kan niet nul zijn")
    val g = Breuk.ggd(t.abs, n.abs)
    val teller = n.signum * (t / g)
    val noemer = n.signum * (n / g)

    // auxilary constructor
    def this(n: Int) = this(n, 1)

    def + (andere: Breuk): Breuk = {
      val t1 = teller * andere.noemer + andere.teller * noemer
      val n1 = noemer * andere.noemer
      Breuk(t1, n1)
    }

    override def equals(obj: Any): Boolean = {
      obj match {
        case m: Breuk => m.teller == this.teller && m.noemer == this.noemer
        case _ => false
      }
    }
    def apply(str: String) = s"$str ${teller}/${noemer}"

    override def toString: String = s" ${teller}/${noemer} "
  }

  /**
    * Companion object voor Breuk
    *
    */
  object Breuk {
    // apply is de default methode: Breuk(8,3) is Breuk.apply(8, 3)
    def apply(t: Int, n: Int): Breuk = new Breuk(t, n)
    // Methode om van Int een breuk te maken
    // deze is implicit en wordt "vanzelf" aangeroepen bij een assignment
    implicit def apply(t: Int): Breuk = new Breuk(t, 1)

    def ggd(a: Int, b: Int): Int = {
      if (b == 0) a else ggd(b, a % b)
    }

  }


}
