package just.fp.syntax

import just.fp.Equal

/** @author Kevin Lee
  * @since 2019-07-28
  */

trait EqualSyntax {
  import EqualSyntax._

  implicit def ToEqualOps[A](eqLeft: A): EqualOps[A] =
    new EqualOps(eqLeft)
}
object EqualSyntax {
  final class EqualOps[A](private val eqLeft: A) extends AnyVal {
    def ===(eqRight: A)(implicit E: Equal[A]): Boolean =
      E.equal(eqLeft, eqRight)
    def !==(eqRight: A)(implicit E: Equal[A]): Boolean =
      !E.equal(eqLeft, eqRight)
  }
}
