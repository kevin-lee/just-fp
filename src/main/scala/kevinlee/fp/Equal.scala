package kevinlee.fp

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
    def symmetry(x: F, y: F): Boolean = equal(x, y) == equal(y, x)

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
      notEqual(x, y) == !equal(x, y)
  }
}

object Equal extends ListEqualInstance with VectorEqualInstance {
  def equalA[A]: Equal[A] = new Equal[A] {
    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    def equal(x: A, y: A): Boolean = x == y
  }
}

trait ListEqualInstance {
  implicit def listEqual[A]: Equal[List[A]]= Equal.equalA[List[A]]
}

trait VectorEqualInstance {
  implicit def vectorEqual[A]: Equal[Vector[A]]= Equal.equalA[Vector[A]]
}
