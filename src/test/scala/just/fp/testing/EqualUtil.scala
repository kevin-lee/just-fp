package just.fp.testing

import just.fp.Equal

/**
  * @author Kevin Lee
  * @since 2019-09-19
  */
object EqualUtil {

  object FutureEqualInstance {
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration._
    import scala.concurrent.{Await, Future}

    implicit def futureEqual[A](implicit EQ: Equal[A]): Equal[Future[A]] = new Equal[Future[A]] {
      override def equal(x: Future[A], y: Future[A]): Boolean =
        Await.result(x.flatMap(a => y.map(b => EQ.equal(a, b))), 1.second)
    }
  }

  object TupleEqualInstance {

    import just.fp.JustSyntax._

    implicit def tupleEq[A: Equal, B: Equal]: Equal[(A, B)] = new Equal[(A, B)] {
      override def equal(x: (A, B), y: (A, B)): Boolean =
        x._1 === y._1 && x._2 === y._2
    }

  }
}
