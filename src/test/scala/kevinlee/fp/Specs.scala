package kevinlee.fp

import hedgehog._

/**
  * @author Kevin Lee
  * @since 2019-06-01
  */
object Specs {

  object functorLaws {
    def laws[F[_]](
        genM: Gen[F[Int]]
      , genF: Gen[Int => Int]
      )(implicit functor: Functor[F]
      , eqM: Equal[F[Int]]
      ): Property = for {
      m <- genM.log("m: F[Int]")
      f <- genF.log("f: Int => Int")
      f2 <- genF.log("f2: Int => Int")
    } yield {
      Result.all(List(
        (functor.functorLaw.identity[Int](m) ==== true)
          .log("functorLaw.identity")
      , (functor.functorLaw.composition[Int, Int, Int](m, f, f2) ==== true)
          .log("functorLaw.composition")
      ))
    }
  }

  object monadLaws {
    def laws[M[_]](
        genM: Gen[M[Int]]
      , genInt: Gen[Int]
      , genF: Gen[Int => Int]
      , genFm: Gen[Int => M[Int]]
      )(implicit monad: Monad[M]
      , eqM: Equal[M[Int]]
      ): Property = for {
      m <- genM.log("m: M[Int]")
      x <- genInt.log("x: Int")
      f <- genF.log("f: Int => Int")
      f2 <- genF.log("f2: Int => Int")
      fm <- genFm.log("fm: Int => M[Int]")
      fm2 <- genFm.log("fm2: Int => M[Int]")
    } yield {
      Result.all(List(
        (monad.functorLaw.identity[Int](m) ==== true)
          .log("functorLaw.identity")
      , (monad.functorLaw.composition[Int, Int, Int](m, f, f2) ==== true)
          .log("functorLaw.composition")
      , (monad.applicativeLaw.identityAp[Int](m) ==== true)
          .log("applicativeLaw.identityAp")
      , (monad.applicativeLaw.homomorphism[Int, Int](f, x) ==== true)
          .log("applicativeLaw.homomorphism")
      , (monad.applicativeLaw.interchange[Int, Int](x, monad.pure(f)) ==== true)
          .log("applicativeLaw.interchange")
      , (monad.applicativeLaw.compositionAp[Int, Int, Int](m, monad.pure(f), monad.pure(f2)) ==== true)
          .log("applicativeLaw.compositionAp")
      , (monad.monadLaw.leftIdentity[Int, Int](x, fm) ==== true)
          .log("monadLaw.leftIdentity")
      , (monad.monadLaw.rightIdentity[Int](m) ==== true)
          .log("monadLaw.rightIdentity")
      , (monad.monadLaw.associativity[Int, Int, Int](m, fm, fm2) ==== true)
          .log("monadLaw.associativity")
      ))
    }
  }

}
