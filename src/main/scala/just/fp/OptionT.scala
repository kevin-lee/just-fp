package just.fp

import just.fp.syntax.{implicitToSome, none}

/**
  * @author Kevin Lee
  * @since 2019-11-29
  */
final case class OptionT[F[_], A](run: F[Option[A]]) {
  def map[B](f: A => B)(implicit F: Functor[F]): OptionT[F, B] =
    OptionT(F.map(run)(_.map(f)))

  def ap[B](fa: OptionT[F, A => B])(implicit F: Applicative[F]): OptionT[F, B] =
    OptionT(
      F.ap(run)(F.map(fa.run) {
        case Some(ab) =>
          {
            case Some(a) =>
              ab(a).some
            case None =>
              none[B]
          }
        case None =>
          _ => none[B]
      })
    )

  def flatMap[B](f: A => OptionT[F, B])(implicit M: Monad[F]): OptionT[F, B] =
    OptionT(
      M.flatMap(run) {
        case Some(a) =>
          f(a).run
        case None =>
          M.pure(none[B])
      }
    )

  def isDefined(implicit F: Functor[F]): F[Boolean] =
    F.map(run)(_.isDefined)

  def isEmpty(implicit F: Functor[F]): F[Boolean] =
    F.map(run)(_.isEmpty)

}

object OptionT extends OptionTMonadInstance {
  def pure[F[_]: Applicative, A](a: => A): OptionT[F, A] =
    OptionT(Applicative[F].pure(a.some))

  def some[F[_]: Applicative, A](a: => A): OptionT[F, A] = pure(a)

  def none[F[_]: Applicative, A]: OptionT[F, A] =
    OptionT(Applicative[F].pure(None))
}

private trait OptionTFunctor[F[_]] extends Functor[OptionT[F, *]] {
  implicit def F: Functor[F]

  override def map[A, B](fa: OptionT[F, A])(f: A => B): OptionT[F, B] =
    fa.map(f)(F)
}

private trait OptionTApplicative[F[_]] extends Applicative[OptionT[F, *]] with OptionTFunctor[F] {
  implicit def F: Applicative[F]

  override def pure[A](a: => A): OptionT[F, A] = OptionT(F.pure(a.some))

  override def ap[A, B](fa: => OptionT[F, A])(fab: => OptionT[F, A => B]): OptionT[F, B] =
    fa.ap(fab)(F)
}

private trait OptionTMonad[F[_]] extends Monad[OptionT[F, *]] with OptionTApplicative[F] {
  implicit def F: Monad[F]
}

sealed abstract class OptionTFunctorInstance {
  implicit def OptionTFunctor[F[_]](implicit F0: Functor[F]): Functor[OptionT[F, *]] = new OptionTFunctor[F] {
    override implicit val F: Functor[F] = F0
  }
}

sealed abstract class OptionTApplicativeInstance extends OptionTFunctorInstance {
  implicit def OptionTApplicative[F[_]](implicit F0: Applicative[F]): Applicative[OptionT[F, *]] =
    new OptionTApplicative[F] {
      override implicit val F: Applicative[F] = F0
    }
}

sealed abstract class OptionTMonadInstance extends OptionTApplicativeInstance {

  implicit def OptionTMonad[F[_]](implicit F0: Monad[F]): Monad[OptionT[F, *]] = new OptionTMonad[F] {

    override implicit val F: Monad[F] = F0

    override def flatMap[A, B](ma: OptionT[F, A])(f: A => OptionT[F, B]): OptionT[F, B] =
      ma.flatMap(f)(F)

  }

  implicit def OptionTEqual[F[_], A](implicit EQ: Equal[F[Option[A]]]): Equal[OptionT[F, A]] =
    new Equal[OptionT[F, A]] {
      override def equal(x: OptionT[F, A], y: OptionT[F, A]): Boolean =
        EQ.equal(x.run, y.run)
    }

}