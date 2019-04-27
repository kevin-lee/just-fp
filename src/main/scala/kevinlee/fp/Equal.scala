package kevinlee.fp

/**
  * @author Kevin Lee
  * @since 2019-04-22
  */
trait Equal[F] {
  def equal(x: F, y: F): Boolean

  def notEqual(x: F, y: F): Boolean = !equal(x, y)
}

object Equal {
  def equalA[A]: Equal[A] = new Equal[A] {
    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    def equal(x: A, y: A): Boolean = x == y
  }
}