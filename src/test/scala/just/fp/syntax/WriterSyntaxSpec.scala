package just.fp.syntax

import hedgehog._
import hedgehog.runner._

import just.fp.Gens

/**
  * @author Kevin Lee
  * @since 2019-10-02
  */
object WriterSyntaxSpec extends Properties {
  override def tests: List[Test] = List(
    property("test value.writer(w) syntax", testValueWriter)
  )

  def testValueWriter: Property = for {
    a <- Gens.genIntFromMinToMax.log("a")
    w <- Gens.genUnicodeString.log("w")
  } yield {
    import just.fp._

    val expected = Writer(w, a)
    val actual = a.writer(w)

    actual ==== expected
  }
}
