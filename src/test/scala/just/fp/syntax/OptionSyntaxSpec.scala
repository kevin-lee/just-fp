package just.fp.syntax

import hedgehog._
import hedgehog.runner._
import just.fp.Gens
import just.fp.testing.TypeUtil

/**
  * @author Kevin Lee
  * @since 2019-09-21
  */
object OptionSyntaxSpec extends Properties {

  override def tests: List[Test] = List(
    property("testSome", testSome)
  , example("testNone", testNone)
  )

  def testSome: Property = for {
    a <- Gens.genIntFromMinToMax.log("a")
    b <- Gens.genLongFromMinToMax.log("b")
    c <- Gens.genBoolean.log("c")
    d <- Gens.genUnicodeString.log("d")
  } yield {
    val expected = "scala.Option"
    val actualA = a.some
    val actualB = b.some
    val actualC = c.some
    val actualD = d.some

    Result.all(List(
        Result.diffNamed(
            s"actualA class ==== $expected"
          , TypeUtil.getRuntimeClass(actualA).getName, expected
          )(_ === _)
      , Result.diffNamed(
            s"actualB class ==== $expected"
          , TypeUtil.getRuntimeClass(actualB).getName, expected
          )(_ === _)
      , Result.diffNamed(
            s"actualC class ==== $expected"
          , TypeUtil.getRuntimeClass(actualC).getName, expected
          )(_ === _)
      , Result.diffNamed(
            s"actualD class ==== $expected"
          , TypeUtil.getRuntimeClass(actualD).getName, expected
          )(_ === _)
    ))
  }

  def testNone: Result = {
    val expected = "scala.Option"

    val actualA = none[Int]
    val actualB = none[Long]
    val actualC = none[Boolean]
    val actualD = none[String]

    Result.all(List(
      Result.diffNamed(
        s"actualA class ==== $expected"
        , TypeUtil.getRuntimeClass(actualA).getName, expected
      )(_ === _)
      , Result.diffNamed(
        s"actualB class ==== $expected"
        , TypeUtil.getRuntimeClass(actualB).getName, expected
      )(_ === _)
      , Result.diffNamed(
        s"actualC class ==== $expected"
        , TypeUtil.getRuntimeClass(actualC).getName, expected
      )(_ === _)
      , Result.diffNamed(
        s"actualD class ==== $expected"
        , TypeUtil.getRuntimeClass(actualD).getName, expected
      )(_ === _)
    ))

  }

}
