package just.fp.syntax

import just.fp.SemiGroup

import scala.language.implicitConversions

/**
  * @author Kevin Lee
  * @since 2019-09-18
  */
object SemiGroupSyntax {
  final class SemiGroupOps[A: SemiGroup] private[syntax](val a1: A) {
    def |+|(a2: A): A = implicitly[SemiGroup[A]].append(a1, a2)

    def mappend(a2: A): A = implicitly[SemiGroup[A]].append(a1, a2)
  }
}

trait SemiGroupSyntax {
  import SemiGroupSyntax._

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitConversion"))
  implicit def ToSemiGroupOps[A: SemiGroup](a: A): SemiGroupOps[A] = new SemiGroupOps(a)

}