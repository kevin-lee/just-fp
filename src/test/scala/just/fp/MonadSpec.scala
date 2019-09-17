package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-06-01
  */
object MonadSpec extends Properties {
  override def tests: List[Test] = List(
    property("testListMonadLaws", ListMonadLaws.laws)
  , property("testVectorMonadLaws", VectorMonadLaws.laws)
  , property("testFutureMonadLaws", FutureMonadLaws.laws)
  )

  object ListMonadLaws {
    def genList: Gen[List[Int]] = Gens.genList(Gens.genIntFromMinToMax, 20)

    def laws: Property =
      Specs.monadLaws.laws[List](
        genList
      , Gens.genIntFromMinToMax
      , Gens.genIntToInt
      , Gens.genAToMonadA(Gens.genIntToInt)
      )
  }

  object VectorMonadLaws {
    def genVector: Gen[Vector[Int]] = Gens.genVector(Gens.genIntFromMinToMax, 20)

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
    import scala.concurrent.duration._
    import scala.concurrent.{Await, Future}

    implicit def futureEqual: Equal[Future[Int]] = new Equal[Future[Int]] {
      @SuppressWarnings(Array("org.wartremover.warts.Equals"))
      override def equal(x: Future[Int], y: Future[Int]): Boolean =
        Await.result(x.flatMap(a => y.map(b => a == b)), 1.second)
    }

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
