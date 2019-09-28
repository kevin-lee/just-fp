package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-09-18
  */
object MonoidSpec extends Properties {

  override def tests: List[Test] = List(
    property("testOptionMonoidLaw", OptionMonoidLaws.laws)
  , property("testListMonoidLaw", ListMonoidLaws.laws)
  , property("testVectorMonoidLaw", VectorMonoidLaws.laws)
  , property("testStringMonoidLaw", StringMonoidLaws.laws)
  , property("testByteMonoidLaw", ByteMonoidLaws.laws)
  , property("testShortMonoidLaw", ShortMonoidLaws.laws)
  , property("testCharMonoidLaw", CharMonoidLaws.laws)
  , property("testIntMonoidLaw", IntMonoidLaws.laws)
  , property("testLongMonoidLaw", LongMonoidLaws.laws)
  , property("testBigIntMonoidLaw", BigIntMonoidLaws.laws)
  , property("testBigDecimalMonoidLaw", BigDecimalMonoidLaws.laws)
  )

  object ListMonoidLaws {
    def genList: Gen[List[Int]] = Gens.genList(Gens.genIntFromMinToMax, 20)

    def laws: Property =
      Specs.monoidLaws.laws[List[Int]](
        genList
      )
  }

  object VectorMonoidLaws {
    def genVector: Gen[Vector[Int]] = Gens.genVector(Gens.genIntFromMinToMax, 20)

    def laws: Property =
      Specs.monoidLaws.laws[Vector[Int]](
        genVector
      )
  }

  object OptionMonoidLaws {
    def genOption: Gen[Option[Int]] = Gens.genOption(Gens.genIntFromMinToMax)

    def laws: Property =
      Specs.monoidLaws.laws[Option[Int]](
        genOption
      )
  }

  object StringMonoidLaws {
    def genString: Gen[String] = Gens.genUnicodeString

    def laws: Property =
      Specs.monoidLaws.laws[String](
        genString
      )
  }

  object ByteMonoidLaws {
    def genByte: Gen[Byte] = Gens.genByteFromMinToMax

    def laws: Property =
      Specs.monoidLaws.laws[Byte](
        genByte
      )
  }

  object ShortMonoidLaws {
    def genShort: Gen[Short] = Gens.genShortFromMinToMax

    def laws: Property =
      Specs.monoidLaws.laws[Short](
        genShort
      )
  }

  object CharMonoidLaws {
    def genChar: Gen[Char] = Gens.genUnicodeChar

    def laws: Property =
      Specs.monoidLaws.laws[Char](
        genChar
      )
  }

  object IntMonoidLaws {
    def genInt: Gen[Int] = Gens.genIntFromMinToMax

    def laws: Property =
      Specs.monoidLaws.laws[Int](
        genInt
      )
  }

  object LongMonoidLaws {
    def genLong: Gen[Long] = Gens.genLongFromMinToMax

    def laws: Property =
      Specs.monoidLaws.laws[Long](
        genLong
      )
  }

  object BigIntMonoidLaws {
    def genBigInt: Gen[BigInt] = Gens.genBigInt

    def laws: Property =
      Specs.monoidLaws.laws[BigInt](
        genBigInt
      )
  }

  object BigDecimalMonoidLaws {
    def genBigDecimal: Gen[BigDecimal] = Gens.genBigDecimal

    def laws: Property =
      Specs.monoidLaws.laws[BigDecimal](
        genBigDecimal
      )
  }

}
