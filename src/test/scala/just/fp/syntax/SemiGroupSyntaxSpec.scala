package just.fp.syntax

import hedgehog._
import hedgehog.runner._

/**
  * @author Kevin Lee
  * @since 2019-09-18
  */
object SemiGroupSyntaxSpec extends Properties {

  import just.fp._

  override def tests: List[Test] = List(
      property("test List |+| List", testPlus(Gens.genList(Gens.genIntFromMinToMax, 10)))
    , property("test Vector |+| Vector", testPlus(Gens.genVector(Gens.genIntFromMinToMax, 10)))
    , property("test String |+| String", testPlus(Gens.genUnicodeString))
    , property("test Byte |+| Byte", testPlus(Gens.genByteFromMinToMax))
    , property("test Short |+| Short", testPlus(Gens.genShortFromMinToMax))
    , property("test Char |+| Char", testPlus(Gens.genCharFromMinToMax))
    , property("test Int |+| Int", testPlus(Gens.genIntFromMinToMax))
    , property("test Long |+| Long", testPlus(Gens.genLongFromMinToMax))
    , property("test BigInt |+| BigInt", testPlus(Gens.genBigInt))
    , property("test BigDecimal |+| BigDecimal", testPlus(Gens.genBigDecimal))
    , property("test List.mappend(List)", testMappend(Gens.genList(Gens.genIntFromMinToMax, 10)))
    , property("test Vector.mappend(Vector)", testMappend(Gens.genVector(Gens.genIntFromMinToMax, 10)))
    , property("test String.mappend(String)", testMappend(Gens.genUnicodeString))
    , property("test Byte.mappend(Byte)", testMappend(Gens.genByteFromMinToMax))
    , property("test Short.mappend(Short)", testMappend(Gens.genShortFromMinToMax))
    , property("test Char.mappend(Char)", testMappend(Gens.genCharFromMinToMax))
    , property("test Int.mappend(Int)", testMappend(Gens.genIntFromMinToMax))
    , property("test Long.mappend(Long)", testMappend(Gens.genLongFromMinToMax))
    , property("test BigInt.mappend(BigInt)", testMappend(Gens.genBigInt))
    , property("test BigDecimal.mappend(BigDecimal)", testMappend(Gens.genBigDecimal))
    )

  def testPlus[A: SemiGroup](genA: Gen[A]): Property = for {
    a1 <- genA.log("a1")
    a2 <- genA.log("a2")
  } yield (a1 |+| a2) ==== implicitly[SemiGroup[A]].append(a1, a2)

  def testMappend[A: SemiGroup](genA: Gen[A]): Property = for {
    a1 <- genA.log("a1")
    a2 <- genA.log("a2")
  } yield (a1.mappend(a2)) ==== implicitly[SemiGroup[A]].append(a1, a2)

}
