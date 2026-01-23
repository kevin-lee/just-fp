package just.fp.testing

import scala.reflect.ClassTag

/**
  * @author Kevin Lee
  * @since 2019-09-21
  */
object TypeUtil {

  def getRuntimeClass[A: ClassTag](a: A): Class[_] = {
    val _ = a
    implicitly[ClassTag[A]].runtimeClass
  }

}
