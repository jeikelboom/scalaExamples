package models.domein

object Constants {
  val SPEK = Artikel(1, "10", "Ham", ArtikelGroep.Vleeswaren, Bedrag(2, 75))
  val WORST = Artikel(1, "11", "Salzburger Worst", ArtikelGroep.Vleeswaren, Bedrag(4, 35))
  val HAM = Artikel(1, "12", "Ham", ArtikelGroep.Vleeswaren, Bedrag(1, 95))
  val COLA = Artikel(1, "13", "Cola", ArtikelGroep.Frisdrank, Bedrag(2, 55))
  val SINAS = Artikel(1, "14", "Sinas", ArtikelGroep.Frisdrank, Bedrag(2, 15))
  val ARTIKELEN_LIJST = List(SPEK, WORST,HAM, COLA, SINAS)

  val ARTIKEL_NIET_GEVONDEN = FoutMelding("artikel.niet.gevonden")


}
