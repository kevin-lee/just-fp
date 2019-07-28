package kevinlee.fp.syntax

import kevinlee.fp.Equal

import scala.language.implicitConversions

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
object EqualSyntax {
  final class EqualOps[A] private[syntax] (val value: A) extends AnyVal {
    def ===(other: A)(implicit E: Equal[A]): Boolean =
      E.equal(value, other)
    def !==(other: A)(implicit E: Equal[A]): Boolean =
      !E.equal(value, other)
  }
}

@SuppressWarnings(Array("org.wartremover.warts.ImplicitConversion"))
trait EqualSyntax {
  import EqualSyntax._

  implicit def ToEqualOps[A](value: A): EqualOps[A] =
    new EqualOps(value)
}
