package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait FloatEqualInstance {
  implicit val floatEqual: Equal[Float] = NatualEqual.equalA[Float]
}
