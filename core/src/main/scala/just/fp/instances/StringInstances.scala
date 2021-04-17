package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait StringEqualInstance {
  implicit val stringEqual: Equal[String] = NatualEqual.equalA[String]
}
