package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait BigIntEqualInstance {
  implicit val bigIntEqual: Equal[BigInt] = NatualEqual.equalA[BigInt]
}
