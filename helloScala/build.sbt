import Dependencies._
import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion     := "2.12.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "helloScala",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += cats,
    libraryDependencies += json
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
