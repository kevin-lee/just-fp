package kevinlee.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-06-01
  */
object MonadSpec extends Properties {
  override def tests: List[Test] = List(
    property("testListMoandLaws", ListMonadLaws.laws)
  )

  object ListMonadLaws {
    def genInts: Gen[Int] = Gens.genInts(Int.MinValue, Int.MaxValue)

    def genList: Gen[List[Int]] = Gens.genList(genInts, 20)

    def laws: Property =
      Specs.monadLaws.laws(
        genList
      , genInts
      , Gens.genIntToInt
      , Gens.genAToMonadA[List, Int](Gens.genIntToInt)
      )
  }

}
