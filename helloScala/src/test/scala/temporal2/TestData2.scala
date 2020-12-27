package temporal2

import java.time.LocalDate

import temporal2.DiscreteTemporal._

object TestData2 {

  val d1 :LocalDate = date(2001, 1,1)
  val d2 :LocalDate = date(2002, 2,2)
  val d2p :LocalDate = localDateEnum.pred(d2)
  val d2s :LocalDate = localDateEnum .succ(d2)
  val d3 :LocalDate = date(2003, 3,3)
  val d3p :LocalDate = localDateEnum.pred(d3)
  val d3s :LocalDate = localDateEnum .succ(d3)
  val d4 :LocalDate = date(2004, 4,4)
  val d4p :LocalDate = localDateEnum.pred(d4)
  val d4s :LocalDate = localDateEnum .succ(d4)
  val d5 :LocalDate = date(2005, 5,5)
  val d6 :LocalDate = date(2006, 5,5)
  val d7 :LocalDate = date(2007, 5,5)
  val d8 :LocalDate = date(2008, 5,5)
  val d9 :LocalDate = date(2009, 5,5)

  val v1 :String = "v01"
  val v2 :String = "v02"
  val v3 :String = "v03"
  val v4 :String = "v04"


}
