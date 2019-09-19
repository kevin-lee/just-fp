package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-06-02
  */
object FunctorSpec extends Properties {
  override def tests: List[Test] = List(
    property("testEitherFunctorLaws", EitherFunctorLaws.laws)
  , property("testListFunctorLaws", ListFunctorLaws.laws)
  , property("testVectorFunctorLaws", VectorFunctorLaws.laws)
  , property("testFutureFunctorLaws", FutureFunctorLaws.laws)
  )

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
    import scala.concurrent.duration._
    import scala.concurrent.{Await, Future}

    implicit def futureEqual: Equal[Future[Int]] = new Equal[Future[Int]] {
      @SuppressWarnings(Array("org.wartremover.warts.Equals"))
      override def equal(x: Future[Int], y: Future[Int]): Boolean =
        Await.result(x.flatMap(a => y.map(b => a == b)), 1.second)
    }

    def genFuture: Gen[Future[Int]] = Gens.genFuture(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[Future](
          genFuture
        , Gens.genIntToInt
        )
  }

}
