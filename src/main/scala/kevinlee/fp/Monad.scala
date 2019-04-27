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

}
