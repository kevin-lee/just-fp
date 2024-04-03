package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-06-01
  */
object MonadSpec extends Properties {
  override def tests: List[Test] = List(
    property("testOptionMonadLaws", OptionMonadLaws.laws)
  , property("testEitherMonadLaws", EitherMonadLaws.laws)
  , property("testListMonadLaws", ListMonadLaws.laws)
  , property("testVectorMonadLaws", VectorMonadLaws.laws)
  , property("testFutureMonadLaws", FutureMonadLaws.laws)
  )

  object OptionMonadLaws {
    def genList: Gen[Option[Int]] = Gens.genOption(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[Option](
        genList
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      , Gens.genAToMonadA(Gens.genIntToInt)
      )
  }

  object EitherMonadLaws {
    def genList: Gen[Either[String, Int]] = Gens.genEither(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[Either[String, *]](
        genList
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      , Gens.genAToMonadA[Either[String, *], Int](Gens.genIntToInt)
      )
  }

  object ListMonadLaws {
    def genList: Gen[List[Int]] = Gens.genList(Gens.genIntFromMinToMax, 0, 20)

    def laws: Property =
      Specs.monadLaws.laws[List](
        genList
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      , Gens.genAToMonadA(Gens.genIntToInt)
      )
  }

  object VectorMonadLaws {
    def genVector: Gen[Vector[Int]] = Gens.genVector(Gens.genIntFromMinToMax, 0, 20)

    def laws: Property =
      Specs.monadLaws.laws[Vector](
        genVector
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      , Gens.genAToMonadA(Gens.genIntToInt)
      )
  }

  /* NOTE: Future complies with the laws only for the success cases.
   * It does not for failure cases.
   */
  object FutureMonadLaws {
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.Future

    import just.fp.testing.EqualUtil.FutureEqualInstance.futureEqual

    def genFuture: Gen[Future[Int]] = Gens.genFuture(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[Future](
          genFuture
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        , Gens.genAToMonadA(Gens.genIntToInt)
        )
  }

}
