package models

case class Geld(val bedragInCenten: Int) {
  val centen = bedragInCenten % 100
  val euros = bedragInCenten /100

  def +(bedrag: Geld) = Geld(bedragInCenten + bedrag.bedragInCenten)
  def -(bedrag: Geld) = Geld(bedragInCenten = bedrag.bedragInCenten)
  def /(door: Int) = Geld(bedragInCenten / door)
  def *(maal: Int) = Geld(bedragInCenten * maal)
  def roundedDown() = bedragInCenten - bedragInCenten % 5

  override def toString: String ={
    val euros6w = f"${euros.abs}%6d"
    val centen2w= f"${centen.abs}%02d"
    val sign = if (bedragInCenten < 0) "-" else " "
    s" € ${euros6w},${centen2w}${sign}"
  }

}

object Geld {

  def apply(euros: Int, centen: Int): Geld = new Geld(100 * euros + centen)
//  def apply(bedragInCenten: Int) : Geld = new  Geld(bedragInCenten)

}