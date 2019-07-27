package kevinlee.fp.syntax

import hedgehog._
import hedgehog.runner._


/**
  * @author Kevin Lee
  * @since 2019-05-26
  */
object EitherOpsSpec extends Properties {

  object TestSyntax extends EitherSyntax
  import TestSyntax._

  override def tests: List[Prop] = List(
    property("Left(a).foreach should do nothing", testLeftForeach)
  , property("Right(b).foreach(f) should apply f to b in Right", testRightForeach)
  , property("Left(a).getOrElse(b) should return b", testLeftGetOrElse)
  , property("Right(b).getOrElse(b2) should return b", testRightGetOrElse)
  , property("Left(a).forall(f) should always be true", testLeftForall)
  , property("Right(b).forall(f) should return the result of f(b)", testRightForall)
  , property("Left(a).exists(f) should always be false", testLeftExists)
  , property("Right(b).exists(f) should return the result of f(b)", testRightExists)
  , property("Left(a).flatMap(f) should always be Left(a)", testLeftFlatMap)
  , property("Right(b).flatMap(f) should return f(b)", testRightFlatMap)
  , property("Left(a).map(f) should always be Left(a)", testLeftMap)
  , property("Right(b).map(f) should return Right(f(b))", testRightMap)
  , property("Left(a).leftFlatMap(f) should return f(a)", testLeftLeftFlatMap)
  , property("Right(b).leftFlatMap(f) should always be Right(b)", testRightLeftFlatMap)
  , property("Left(a).leftMap(f) should return Left(f(a))", testLeftLeftMap)
  , property("Right(b).leftMap(f) should always be Right(b)", testRightLeftMap)
  , property("Left(a).filter(f) should always return None", testLeftFilter)
  , property("Right(b).filter(f) should return Some(Right(b)) if f(b) is true. None otherwise", testRightFilter)
  , property("Left(a).toList should return Nil", testLeftToList)
  , property("Right(b).toList should return List(b)", testRightToList)
  , property("Left(a).toOption should return None", testLeftToOption)
  , property("Right(b).toOption should return Some(b)", testRightToOption)
  )

  /* RightBiasedEither */

  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  def testLeftForeach: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
  } yield {
    val l = a.left[Int]
    var x: Option[Int] = None
    l.foreach {y =>
      x = Some(y)
      ()
    }
    x ==== None
  }

  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  def testRightForeach: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
  } yield {
    val r = b.right[String]
    var x: Option[Int] = None
    r.foreach { y =>
      x = Some(y)
      ()
    }
    x ==== Some(b)
  }

  def testLeftGetOrElse: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
    b <- Gen.int(Range.linear(1, 100)).log("b")
  } yield {
    val l = a.left[Int]
    l.getOrElse(b) ==== b
  }

  def testRightGetOrElse: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
    b2 <- Gen.int(Range.linear(1, 100)).log("b2")
  } yield {
    val r = b.right[String]
    r.getOrElse(b2) ==== b
  }

  def testLeftForall: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
  } yield {
    val l = a.left[Int]
    l.forall(_ > 0) ==== true
  }

  def testRightForall: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
    c <- Gen.int(Range.linear(1, 100)).log("c")
  } yield {
    val r = b.right[String]
    r.forall(x => x > c) ==== (b > c)
  }

  def testLeftExists: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
  } yield {
    val l = a.left[Int]
    l.exists(_ > 0) ==== false
  }

  def testRightExists: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
  } yield {
    val r = b.right[String]
    Result.all(List(
      (r.exists(x => x == b) ==== true).log("exist case")
    , (r.exists(x => x != b) ==== false).log("not exist case")
    ))
  }

  def testLeftFlatMap: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
  } yield {
    val l = a.left[Int]
    l.flatMap(x => Right(x + x)) ==== Left(a)
  }

  def testRightFlatMap: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
    b2 <- Gen.int(Range.linear(1, 100)).log("b2")
  } yield {
    val r = b.right[String]
    r.flatMap(x => Right(x + b2)) ==== Right(b + b2)
  }

  def testLeftMap: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
  } yield {
    val l = a.left[Int]
    l.map(x => x + x) ==== Left(a)
  }

  def testRightMap: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
    b2 <- Gen.int(Range.linear(1, 100)).log("b2")
  } yield {
    val r = b.right[String]
    r.map(x => x + b2) ==== Right(b + b2)
  }

  def testLeftLeftFlatMap: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
    a2 <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a2")
  } yield {
    val l = a.left[Int]
    l.leftFlatMap(x => Left(x + a2)) ==== Left(a + a2)
  }

  def testRightLeftFlatMap: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
  } yield {
    val r = b.right[String]
    r.leftFlatMap(x => Left(x + x)) ==== Right(b)
  }

  def testLeftLeftMap: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
    a2 <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a2")
  } yield {
    val l = a.left[Int]
    l.leftMap(x => x + a2) ==== Left(a + a2)
  }

  def testRightLeftMap: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
  } yield {
    val r = b.right[String]
    r.leftMap(x => x + x) ==== Right(b)
  }

  def testLeftFilter: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
  } yield {
    val l = a.left[Int]
    Result.all(List(
      (l.filter(x => x == x) ==== None).log("equal case")
    , (l.filter(x => x != x) ==== None).log("not equal case")
    ))
  }

  def testRightFilter: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
  } yield {
    val r = b.right[String]
    Result.all(List(
      (r.filter(x => x == x) ==== Some(Right(b))).log("equal case")
    , (r.filter(x => x != x) ==== None).log("not equal case")
    ))
  }

  def testLeftToList: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
  } yield {
    val l = a.left[Int]
    l.toList ==== List.empty[Int]
  }

  def testRightToList: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
  } yield {
    val r = b.right[String]
    r.toList ==== List(b)
  }

  def testLeftToOption: Property = for {
    a <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("a")
  } yield {
    val l = a.left[Int]
    l.toOption ==== None
  }

  def testRightToOption: Property = for {
    b <- Gen.int(Range.linear(1, 100)).log("b")
  } yield {
    val r = b.right[String]
    r.toOption ==== Some(b)
  }

}
