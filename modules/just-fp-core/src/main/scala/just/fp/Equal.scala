package just.fp

import just.fp.instances._

/**
  * @author Kevin Lee
  * @since 2019-04-22
  */
trait Equal[F] {

  def equal(x: F, y: F): Boolean

  def notEqual(x: F, y: F): Boolean = !equal(x, y)

  trait EqualLaw {
    /* Reflexivity
     * x == x = True
     */
    def reflexivity(x: F): Boolean = equal(x, x)

    /* Symmetry
     * x == y = y == x
     */
    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    def symmetry(x: F, y: F): Boolean = equal(x, y) == equal(y, x)  //scalafix:ok

    /* Transitivity
     * if x == y && y == z = True, then x == z = True
     */
    def transitivity(x: F, y: F, z: F): Boolean =
      !(equal(x, y) && equal(y, z)) || equal(x, z)

    /*
     * Substitutivity
     * if x == y = True and f is a "public" function whose return type is an instance of Eq, then f x == f y = True
     */
    def substitutivity(x: F, y: F, f: F => F): Boolean =
      !equal(x, y) || equal(f(x), f(y))

    /* Negation
     * x /= y = not (x == y)
     */
    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    def negation(x: F, y: F): Boolean =
      notEqual(x, y) == !equal(x, y)  //scalafix:ok
  }
}

object Equal
  extends NatualEqual
    with BooleanEqualInstance
    with IntEqualInstance
    with ShortEqualInstance
    with LongEqualInstance
    with ByteEqualInstance
    with CharEqualInstance
    with FloatEqualInstance
    with DoubleEqualInstance
    with StringEqualInstance
    with BigIntEqualInstance
    with BigDecimalEqualInstance
    with OptionEqualInstance
    with EitherEqualInstance
    with ListEqualInstance
    with VectorEqualInstance {

  @inline final def apply[F : Equal]: Equal[F] = implicitly[Equal[F]]
}

trait NatualEqual {
  def equalA[A]: Equal[A] = new Equal[A] {
    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    def equal(x: A, y: A): Boolean = x == y  //scalafix:ok
  }
}
object NatualEqual extends NatualEqual

trait OptionEqualInstance {
  implicit def optionEqual[A]: Equal[Option[A]] = NatualEqual.equalA[Option[A]]
}

trait EitherEqualInstance {
  implicit def eitherEqual[A, B]: Equal[Either[A, B]] = NatualEqual.equalA[Either[A, B]]
}

trait ListEqualInstance {
  implicit def listEqual[A]: Equal[List[A]]= NatualEqual.equalA[List[A]]
}

trait VectorEqualInstance {
  implicit def vectorEqual[A]: Equal[Vector[A]]= NatualEqual.equalA[Vector[A]]
}
