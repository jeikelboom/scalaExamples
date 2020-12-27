package temporal2

import java.time.LocalDate

import cats.collections.Range
import temporal2.DiscreteTemporal._

object TestData2 {

  val d1 = date(2001, 1,1)
  val d2 = date(2002, 2,2)
  val d2p = localDateEnum.pred(d2)
  val d2s = localDateEnum .succ(d2)
  val d3 = date(2003, 3,3)
  val d3p = localDateEnum.pred(d3)
  val d3s = localDateEnum .succ(d3)
  val d4 = date(2004, 4,4)
  val d4p = localDateEnum.pred(d4)
  val d4s = localDateEnum .succ(d4)
  val d5 = date(2005, 5,5)
  val d6 = date(2006, 5,5)
  val d7 = date(2007, 5,5)
  val d8 = date(2008, 5,5)
  val d9 = date(2009, 5,5)

  val v1 = "v01"
  val v2 = "v02"
  val v3 = "v03"
  val v4 = "v04"


}
