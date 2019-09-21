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
    , property("testToEither", testToEither)
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
            "=== Not Equal ==="
          , TypeUtil.getRuntimeClass(actualA).getName, expected
          )(_ === _)
      , Result.diffNamed(
            "=== Not Equal ==="
          , TypeUtil.getRuntimeClass(actualB).getName, expected
          )(_ === _)
      , Result.diffNamed(
            "=== Not Equal ==="
          , TypeUtil.getRuntimeClass(actualC).getName, expected
          )(_ === _)
      , Result.diffNamed(
            "=== Not Equal ==="
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
          "=== Not Equal ==="
        , TypeUtil.getRuntimeClass(actualA).getName, expected
      )(_ === _)
      , Result.diffNamed(
          "=== Not Equal ==="
        , TypeUtil.getRuntimeClass(actualB).getName, expected
      )(_ === _)
      , Result.diffNamed(
          s"actualC class ==== $expected"
        , TypeUtil.getRuntimeClass(actualC).getName, expected
      )(_ === _)
      , Result.diffNamed(
          "=== Not Equal ==="
        , TypeUtil.getRuntimeClass(actualD).getName, expected
      )(_ === _)
    ))
  }

  def testToEither: Property = for {
    maybeN <- Gens.genOption(Gens.genIntFromMinToMax).log("maybeN")
    leftString <- Gens.genUnicodeString.log("leftString")
  } yield {
    val expectedType = "scala.util.Either"
    val actual = maybeN.toEither(leftString)
    Result.all(List(
        Result.diffNamed(
            "=== Not Equal ==="
          , TypeUtil.getRuntimeClass(actual).getName, expectedType
        )(_ === _)
      , maybeN match {
          case Some(n) =>
            actual ==== Right(n)

          case None =>
            actual ==== Left(leftString)
        }
      ))
  }

}
