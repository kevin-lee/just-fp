package just.fp.syntax

/**
  * @author Kevin Lee
  * @since 2019-09-21
  */
object OptionOps {
  final class ToSome[A](val a: A) extends AnyVal {
    def some: Option[A] = Some(a)
  }

  final class ToEither[B](val maybeB: Option[B]) extends AnyVal {
    def toEither[A](a: => A): Either[A, B] = maybeB match {
      case Some(b) => Right(b)
      case None => Left(a)
    }
  }
}

trait OptionSyntax {
  import OptionOps._

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitConversion"))
  implicit def implicitToSome[A](a: A): ToSome[A] = new ToSome[A](a)

  def none[A]: Option[A] = None

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitConversion"))
  implicit def implicitToEither[B](b: Option[B]): ToEither[B] = new ToEither[B](b)
}
