package just.fp.syntax

import scala.language.implicitConversions

/**
  * @author Kevin Lee
  * @since 2019-09-21
  */
object OptionOps {
  final class ToSome[A](val a: A) extends AnyVal {
    def some: Option[A] = Some(a)
  }
}

trait OptionSyntax {
  import OptionOps._

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitConversion"))
  implicit def toSome[A](a: A): ToSome[A] = new ToSome[A](a)

  def none[A]: Option[A] = None
}
