package kevinlee.fp.testing

object TestPredef {

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  implicit final class AnyEquals[A](val self: A) extends AnyVal {
    def ===(other: A): Boolean = self == other
    def !==(other: A): Boolean = self != other
  }

}