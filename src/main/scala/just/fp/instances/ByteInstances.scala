package just.fp.instances

import just.fp.Equal

/**
  * @author Kevin Lee
  * @since 2019-07-28
  */
trait ByteEqualInstance {
  implicit val byteEqual: Equal[Byte] = new Equal[Byte] {
    @SuppressWarnings(Array("org.wartremover.warts.Equals"))
    override def equal(x: Byte, y: Byte): Boolean = x == y
  }
}
