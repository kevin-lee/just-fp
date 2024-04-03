package just

/**
  * @author Kevin Lee
  * @since 2019-03-24
  */
package object fp extends IdInstance {

  type Id[X] = X

  type Writer[L, V] = WriterT[Id, L, V]

  object Writer {
    def apply[W, A](w: W, a: A): Writer[W, A] = Writer.writer((w, a))

    def writer[W, A](wa: (W, A)): Writer[W, A] = WriterT[Id, W, A](wa)
  }
}
