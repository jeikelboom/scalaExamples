package workshops

import java.time.{Instant, OffsetDateTime, ZonedDateTime}

import cats.Applicative
import temporal.Temporal.{BEGIN_OF_TIME, TimelineElement}

object WS14implicitly {

  abstract class Timescale[T] extends Ordering[T]{
    val min: T
    val max: T

  }

  implicit val instantTimescale: Timescale[Instant] = new Timescale[Instant] {
    override val min: Instant = Instant.MIN
    override val max: Instant = Instant.MAX
    override def compare(x: Instant, y: Instant): Int = if (x.isBefore(y))  {-1} else if (y.isBefore(x)) {1} else 0
  }

  implicit val offsetTimescale: Timescale[OffsetDateTime] = new Timescale[OffsetDateTime] {
    override val min: OffsetDateTime = OffsetDateTime.MIN
    override val max: OffsetDateTime = OffsetDateTime.MAX
    override def compare(x: OffsetDateTime, y: OffsetDateTime): Int = if (x.isBefore(y))  {-1} else if (y.isBefore(x)) {1} else 0
  }

  def getMax[T: Timescale]: T = implicitly[Timescale[T]].max

  case class TempThingy1[T, A](val t: T, val v: A){
  }





}
