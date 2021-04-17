package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait LongEqualInstance {
  implicit val longEqual: Equal[Long] = NatualEqual.equalA[Long]
}
