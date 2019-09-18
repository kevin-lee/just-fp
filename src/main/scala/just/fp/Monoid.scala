package just.fp

/**
  * @author Kevin Lee
  * @since 2019-03-16
  */
trait Monoid[A] extends SemiGroup[A] {
  def zero: A

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  trait MonoidLaw {
    /*
     * (x <> y) <> z = x <> (y <> z) -- associativity
     */
    def associativity(ma: Monoid[A], x: A, y: A, z: A)(implicit EA: Equal[A]): Boolean =
      EA.equal(ma.append(ma.append(x, y), z), ma.append(x, ma.append(y, z)))

    /*
     * mempty <> x = x -- left identity
     */
    def leftIdentity(ma: Monoid[A], x: A)(implicit EA: Equal[A]): Boolean =
      EA.equal(ma.append(ma.zero, x), x)

    /*
     * x <> mempty = x -- right identity
     */
    def rightIdentity(ma: Monoid[A], x: A)(implicit EA: Equal[A]): Boolean =
      EA.equal(ma.append(x, ma.zero), x)
  }

  def monoidLaw: MonoidLaw = new MonoidLaw {}
}

object Monoid {

  implicit def listMonoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    override def zero: List[A] = Nil

    override def append(a1: List[A], a2: => List[A]): List[A] = a1 ++ a2
  }

  implicit def vectorMonoid[A]: Monoid[Vector[A]] = new Monoid[Vector[A]] {
    override def zero: Vector[A] = Vector.empty

    override def append(a1: Vector[A], a2: => Vector[A]): Vector[A] = a1 ++ a2
  }

  implicit def stringMonoid[A]: Monoid[String] = new Monoid[String] {
    override def zero: String = ""

    override def append(a1: String, a2: => String): String = a1 + a2
  }

  implicit def byteMonoid[A]: Monoid[Byte] = new Monoid[Byte] {
    override def zero: Byte = 0.toByte

    override def append(a1: Byte, a2: => Byte): Byte = (a1 + a2).toByte
  }

  implicit def shortMonoid[A]: Monoid[Short] = new Monoid[Short] {
    override def zero: Short = 0.toShort

    override def append(a1: Short, a2: => Short): Short = (a1 + a2).toShort
  }

  implicit def charMonoid[A]: Monoid[Char] = new Monoid[Char] {
    override def zero: Char = Char.MinValue

    override def append(a1: Char, a2: => Char): Char = (a1 + a2).toChar
  }

  implicit def intMonoid[A]: Monoid[Int] = new Monoid[Int] {
    override def zero: Int = 0

    override def append(a1: Int, a2: => Int): Int = a1 + a2
  }

  implicit def longMonoid[A]: Monoid[Long] = new Monoid[Long] {
    override def zero: Long = 0L

    override def append(a1: Long, a2: => Long): Long = a1 + a2
  }

  implicit def bigIntMonoid[A]: Monoid[BigInt] = new Monoid[BigInt] {
    override def zero: BigInt = BigInt(0)

    override def append(a1: BigInt, a2: => BigInt): BigInt = a1 + a2
  }

  implicit def bigDecimalMonoid[A]: Monoid[BigDecimal] = new Monoid[BigDecimal] {
    override def zero: BigDecimal = BigDecimal(0)

    override def append(a1: BigDecimal, a2: => BigDecimal): BigDecimal = a1 + a2
  }

}