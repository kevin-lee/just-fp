package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait CharEqualInstance {
  implicit val charEqual: Equal[Char] = NatualEqual.equalA[Char]
}
