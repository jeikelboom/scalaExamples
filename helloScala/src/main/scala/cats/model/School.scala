package cats.model

import java.time.LocalDate

import cats.model.Model._

object Model {

  case class Student(val studentNId: Int, val name: String)
  case class Instructor(val teacherId: Int, name: String)
  case class Course(val courseId: Int, val title: String, val days: Int)
  case class Prerequisite(val prereqId: Int, val courseId: Int, val prereq: Int)
  case class Offering(val scheduleId: Int, val courseId: Int, val startDate: LocalDate, val instructorId: Int)
  case class Enrollment(val enrolId: Int, val studentId: Int, val scheduleId: Int)
  case class Diploma(val diplomaId: Int, val studentId: Int, val courseId: Int, val grade: Int)

  case class School(
        val students: List[Student],
        val instructors: List[Instructor],
        val courses: List[Course],
        val prerequisites: List[Prerequisite],
        val offerings: List[Offering],
        val enrollments: List[Enrollment],
        val diplomas: List[Diploma])
}

object Myschool {
  val students = List(
    Student(10, "Fred"),
    Student(11, "Betty"),
    Student(12, "Donald"),
    Student(13, "Frodo")
  )
  val instructors = List(
    Instructor(20, "John"),
    Instructor(21, "Phil"),
    Instructor(22, "Michelle")
  )
  val courses = List(
    Course(30, "Intro programming", 3),
    Course(31, "Java 1 basics", 5),
    Course(32, "Java 2 object orientation", 5),
    Course(33, "Java 3 visual programming", 3),
    Course(34, "Scala for Java Programmers", 5),
    Course(35, "Advanced Scale", 5),
    Course(36, "English", 20)
  )
  val prerequisites = List(
    Prerequisite(41, 35, 34),
    Prerequisite(42, 34, 32),
    Prerequisite(43, 33, 32),
    Prerequisite(44, 32, 31),
    Prerequisite(45, 31, 30)
  )
  val offerings = List(
    Offering(51, 30, LocalDate.of(2020, 3, 2), 20),
    Offering(52, 30, LocalDate.of(2020, 6, 1), 20),
    Offering(53, 30, LocalDate.of(2020, 9, 7), 20),
    Offering(54, 30, LocalDate.of(2020, 12, 7), 20),

    Offering(55, 31, LocalDate.of(2020, 3, 9), 22),
    Offering(56, 31, LocalDate.of(2020, 6, 8), 21),
    Offering(57, 31, LocalDate.of(2020, 9, 14), 21),
    Offering(58, 31, LocalDate.of(2020, 12, 14), 22),

    Offering(60, 32, LocalDate.of(2020, 3, 16), 22),
    Offering(62, 32, LocalDate.of(2020, 9, 21), 21),

    Offering(60, 33, LocalDate.of(2020, 3, 16), 22),
    Offering(62, 33, LocalDate.of(2020, 9, 21), 21),
  )

  val enrollments = List(
    Enrollment(71, 10, 51),
    Enrollment(72, 10, 56),
    Enrollment(73, 10, 60),

    Enrollment(74, 11, 51),
    Enrollment(75, 11, 56),
    Enrollment(76, 11, 60)
  )

  val diplomas: List[Diploma] = List()

  val myschool = School(students, instructors, courses, prerequisites, offerings, enrollments, diplomas)

}


