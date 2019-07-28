package kevinlee.fp.syntax

import hedgehog._
import hedgehog.runner._

import kevinlee.fp.Gens

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
object EqualSyntaxSpec extends Properties {

  object TestEqualSyntax extends EqualSyntax
  import TestEqualSyntax._
  import kevinlee.fp.Equal

  override def tests: List[Prop] = List(
      property("test Boolean === Boolean", testTripleEquals(Gens.genBoolean))
    , property("test Int === Int", testTripleEquals(Gens.genIntFromMinToMax))
    , property("test Short === Short", testTripleEquals(Gens.genShortFromMinToMax))
    , property("test Long === Long", testTripleEquals(Gens.genLongFromMinToMax))
    , property("test Char === Char", testTripleEquals(Gens.genUnicodeChar))
    , property("test Float === Float", testTripleEquals(Gens.genAllFloatNoNaN))
    , property("test Double === Double", testTripleEquals(Gens.genAllDoubleNoNaN))
    , property("test String === String", testTripleEquals(Gens.genUnicodeString))
    , property("test BigInt === BigInt", testTripleEquals(Gens.genBigInt))
    , property("test BigDecimal === BigDecimal", testTripleEquals(Gens.genBigDecimal))
    , property("test Boolean !== Boolean", testNotDoubleEquals(Gens.genDifferentBooleanPair))
    , property("test Int !== Int", testNotDoubleEquals(Gens.genDifferentIntPair))
    , property("test Short !== Short", testNotDoubleEquals(Gens.genDifferentShortPair))
    , property("test Long !== Long", testNotDoubleEquals(Gens.genDifferentLongPair))
    , property("test Char !== Char", testNotDoubleEquals(Gens.genDifferentUnicodeCharPair))
    , property("test Float !== Float", testNotDoubleEquals(Gens.genDifferentFloatPair))
    , property("test Double !== Double", testNotDoubleEquals(Gens.genDifferentDoublePair))
    , property("test String !== String", testNotDoubleEquals(Gens.genDifferentStringPair))
    , property("test BigInt !== BigInt", testNotDoubleEquals(Gens.genDifferentBigIntPair))
    , property("test BigDecimal !== BigDecimal", testNotDoubleEquals(Gens.genDifferentBigDecimalPair))
    )

  def testTripleEquals[A : Equal](gen: Gen[A]): Property = for {
    x <- gen.log("x")
  } yield {
    Result.diff(x, x)(_ === _)
  }

  def testNotDoubleEquals[A: Equal](gen: Gen[(A, A)]): Property = for {
    xyPair <- gen.log("(x, y)")
    (x, y) = xyPair
  } yield {
    Result.diff(x, y)(_ !== _)
  }
}
