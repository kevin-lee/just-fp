package just.fp.instances

import just.fp.{Equal, NatualEqual}

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait ByteEqualInstance {
  implicit val byteEqual: Equal[Byte] = NatualEqual.equalA[Byte]
}
