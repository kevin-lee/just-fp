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
      property("List |+| List", testPlus(Gens.genList(Gens.genIntFromMinToMax, 10)))
    , property("Vector |+| Vector", testPlus(Gens.genVector(Gens.genIntFromMinToMax, 10)))
    , property("test String |+| String", testPlus(Gens.genUnicodeString))
    , property("test Byte |+| Byte", testPlus(Gens.genByteFromMinToMax))
    , property("test Short |+| Short", testPlus(Gens.genShortFromMinToMax))
    , property("test Char |+| Char", testPlus(Gens.genCharFromMinToMax))
    , property("test Int |+| Int", testPlus(Gens.genIntFromMinToMax))
    , property("test Long |+| Long", testPlus(Gens.genLongFromMinToMax))
    , property("test BigInt |+| BigInt", testPlus(Gens.genBigInt))
    , property("test BigDecimal |+| BigDecimal", testPlus(Gens.genBigDecimal))
    )

  def testPlus[A: SemiGroup](genA: Gen[A]): Property = for {
    a1 <- genA.log("a1")
    a2 <- genA.log("a2")
  } yield (a1 |+| a2) ==== implicitly[SemiGroup[A]].append(a1, a2)

}
