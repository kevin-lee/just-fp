package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait BigDecimalEqualInstance {
  implicit val bigDecimalEqual: Equal[BigDecimal] = NatualEqual.equalA[BigDecimal]
}
