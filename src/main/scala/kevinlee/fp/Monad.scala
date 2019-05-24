package kevinlee.fp

/**
  * @author Kevin Lee
  * @since 2019-03-16
  */
trait Monad[M[_]] extends Applicative[M] {

  override def map[A, B](ma: M[A])(f: A => B): M[B] =
    flatMap(ma)(a => pure(f(a)))

  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]

  override def ap[A, B](ma: => M[A])(f: => M[A => B]): M[B] =
    flatMap(ma) { a =>
      map(f)(fab => fab(a))
    }

  trait MonadLaw extends ApplicativeLaw {
    /*
     * return a >>= f === f a
     */
    def leftIdentity[A, B](a: A, f: A => M[B])(implicit MB: Equal[M[B]]): Boolean =
      MB.equal(flatMap(pure(a))(f), f(a))

    /*
     * m >>= return === m
     */
    def rightIdentity[A](a: M[A])(implicit MA: Equal[M[A]]): Boolean =
      MA.equal(flatMap(a)(pure(_: A)), a)

    /*
     * (m >>= f) >>= g === m >>= (\x -> f x >>= g)
     */
    def associativity[A, B, C](a: M[A], f: A => M[B], g: B => M[C])(implicit MC: Equal[M[C]]): Boolean =
      MC.equal(flatMap(flatMap(a)(f))(g), flatMap(a)(x => flatMap(f(x))(g)))
  }
}
