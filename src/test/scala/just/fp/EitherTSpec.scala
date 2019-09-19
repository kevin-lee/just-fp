package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-09-19
  */
object EitherTSpec extends Properties {
  override def tests: List[Test] = List(
      property("testEitherTFunctorLaws", EitherTFunctorLaws.laws)
    , property("testEitherTApplicativeLaws", EitherTApplicativeLaws.laws)
    , property("testEitherTMonadLaws", EitherTMonadLaws.laws)
    , property("testEitherTIdFunctorLaws", EitherTIdFunctorLaws.laws)
    , property("testEitherTIdApplicativeLaws", EitherTIdApplicativeLaws.laws)
    , property("testEitherTIdMonadLaws", EitherTIdMonadLaws.laws)
    )

  object EitherTFunctorLaws {
    type EitherTOption[A] = EitherT[Option, String, A]

    def genEitherT: Gen[EitherTOption[Int]] = Gens.genEitherT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[EitherTOption](
          genEitherT
        , Gens.genIntToInt
        )
  }

  object EitherTApplicativeLaws {
    type EitherTOption[A] = EitherT[Option, String, A]

    def genEitherT: Gen[EitherTOption[Int]] = Gens.genEitherT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[EitherTOption](
          genEitherT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        )
  }

  object EitherTMonadLaws {
    type EitherTOption[A] = EitherT[Option, String, A]

    def genEitherT: Gen[EitherTOption[Int]] = Gens.genEitherT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[EitherTOption](
          genEitherT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        , Gens.genAToMonadA(Gens.genIntToInt)
        )
  }

  object EitherTIdFunctorLaws {
    type EitherTId[A] = EitherT[Id, String, A]

    def genEitherT: Gen[EitherTId[Int]] = Gens.genEitherT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[EitherTId](
          genEitherT
        , Gens.genIntToInt
        )
  }

  object EitherTIdApplicativeLaws {
    type EitherTId[A] = EitherT[Id, String, A]

    def genEitherT: Gen[EitherTId[Int]] = Gens.genEitherT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[EitherTId](
          genEitherT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        )
  }

  object EitherTIdMonadLaws {
    type EitherTId[A] = EitherT[Id, String, A]

    def genEitherT: Gen[EitherTId[Int]] = Gens.genEitherT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[EitherTId](
          genEitherT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        , Gens.genAToMonadA(Gens.genIntToInt)
        )
  }

}
