package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait BooleanEqualInstance {
  implicit val booleanEqual: Equal[Boolean] = NatualEqual.equalA[Boolean]
}
