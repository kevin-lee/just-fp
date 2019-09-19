package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-06-02
  */
object FunctorSpec extends Properties {
  override def tests: List[Test] = List(
    property("testOptionFunctorLaws", OptionFunctorLaws.laws)
  , property("testEitherFunctorLaws", EitherFunctorLaws.laws)
  , property("testListFunctorLaws", ListFunctorLaws.laws)
  , property("testVectorFunctorLaws", VectorFunctorLaws.laws)
  , property("testFutureFunctorLaws", FutureFunctorLaws.laws)
  )

  object OptionFunctorLaws {
    def genList: Gen[Option[Int]] = Gens.genOption(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[Option](
        genList
        , Gens.genIntToInt
      )
  }

  object EitherFunctorLaws {
    def genEither: Gen[Either[String, Int]] = Gens.genEither(Gens.genUnicodeString ,Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[Either[String, ?]](
          genEither
        , Gens.genIntToInt
        )
  }

  object ListFunctorLaws {
    def genList: Gen[List[Int]] = Gens.genList(Gens.genIntFromMinToMax, 20)

    def laws: Property =
      Specs.functorLaws.laws[List](
          genList
        , Gens.genIntToInt
        )
  }

  object VectorFunctorLaws {
    def genVector: Gen[Vector[Int]] = Gens.genVector(Gens.genIntFromMinToMax, 20)

    def laws: Property =
      Specs.functorLaws.laws[Vector](
          genVector
        , Gens.genIntToInt
        )
  }

  object FutureFunctorLaws {
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.Future

    import Specs.FutureEqualInstance.futureEqual

    def genFuture: Gen[Future[Int]] = Gens.genFuture(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[Future](
          genFuture
        , Gens.genIntToInt
        )
  }

}
