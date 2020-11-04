package Temporal

import java.time.{LocalDateTime, OffsetDateTime, ZoneId, ZonedDateTime}
import java.time.format.DateTimeFormatter

import org.scalatest.{FlatSpec, Matchers}
import temporal.Temporal._
import temporal.Temporal.Interval

class OverlapsTest extends FlatSpec with Matchers {


  "1-5" should "overlap 3-7" in {
    val a: Interval  = Interval(timestamp("2019-04-01T00:00:00"), timestamp("2019-04-05T00:00:00"))
    val b: Interval  = Interval(timestamp("2019-04-03T00:00:00"), timestamp("2019-04-07T00:00:00"))
    a.overlaps(b) shouldEqual  true

  }

  def timestamp(value: String): ZonedDateTime =
    ZonedDateTime.of(LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME), ZoneId.of("Europe/Amsterdam"))

}
