package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait DoubleEqualInstance {
  implicit val doubleEqual: Equal[Double] = NatualEqual.equalA[Double]
}
