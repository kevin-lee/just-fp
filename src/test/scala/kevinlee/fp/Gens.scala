package kevinlee.fp

import hedgehog._

/**
  * @author Kevin Lee
  * @since 2019-06-01
  */
object Gens {

  def genInts(from: Int, to: Int): Gen[Int] =
    Gen.int(Range.linear(from, to))

  def genIntToInt: Gen[Int => Int] =
    Gen.element1[Int => Int](identity[Int], x => x + x, x => x * 100, x => x + 100, x => x - 100)

  def genAToMonadA[M[_], A](genF: Gen[A => A])(implicit m: Monad[M]): Gen[A => M[A]] =
    genF.map(f => x => m.pure(f(x)))

  def genList[A](genA: Gen[A], length: Int): Gen[List[A]] =
    genA.list(Range.linear(0, length))

  def genVector[A](genA: Gen[A], length: Int): Gen[Vector[A]] =
    genA.list(Range.linear(0, length)).map(_.toVector)

}
