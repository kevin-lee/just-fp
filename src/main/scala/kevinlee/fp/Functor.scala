package kevinlee.fp

/**
  * @author Kevin Lee
  * @since 2019-03-16
  */
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]

  def lift[A, B](f: A => B): F[A] => F[B] = fa => map(fa)(f)

  trait FunctorLaw {
    /* Functors must preserve identity morphisms
     * fmap id = id
     */
    def identity[A](fa: F[A])(implicit FA: Equal[F[A]]): Boolean =
      FA.equal(map(fa)(scala.Predef.identity), fa)

    /* Functors preserve composition of morphisms
     * fmap (f . g)  ==  fmap f . fmap g
     */
    def composition[A, B, C](fa: F[A], f: B => C, g: A => B)(implicit FC: Equal[F[C]]): Boolean =
      FC.equal(map(fa)(f compose g), map(map(fa)(g))(f))
  }

  def functorLaw: FunctorLaw = new FunctorLaw {}
}

object Functor {

  implicit val listFunctor: Functor[List] = new Functor[List] {
    def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }

  implicit val vectorFunctor: Functor[Vector] = new Functor[Vector] {
    def map[A, B](fa: Vector[A])(f: A => B): Vector[B] = fa.map(f)
  }

}