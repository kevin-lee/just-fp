package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-09-19
  */
object OptionTSpec extends Properties {

  type EitherStringOr[A] = Either[String, A]
  type OptionTEither[A] = OptionT[EitherStringOr, A]
  type OptionTId[A] = OptionT[Id, A]

  override def tests: List[Test] = List(
      property("testOptionTFunctorLaws", OptionTFunctorLaws.laws)
    , property("testOptionTApplicativeLaws", OptionTApplicativeLaws.laws)
    , property("testOptionTMonadLaws", OptionTMonadLaws.laws)
    , property("testOptionTIdFunctorLaws", OptionTIdFunctorLaws.laws)
    , property("testOptionTIdApplicativeLaws", OptionTIdApplicativeLaws.laws)
    , property("testOptionTIdMonadLaws", OptionTIdMonadLaws.laws)
    )

  object OptionTFunctorLaws {

    def genOptionT: Gen[OptionTEither[Int]] = Gens.genOptionT[EitherStringOr, Int](Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[OptionTEither](
          genOptionT
        , Gens.genIntToInt
        )
  }

  object OptionTApplicativeLaws {

    def genOptionT: Gen[OptionTEither[Int]] = Gens.genOptionT[EitherStringOr, Int](Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[OptionTEither](
          genOptionT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        )
  }

  object OptionTMonadLaws {

    def genOptionT: Gen[OptionTEither[Int]] = Gens.genOptionT[EitherStringOr, Int](Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[OptionTEither](
          genOptionT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        , Gens.genAToMonadA(Gens.genIntToInt)
        )
  }

  object OptionTIdFunctorLaws {

    def genOptionT: Gen[OptionTId[Int]] = Gens.genOptionT(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[OptionTId](
          genOptionT
        , Gens.genIntToInt
        )
  }

  object OptionTIdApplicativeLaws {

    def genOptionT: Gen[OptionTId[Int]] = Gens.genOptionT(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[OptionTId](
          genOptionT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        )
  }

  object OptionTIdMonadLaws {

    def genOptionT: Gen[OptionTId[Int]] = Gens.genOptionT(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[OptionTId](
          genOptionT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        , Gens.genAToMonadA(Gens.genIntToInt)
        )
  }

}
