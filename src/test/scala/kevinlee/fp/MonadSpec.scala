package kevinlee.fp

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
  )

  object ListMonadLaws {
    def genInts: Gen[Int] = Gens.genInts(Int.MinValue, Int.MaxValue)

    def genList: Gen[List[Int]] = Gens.genList(genInts, 20)

    def laws: Property =
      Specs.monadLaws.laws[List](
        genList
      , genInts
      , Gens.genIntToInt
      , Gens.genAToMonadA(Gens.genIntToInt)
      )
  }

  object VectorMonadLaws {
    def genInts: Gen[Int] = Gens.genInts(Int.MinValue, Int.MaxValue)

    def genVector: Gen[Vector[Int]] = Gens.genVector(genInts, 20)

    def laws: Property =
      Specs.monadLaws.laws[Vector](
        genVector
      , genInts
      , Gens.genIntToInt
      , Gens.genAToMonadA(Gens.genIntToInt)
      )
  }

}
