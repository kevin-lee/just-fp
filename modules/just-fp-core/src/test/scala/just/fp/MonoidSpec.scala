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
  , example(
      "test OptionMonoid.isZero zero case"
    , testMonoidFunction("isZero", none[Int])(_.isZero(_))
    )
  , property(
      "test OptionMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genIntFromMinToMax.map(_.some))(!_.isZero(_))
    )
  , example(
      "test OptionMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", none[Int])(!_.nonZero(_))
    )
  , property(
      "test OptionMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genIntFromMinToMax.map(_.some))(_.nonZero(_))
    )

  , property("testListMonoidLaw", ListMonoidLaws.laws)
  , example(
      "test ListMonoid.isZero zero case"
    , testMonoidFunction("isZero", List.empty[Int])(_.isZero(_))
    )
  , property(
      "test ListMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genList(Gens.genIntFromMinToMax, 1, 10))(!_.isZero(_))
    )
  , example(
      "test ListMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", List.empty[Int])(!_.nonZero(_))
    )
  , property(
      "test ListMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genList(Gens.genIntFromMinToMax, 1, 10))(_.nonZero(_))
    )

  , property("testVectorMonoidLaw", VectorMonoidLaws.laws)
  , example(
      "test VectorMonoid.isZero zero case"
    , testMonoidFunction("isZero", Vector.empty[Int])(_.isZero(_))
    )
  , property(
      "test VectorMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genVector(Gens.genIntFromMinToMax, 1, 10))(!_.isZero(_))
    )
  , example(
      "test VectorMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", Vector.empty[Int])(!_.nonZero(_))
    )
  , property(
      "test VectorMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genVector(Gens.genIntFromMinToMax, 1, 10))(_.nonZero(_))
    )

  , property("testStringMonoidLaw", StringMonoidLaws.laws)
  , example(
      "test StringMonoid.isZero zero case"
    , testMonoidFunction("isZero", "")(_.isZero(_))
    )
  , property(
      "test StringMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gen.constant("a").flatMap(c => Gens.genUnicodeString.map(c + _)))(!_.isZero(_))
    )
  , example(
      "test StringMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", "")(!_.nonZero(_))
    )
  , property(
      "test StringMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gen.constant("a").flatMap(c => Gens.genUnicodeString.map(c + _)))(_.nonZero(_))
    )

  , property("testByteMonoidLaw", ByteMonoidLaws.laws)
  , example(
      "test ByteMonoid.isZero zero case"
    , testMonoidFunction("isZero", 0.toByte)(_.isZero(_))
    )
  , property(
      "test ByteMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genByte(1, Byte.MaxValue))(!_.isZero(_))
    )
  , example(
      "test ByteMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", 0.toByte)(!_.nonZero(_))
    )
  , property(
      "test ByteMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genByte(1, Byte.MaxValue))(_.nonZero(_))
    )

  , property("testShortMonoidLaw", ShortMonoidLaws.laws)
  , example(
      "test ShortMonoid.isZero zero case"
    , testMonoidFunction("isZero", 0.toShort)(_.isZero(_))
    )
  , property(
      "test ShortMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genShort(1, Short.MaxValue))(!_.isZero(_))
    )
  , example(
      "test ShortMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", 0.toShort)(!_.nonZero(_))
    )
  , property(
      "test ShortMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genShort(1, Short.MaxValue))(_.nonZero(_))
    )

  , property("testCharMonoidLaw", CharMonoidLaws.laws)
  , example(
      "test CharMonoid.isZero zero case"
    , testMonoidFunction("isZero", 0.toChar)(_.isZero(_))
    )
  , property(
      "test CharMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genChar(1, Char.MaxValue))(!_.isZero(_))
    )
  , example(
      "test CharMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", 0.toChar)(!_.nonZero(_))
    )
  , property(
      "test CharMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genChar(1, Char.MaxValue))(_.nonZero(_))
    )

  , property("testIntMonoidLaw", IntMonoidLaws.laws)
  , example(
      "test IntMonoid.isZero zero case"
    , testMonoidFunction("isZero", 0)(_.isZero(_))
    )
  , property(
      "test IntMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genInt(1, Int.MaxValue))(!_.isZero(_))
    )
  , example(
      "test IntMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", 0)(!_.nonZero(_))
    )
  , property(
      "test IntMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genInt(1, Int.MaxValue))(_.nonZero(_))
    )

  , property("testLongMonoidLaw", LongMonoidLaws.laws)
  , example(
      "test LongMonoid.isZero zero case"
    , testMonoidFunction("isZero", 0L)(_.isZero(_))
    )
  , property(
      "test LongMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genLong(1L, Long.MaxValue))(!_.isZero(_))
    )
  , example(
      "test LongMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", 0L)(!_.nonZero(_))
    )
  , property(
      "test LongMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genLong(1L, Long.MaxValue))(_.nonZero(_))
    )

  , property("testBigIntMonoidLaw", BigIntMonoidLaws.laws)
  , example(
      "test BigIntMonoid.isZero zero case"
    , testMonoidFunction("isZero", BigInt(0))(_.isZero(_))
    )
  , property(
      "test BigIntMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genBigInt(1L, Long.MaxValue))(!_.isZero(_))
    )
  , example(
      "test BigIntMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", BigInt(0))(!_.nonZero(_))
    )
  , property(
      "test BigIntMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genBigInt(1L, Long.MaxValue))(_.nonZero(_))
    )

  , property("testBigDecimalMonoidLaw", BigDecimalMonoidLaws.laws)
  , example(
      "test BigDecimalMonoid.isZero zero case"
    , testMonoidFunction("isZero", BigDecimal(0))(_.isZero(_))
    )
  , property(
      "test BigDecimalMonoid.isZero non-zero case"
    , propertyTestMonoidFunction("isZero", Gens.genBigDecimal(1F, Float.MaxValue, 1L, Long.MaxValue))(!_.isZero(_))
    )
  , example(
      "test BigDecimalMonoid.nonZero zero case"
    , testMonoidFunction("nonZero", BigDecimal(0))(!_.nonZero(_))
    )
  , property(
      "test BigDecimalMonoid.nonZero non-zero case"
    , propertyTestMonoidFunction("nonZero", Gens.genBigDecimal(1F, Float.MaxValue, 1L, Long.MaxValue))(_.nonZero(_))
    )
  )

  def testMonoidFunction[A : Monoid](name: String, monoid: A)(f: (Monoid[A], A) => Boolean): Result = {
    Result.diffNamed(s"=== $name Not true ===", Monoid[A], monoid)(f)
  }

  def propertyTestMonoidFunction[A : Monoid](name: String, genA: Gen[A])(f: (Monoid[A], A) => Boolean): Property = for {
    monoid <- genA.log("monoid")
  } yield {
    Result.diffNamed(s"=== $name Not false ===", Monoid[A], monoid)(f)
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
