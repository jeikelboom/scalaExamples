import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.1.0"
  lazy val cats_collections =  "org.typelevel" %% "cats-collections-core" % "0.7.0"
  lazy val json =  "com.typesafe.play" % "play-json_2.12" % "2.8.0"
}
