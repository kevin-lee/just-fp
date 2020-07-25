---
id: 'functor'
title: "Functor"
---
## Functor
A `functor` is a typeclass for the types that can be mapped over.

`Functor` complies with identity law and composition law.

## Identity Law
`map(identity) === identity`
```scala mdoc:reset-object
import just.fp._
import just.fp.syntax._

Functor[Option].map(1.some)(identity(_)) === identity(1.some)
Functor[Option].map(1.some)(identity(_))
identity(1.some)

Functor[Option].map(none[Int])(identity(_)) === identity(none[Int])
Functor[Option].map(none[Int])(identity(_))
identity(none[Int])
```

## Composition Law
`map(f compose g) === map(f) compose map(g)`
```scala mdoc:reset-object
import just.fp._
import just.fp.syntax._

val f = (a: Int) => a + 100
val g = (b: Int) => b * 2

Functor[Option].map(1.some)(f compose g)

Functor[Option].map(Functor[Option].map(1.some)(g))(f)

Functor[Option].map(1.some)(f compose g) ===
  Functor[Option].map(Functor[Option].map(1.some)(g))(f)


Functor[Option].map(none[Int])(f compose g)

Functor[Option].map(Functor[Option].map(none[Int])(g))(f)

Functor[Option].map(none[Int])(f compose g) ===
  Functor[Option].map(Functor[Option].map(none[Int])(g))(f)
```


## Examples

If there is a typeclass instance of `Functor` for a type `A`, 
`map` method can be used.

e.g.)
```scala mdoc:reset-object
import just.fp._

final case class SomeType[A](a: A)

object SomeType {
  implicit val functorA: Functor[SomeType] = new Functor[SomeType] {
    override def map[A, B](fa: SomeType[A])(f: A => B): SomeType[B] =
      SomeType(f(fa.a)) 
  } 
}

def times2(someType: SomeType[Int]): SomeType[Int] = 
    implicitly[Functor[SomeType]].map(someType)(_ * 2)

times2(SomeType(111))
```

There are existing `Functor` instances for Scala's `Option`, `Either`, `List`, `Vector` and `Future`.
```scala mdoc:reset-object
import just.fp._
import just.fp.syntax._ 
// just.fp.syntax._ is only for .some, none, .right, .left

def foo[A, B, F[_] : Functor](fa: F[A])(f: A => B): F[B] =
  Functor[F].map(fa)(f) 
  // or implicitly[Functor[F]].map(fa)(f)

foo(1.some)(_ * 2)
foo(none[Int])(_ * 2)

foo(1.right[String])(_ * 2)
foo("error".left[Int])(_ * 2)

foo(List(1, 2, 3, 4, 5))(_ * 2)
foo(List.empty[Int])(_ * 2)

foo(Vector(1, 2, 3, 4, 5))(_ * 2)
foo(Vector.empty[Int])(_ * 2)

import scala.concurrent.Future
implicit val ec = scala.concurrent.ExecutionContext.global

foo(Future(1))(_ * 2)
```
