package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait ShortEqualInstance {
  implicit val shortEqual: Equal[Short] = NatualEqual.equalA[Short]
}
