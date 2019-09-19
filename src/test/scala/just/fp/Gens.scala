package just.fp

import hedgehog._

import just.fp.testing.TestPredef._

/**
  * @author Kevin Lee
  * @since 2019-06-01
  */
object Gens {

  def genBoolean: Gen[Boolean] =
    Gen.boolean

  def genDifferentBooleanPair: Gen[(Boolean, Boolean)] =
    for {
      x <- Gen.boolean
      y <- Gen.boolean.map(z => if (x === z) !z else z)
    } yield (x, y)

  def genByte(from: Byte, to: Byte): Gen[Byte] =
    Gens.genInt(from.toInt, to.toInt).map(_.toByte)

  def genByteFromMinToMax: Gen[Byte] = Gens.genByte(Byte.MinValue, Byte.MaxValue)

  def genInt(from: Int, to: Int): Gen[Int] =
    Gen.int(Range.linear(from, to))

  def genIntFromMinToMax: Gen[Int] = Gens.genInt(Int.MinValue, Int.MaxValue)

  def genIntToInt: Gen[Int => Int] =
    /* It has some hard-coded functions for now until Hedgehog has Gen[A => B]
    * Reference: https://github.com/hedgehogqa/scala-hedgehog/issues/90
    */
    Gen.element1[Int => Int](
        identity[Int]
        , x => x + x
        , x => x - x
        , x => x * x
        , x => x + 100
        , x => x - 100
        , x => x * 100
      )

  def genDifferentIntPair: Gen[(Int, Int)] = for {
    n1 <- genIntFromMinToMax
    n2 <- genIntFromMinToMax.map(x => if (x === n1) x + 1 else x)
  } yield (n1, n2)

  def genLong(from: Long, to: Long): Gen[Long] =
    Gen.long(Range.linear(from, to))

  def genLongFromMinToMax: Gen[Long] = Gens.genLong(Long.MinValue, Long.MaxValue)

  def genDifferentLongPair: Gen[(Long, Long)] = for {
    n1 <- genLongFromMinToMax
    n2 <- genLongFromMinToMax.map(x => if (x === n1) x + 1L else x)
  } yield (n1, n2)

  def genShort(from: Short, to: Short): Gen[Short] =
    Gen.short(Range.linear(from, to))

  def genShortFromMinToMax: Gen[Short] = Gens.genShort(Short.MinValue, Short.MaxValue)

  def genDifferentShortPair: Gen[(Short, Short)] = for {
    n1 <- genShortFromMinToMax
    n2 <- genShortFromMinToMax.map(x => if (x === n1) (x + 1).toShort else x)
  } yield (n1, n2)

  def genUnicodeChar: Gen[Char] =
    Gen.unicode

  def genDifferentUnicodeCharPair: Gen[(Char, Char)] = for {
    n1 <- genUnicodeChar
    n2 <- genUnicodeChar.map(x => if (x === n1) (x + 1).toChar else x)
  } yield (n1, n2)

  def genChar(from: Char, to: Char): Gen[Char] =
    Gen.char(from, to)

  def genCharFromMinToMax: Gen[Char] = Gens.genChar(Char.MinValue, Char.MaxValue)

  def genDifferentCharPair: Gen[(Char, Char)] = for {
    n1 <- genCharFromMinToMax
    n2 <- genCharFromMinToMax.map(x => if (x === n1) (x + 1).toChar else x)
  } yield (n1, n2)

  def genDouble(from: Double, to: Double): Gen[Double] =
    Gen.double(Range.linearFracFrom(0, from, to))

  def genDoubleFromMinToMax: Gen[Double] = Gens.genDouble(Float.MinValue.toDouble, Float.MaxValue.toDouble)

  def genAllDouble: Gen[Double] =
    Gen.frequency1(
        94 -> genDoubleFromMinToMax
      , 2 -> Gen.constant(Double.PositiveInfinity)
      , 2 -> Gen.constant(Double.NegativeInfinity)
      , 2 -> Gen.constant(Double.NaN)
      )

  def genAllDoubleNoNaN: Gen[Double] =
    Gen.frequency1(
        94 -> genDoubleFromMinToMax
      , 3 -> Gen.constant(Double.PositiveInfinity)
      , 3 -> Gen.constant(Double.NegativeInfinity)
      )

  def genDifferentDoublePair: Gen[(Double, Double)] = for {
    n1 <- genAllDouble
    n2 <- genAllDouble.map {
        case Double.NaN =>
          Double.NaN
        case Double.PositiveInfinity =>
          if (n1 === Double.PositiveInfinity)
            Double.NegativeInfinity
          else
            Double.PositiveInfinity
        case Double.NegativeInfinity =>
          if (n1 === Double.NegativeInfinity)
            Double.PositiveInfinity
          else
            Double.NegativeInfinity
        case x =>
          if (x === n1) x + 1D else x
      }
  } yield (n1, n2)

  def genFloat(from: Float, to: Float): Gen[Float] =
    Gens.genDouble(from.toDouble, to.toDouble).map(_.toFloat)

  def genFloatFromMinToMax: Gen[Float] = Gens.genFloat(Float.MinValue, Float.MaxValue)

  def genAllFloat: Gen[Float] =
    Gen.frequency1(
        94 -> genFloatFromMinToMax
      , 2 -> Gen.constant(Float.PositiveInfinity)
      , 2 -> Gen.constant(Float.NegativeInfinity)
      , 2 -> Gen.constant(Float.NaN)
    )

  def genAllFloatNoNaN: Gen[Float] =
    Gen.frequency1(
        94 -> genFloatFromMinToMax
      , 3 -> Gen.constant(Float.PositiveInfinity)
      , 3 -> Gen.constant(Float.NegativeInfinity)
    )

  def genDifferentFloatPair: Gen[(Float, Float)] = for {
    n1 <- genAllFloat
    n2 <- genAllFloat.map {
      case Float.NaN =>
        Float.NaN
      case Float.PositiveInfinity =>
        if (n1 === Float.PositiveInfinity)
          Float.NegativeInfinity
        else
          Float.PositiveInfinity
      case Float.NegativeInfinity =>
        if (n1 === Float.NegativeInfinity)
          Float.PositiveInfinity
        else
          Float.NegativeInfinity
      case x =>
        if (x === n1) x + 1F else x
    }
  } yield (n1, n2)

  def genUnicodeString: Gen[String] =
    Gen.string(Gen.unicodeAll, Range.linear(0, 100))

  def genDifferentStringPair: Gen[(String, String)] = for {
    x <- genUnicodeString
    y <- genUnicodeString.map(z => if (x === z) "a" + z else z)
  } yield {
    (x, y)
  }

  def genBigInt: Gen[BigInt] =
    genLongFromMinToMax.map(BigInt(_))

  def genDifferentBigIntPair: Gen[(BigInt, BigInt)] = for {
    n1 <- genBigInt
    n2 <- genBigInt.map(x => if (x === n1) x + BigInt(1) else x)
  } yield {
    (n1, n2)
  }

  def genBigDecimal: Gen[BigDecimal] =
    Gen.choice1(
        /*
         * MathContext.UNLIMITED should be used due to https://github.com/scala/bug/issues/11590
         */
        genDoubleFromMinToMax.map(BigDecimal(_, java.math.MathContext.UNLIMITED))
      , genBigInt.map(BigDecimal(_, java.math.MathContext.UNLIMITED))
      )

  def genDifferentBigDecimalPair: Gen[(BigDecimal, BigDecimal)] = for {
    n1 <- genBigDecimal
    n2 <- genBigDecimal.map(x => if (x === n1) x + BigDecimal(1) else x)
  } yield {
    (n1, n2)
  }

  def genAToMonadA[M[_], A](genF: Gen[A => A])(implicit m: Monad[M]): Gen[A => M[A]] =
    genF.map(f => x => m.pure(f(x)))

  def genList[A](genA: Gen[A], length: Int): Gen[List[A]] =
    genA.list(Range.linear(0, length))

  def genVector[A](genA: Gen[A], length: Int): Gen[Vector[A]] =
    genA.list(Range.linear(0, length)).map(_.toVector)

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  def genFuture[A](
    genInt: Gen[A])(
    implicit ex: scala.concurrent.ExecutionContext): Gen[scala.concurrent.Future[A]] =
    genInt.map(n => scala.concurrent.Future(n))

  def genOption[A](genA: Gen[A]): Gen[Option[A]] =
    genA.option

  def genEither[A, B](genA: Gen[A], genB: Gen[B]): Gen[Either[A, B]] =
    Gen.choice1(genA.map(Left(_)), genB.map(Right(_)))

  def genWriterT[F[_], W, A](genW: Gen[W], genA: Gen[A])(implicit F: Monad[F]): Gen[WriterT[F, W, A]] =
    for {
      w <- genW
      a <- genA
    } yield WriterT(F.pure((w, a)))

  def genEitherT[F[_], A, B](genA: Gen[A], genB: Gen[B])(implicit F: Monad[F]): Gen[EitherT[F, A, B]] =
    genEither(genA, genB).map {
      case Right(b) =>
        EitherT.pure(b)
      case Left(a) =>
        EitherT.pureLeft(a)
    }

}
