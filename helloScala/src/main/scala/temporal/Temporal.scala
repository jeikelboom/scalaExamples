package temporal

import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.{Instant, LocalDateTime, ZoneId, ZonedDateTime}
import java.util.Locale

import cats.{Applicative, Functor}

object Temporal {

  val BEGIN_OF_TIME = Instant.MIN
  val END_OF_TIME = Instant.MAX

  case class Timeline[A] (val a: A, val since: Instant)


  implicit val timelineApplicative : Applicative[Timeline] =
    new Applicative[Timeline] {

      override def pure[A](x: A): Timeline[A] = Timeline[A] (x, BEGIN_OF_TIME)

      override def ap[A, B](ff: Timeline[A => B])(fa: Timeline[A]): Timeline[B] =
        Timeline[B](ff.a(fa.a), ff.since.max(fa.since))
    }

  case class TimelineElement[A] (val interval: Interval, val value: A)

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
        Option(Interval(begin min other.begin, end min other.end))
      } else {
        Option.empty
      }

    def intersects(other: Interval) : Option[Interval] =
      if (overlaps(other)) {
        Option(Interval(begin max other.begin, end max other.end))
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
  def timestamp2String(instant: Instant) = fmt.format(ZonedDateTime.ofInstant(instant, zone))
}
