package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-09-19
  */
object WriterTSpec extends Properties {

  override def tests: List[Test] = List(
    property("testWriterTFunctorLaws", WriterTFunctorLaws.laws)
  , property("testWriterTApplicativeLaws", WriterTApplicativeLaws.laws)
  , property("testWriterTMonadLaws", WriterTMonadLaws.laws)
  , property("testWriterFunctorLaws", WriterFunctorLaws.laws)
  , property("testWriterApplicativeLaws", WriterApplicativeLaws.laws)
  , property("testWriterMonadLaws", WriterMonadLaws.laws)
  )

  object WriterTFunctorLaws {
    type WriterTOption[A] = WriterT[Option, String, A]

    def genWriterT: Gen[WriterTOption[Int]] = Gens.genWriterT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[WriterTOption](
          genWriterT
        , Gens.genIntToInt
        )
  }

  object WriterTApplicativeLaws {
    type WriterTOption[A] = WriterT[Option, String, A]

    def genWriterT: Gen[WriterTOption[Int]] = Gens.genWriterT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[WriterTOption](
          genWriterT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        )
  }

  object WriterTMonadLaws {
    type WriterTOption[A] = WriterT[Option, String, A]

    def genWriterT: Gen[WriterTOption[Int]] = Gens.genWriterT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[WriterTOption](
          genWriterT
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        , Gens.genAToMonadA(Gens.genIntToInt)
      )
  }

  import just.fp.testing.EqualUtil.TupleEqualInstance._

  object WriterFunctorLaws {
    type WriterString[A] = Writer[String, A]

    def genWriter: Gen[WriterString[Int]] = Gens.genWriterT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.functorLaws.laws[WriterString](
          genWriter
        , Gens.genIntToInt
      )
  }

  object WriterApplicativeLaws {
    type WriterString[A] = Writer[String, A]

    def genWriter: Gen[WriterString[Int]] = Gens.genWriterT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.applicativeLaws.laws[WriterString](
          genWriter
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
      )
  }

  object WriterMonadLaws {

    type WriterString[A] = Writer[String, A]

    def genWriter: Gen[WriterString[Int]] = Gens.genWriterT(Gens.genUnicodeString, Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monadLaws.laws[WriterString](
          genWriter
        , Gens.genIntFromMinToMax
        , Gens.genIntToInt
        , Gens.genAToMonadA(Gens.genIntToInt)
      )
  }
}
