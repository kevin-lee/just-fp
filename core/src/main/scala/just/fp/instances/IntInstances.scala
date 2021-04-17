package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait IntEqualInstance {
  implicit val intEqual: Equal[Int] = NatualEqual.equalA[Int]
}
