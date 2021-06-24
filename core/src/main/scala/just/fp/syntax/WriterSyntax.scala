package just.fp.syntax

import just.fp.Writer

/**
  * @author Kevin Lee
  * @since 2019-10-02
  */
object WriterOps {
  final class ToWriter[A](val a: A) extends AnyVal {
    def writer[W](w: W): Writer[W, A] = Writer(w, a)
  }
}

trait WriterSyntax {
  import WriterOps._

  @SuppressWarnings(Array("org.wartremover.warts.ImplicitConversion"))
  implicit final def toWriter[A](a: A): ToWriter[A] = new ToWriter(a)
}
