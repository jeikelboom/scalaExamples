package temporal2

import java.time.Instant

import temporal2.Intervals.{Interval, read}

object TestData {

  val t0 :Instant = Instant.MIN
  val t9 :Instant = Instant.MAX
  val t1 :Instant = read("2011-04-01 10:10")
  val t2 :Instant = read("2012-04-01 11:11")
  val t3 :Instant = read("2013-04-01 12:12")
  val t4 :Instant = read("2014-04-01 13:13")
  val t5 :Instant = read("2015-04-01 14:14")
  val t6 :Instant = read("2016-04-23 11:34")
  val i01: Interval = Interval(t0, t1)
  val i02: Interval = Interval(t0, t2)
  val i03: Interval = Interval(t0, t3)
  val i04: Interval = Interval(t0, t4)
  val i05: Interval = Interval(t0, t5)
  val i09: Interval = Interval(t0, t9)
  val i12: Interval = Interval(t1, t2)
  val i13: Interval = Interval(t1, t3)
  val i14: Interval = Interval(t1, t4)
  val i15: Interval = Interval(t1, t5)
  val i19: Interval = Interval(t1, t9)
  val i23: Interval = Interval(t2, t3)
  val i24: Interval = Interval(t2, t4)
  val i25: Interval = Interval(t2, t5)
  val i29: Interval = Interval(t2, t9)
  val i34: Interval = Interval(t3, t4)
  val i35: Interval = Interval(t3, t5)
  val i39: Interval = Interval(t3, t9)
  val i45: Interval = Interval(t4, t5)
  val i49: Interval = Interval(t4, t9)

  object Personnel{
    case class Employee(name: String)
    case class Address(street: String, city: String)
    case class Department(departmentName: String)
    case class Employment(employee: Employee, address: Address,department: Department, function: Function)
    case class Function(title: String)
    val emp1 = Employee("Anna Nas")
    val add1 = Address("Bakerstreet 12", "London")
    val add2 = Address("KaasMarkt 45", "Alkmaar")
    val add3 = Address("AchterStraat 16", "Alkmaar")
    val dep1 = Department("HRM")
    val dep2 = Department("Silly walks")
    val dep3 = Department("IT")
    val fnc1 = Function("Recruiter")
    val fnc2 = Function("Junior programmer")
    val fnc3 = Function("Recruiter")
  }


}
