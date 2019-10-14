package just.fp

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-09-18
  */
object MonoidSpec extends Properties {

  import just.fp.syntax._

  override def tests: List[Test] = List(
    property("testOptionMonoidLaw", OptionMonoidLaws.laws)
  , example("test OptionMonoid.isZero zero case", testIsZero(none[Int]))
  , property("test OptionMonoid.isZero non-zero case", testNonZero(Gens.genIntFromMinToMax.map(_.some)))
  , property("testListMonoidLaw", ListMonoidLaws.laws)
  , example("test ListMonoid.isZero zero case", testIsZero(List.empty[Int]))
  , property("test ListMonoid.isZero non-zero case", testNonZero(Gens.genList(Gens.genIntFromMinToMax, 1, 10)))
  , property("testVectorMonoidLaw", VectorMonoidLaws.laws)
  , example("test VectorMonoid.isZero zero case", testIsZero(Vector.empty[Int]))
  , property("test VectorMonoid.isZero non-zero case", testNonZero(Gens.genVector(Gens.genIntFromMinToMax, 1, 10)))
  , property("testStringMonoidLaw", StringMonoidLaws.laws)
  , example("test StringMonoid.isZero zero case", testIsZero(""))
  , property("test StringMonoid.isZero non-zero case", testNonZero(Gen.constant("a").flatMap(c => Gens.genUnicodeString.map(c + _))))
  , property("testByteMonoidLaw", ByteMonoidLaws.laws)
  , example("test ByteMonoid.isZero zero case", testIsZero(0.toByte))
  , property("test ByteMonoid.isZero non-zero case", testNonZero(Gens.genByte(1, Byte.MaxValue)))
  , property("testShortMonoidLaw", ShortMonoidLaws.laws)
  , example("test ShortMonoid.isZero zero case", testIsZero(0.toShort))
  , property("test ShortMonoid.isZero non-zero case", testNonZero(Gens.genShort(1, Short.MaxValue)))
  , property("testCharMonoidLaw", CharMonoidLaws.laws)
  , example("test CharMonoid.isZero zero case", testIsZero(0.toChar))
  , property("test CharMonoid.isZero non-zero case", testNonZero(Gens.genChar(1, Char.MaxValue)))
  , property("testIntMonoidLaw", IntMonoidLaws.laws)
  , example("test IntMonoid.isZero zero case", testIsZero(0))
  , property("test IntMonoid.isZero non-zero case", testNonZero(Gens.genInt(1, Int.MaxValue)))
  , property("testLongMonoidLaw", LongMonoidLaws.laws)
  , example("test LongMonoid.isZero zero case", testIsZero(0L))
  , property("test LongMonoid.isZero non-zero case", testNonZero(Gens.genLong(1L, Long.MaxValue)))
  , property("testBigIntMonoidLaw", BigIntMonoidLaws.laws)
  , example("test BigIntMonoid.isZero zero case", testIsZero(BigInt(0)))
  , property("test BigIntMonoid.isZero non-zero case", testNonZero(Gens.genBigInt(1L, Long.MaxValue)))
  , property("testBigDecimalMonoidLaw", BigDecimalMonoidLaws.laws)
  , example("test BigDecimalMonoid.isZero zero case", testIsZero(BigDecimal(0)))
  , property("test BigDecimalMonoid.isZero non-zero case", testNonZero(Gens.genBigDecimal(1F, Float.MaxValue, 1L, Long.MaxValue)))
  )

  def testIsZero[A : Monoid : Equal](monoid: A): Result = {
    Result.diffNamed("=== isZero Not true ===", Monoid[A], monoid)(_.isZero(_))
  }

  def testNonZero[A : Monoid : Equal](genA: Gen[A]): Property = for {
    monoid <- genA.log("monoid")
  } yield {
    Result.diffNamed("=== isZero Not false ===", Monoid[A], monoid)(!_.isZero(_))
  }

  object ListMonoidLaws {
    def genList: Gen[List[Int]] = Gens.genList(Gens.genIntFromMinToMax, 0, 20)

    def laws: Property =
      Specs.monoidLaws.laws[List[Int]](
        genList
      )
  }

  object VectorMonoidLaws {
    def genVector: Gen[Vector[Int]] = Gens.genVector(Gens.genIntFromMinToMax, 0, 20)

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
    def genBigInt: Gen[BigInt] = Gens.genBigIntFromMinToMaxLong

    def laws: Property =
      Specs.monoidLaws.laws[BigInt](
        genBigInt
      )
  }

  object BigDecimalMonoidLaws {
    def genBigDecimal: Gen[BigDecimal] = Gens.genBigDecimalFromMinToMaxFloatLong

    def laws: Property =
      Specs.monoidLaws.laws[BigDecimal](
        genBigDecimal
      )
  }

}
