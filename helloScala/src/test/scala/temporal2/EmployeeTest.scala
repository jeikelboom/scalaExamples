package temporal2

import org.scalatest.{FlatSpec, Matchers}

import temporal2.Temporal2._
import TestData._
import TestData.Personnel._
import cats.Applicative
class EmployeeTest extends FlatSpec with Matchers{

  val addressHistory: TimeLine[Address] = TimeLine(List())
    .append(i13, add1)
    .append(i34, add2)
    .append(i49, add3)
  val departmentHistory: TimeLine[Department] = TimeLine(List())
    .append(i14, dep1)
    .append(i49, dep2)
  val functionHistory: TimeLine[Function] = TimeLine(List())
    .append(i12, fnc1)
    .append(i23, fnc2)
    .append(i34, fnc3)
    .append(i49, fnc2)
  val anna: TimeLine[Employee] = TimeLine(List()).append(i19, emp1)


  def pure[A](x: A): TimeLine[A] = Applicative[TimeLine].pure[A](x)

  val cityHistory: TimeLine[String] = Applicative[TimeLine].ap(pure[Address => String](_.city))(addressHistory)
  val streetHistory: TimeLine[String] =
    Applicative[TimeLine].ap(pure[Address => String](_.street))(addressHistory)
  val addressHistory2: TimeLine[Address] =
    Applicative[TimeLine].ap2(pure[(String, String) =>Address]((s,c) => Address(s,c)))(streetHistory, cityHistory)

  def toEmployment(employee: Employee, address: Address,department: Department, function: Function): Employment =
    Employment(employee, address, department, function)

  val employementHistory: TimeLine[Employment] =
    Applicative[TimeLine].ap4(pure[(Employee, Address, Department, Function) =>Employment](toEmployment))(
      anna, addressHistory, departmentHistory, functionHistory)

  println(addressHistory)
  println(departmentHistory)
  println(functionHistory)
  println(cityHistory)
  println(cityHistory)
  println(employementHistory)

  "addressHistory" should "be join of parts" in {
    addressHistory shouldEqual addressHistory2
  }

  "addressHistory" should "projection of employmentHistory" in {
    val projection: TimeLine[Address] =
      Applicative[TimeLine].ap(pure[Employment => Address](_.address))(employementHistory)
    projection shouldEqual addressHistory
  }

  "departmentHistory" should "projection of employmentHistory" in {
    val projection: TimeLine[Department] =
      Applicative[TimeLine].ap(pure[Employment => Department](_.department))(employementHistory)
    projection shouldEqual departmentHistory
  }

  "functionHistory" should "projection of employmentHistory" in {
    val projection: TimeLine[Function] =
      Applicative[TimeLine].ap(pure[Employment => Function](_.function))(employementHistory)
    projection shouldEqual functionHistory
  }

}
