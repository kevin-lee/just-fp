package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-06-01
  */
object ApplicativeSpec extends Properties {
  override def tests: List[Test] = List(
    property("testOptionApplicativeLaws", OptionApplicativeLaws.laws)
  , property("testEitherApplicativeLaws", EitherApplicativeLaws.laws)
  , property("testListApplicativeLaws", ListApplicativeLaws.laws)
  , property("testVectorApplicativeLaws", VectorApplicativeLaws.laws)
  , property("testFutureApplicativeLaws", FutureApplicativeLaws.laws)
  )

  object OptionApplicativeLaws {
    def genList: Gen[Option[Int]] = Gens.genOption(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[Option](
        genList
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      )
  }

  object EitherApplicativeLaws {
    def genList: Gen[Either[String, Int]] = Gens.genEither(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[Either[String, *]](
        genList
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      )
  }

  object ListApplicativeLaws {
    def genList: Gen[List[Int]] = Gens.genList(Gens.genIntFromMinToMax, 20)

    def laws: Property =
      Specs.applicativeLaws.laws[List](
        genList
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      )
  }

  object VectorApplicativeLaws {
    def genVector: Gen[Vector[Int]] = Gens.genVector(Gens.genIntFromMinToMax, 20)

    def laws: Property =
      Specs.applicativeLaws.laws[Vector](
        genVector
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      )
  }

  /* NOTE: Future complies with the laws only for the success cases.
   * It does not for failure cases.
   */
  object FutureApplicativeLaws {
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.Future

    import just.fp.testing.EqualUtil.FutureEqualInstance.futureEqual

    def genFuture: Gen[Future[Int]] = Gens.genFuture(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[Future](
          genFuture
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        )
  }

}
