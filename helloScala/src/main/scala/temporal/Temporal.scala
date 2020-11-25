package temporal

import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId, ZonedDateTime}
import cats.Applicative

object Temporal {

  val BEGIN_OF_TIME: Instant = Instant.MIN
  val END_OF_TIME: Instant = Instant.MAX


  case class TimelineElement[A] (val since: Instant, val a: A ) {

  }

  abstract class Timescale[T] extends Ordering[T]{
    val minimum: T
    val maximum: T

  }

  implicit val instantTimescale: Timescale[Instant] = new Timescale[Instant] {
    override val minimum: Instant = Instant.MIN
    override val maximum: Instant = Instant.MAX

    override def compare(x: Instant, y: Instant): Int =
      if (x.isBefore(y)) { -1} else if (x.isAfter(y)) { 1 } else {0}
  }


  implicit def timelineApplicative(implicit evidence: Timescale[Instant]) : Applicative[TimelineElement] =
    new Applicative[TimelineElement] {

      override def pure[A](x: A): TimelineElement[A] = TimelineElement[A] (evidence.minimum, x)

      override def ap[A, B](ff: TimelineElement[A => B])(fa: TimelineElement[A]): TimelineElement[B] =
        TimelineElement[B](evidence.max(ff.since, fa.since), ff.a(fa.a))
    }

//  case class TimelineElement[A] (val interval: Interval, val value: A)

  // describes interval begin <= x < end (semi-open)
  case class Interval(val begin: Instant, val end: Instant) {

    def overlaps(other: Interval) : Boolean =
      other.end > begin && end > other.begin

    def meets(other: Interval) : Boolean =
      end == other.begin

    def before(other: Interval) : Boolean =
      end < other.begin

    def after(other: Interval) : Boolean =
      begin > other.end

    def join(other: Interval) : Option[Interval] =
      if (overlaps(other) || meets(other) || other.meets(this)) {
        Option(Interval(begin min other.begin, end max other.end))
      } else {
        Option.empty
      }

    def intersects(other: Interval) : Option[Interval] =
      if (overlaps(other)) {
        Option(Interval(begin max other.begin, end min other.end))
      } else {
        Option.empty
      }
  }

  implicit class InstantOrdering(instant: Instant) extends Ordered[Instant] {
    override def compare(that: Instant): Int =
      if (instant.isBefore(that))  {-1} else if (that.isBefore(instant)) {1} else 0
    def max(other: Instant): Instant = if (instant > other) {instant} else other
    def min(other: Instant): Instant = if (instant < other) {instant} else other
  }

  val fmt: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
  val zone: ZoneId =   ZoneId.of("Europe/Amsterdam")
  def timestamp(value: String): Instant = Instant.from( ZonedDateTime.of(LocalDateTime.parse(value, fmt), zone))
  def timestamp2String(instant: Instant) = {
    instant match {
      case Instant.MAX => "Doomsday"
      case Instant.MIN => "Big bang"
      case _           => fmt.format(ZonedDateTime.ofInstant(instant, zone))
    }
  }
}
