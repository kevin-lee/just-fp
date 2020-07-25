---
id: 'type-safe-equal'
title: "Type-safe Equal"
---

## Type-safe Equal
`==` in Scala is not type safe so the following code can never be `true` as their types are different but it has no compile-time error.

```scala
1 == "1" // always false, no compile-time error
```

`just-fp` has `Equal` typeclass with typeclass instances for value types (`Byte`, `Short`, `Int`, `Char`, `Long`, `Float` and `Double`) as well as `String`, `BigInt` and `BigDecimal`.

It also has `Equal` typeclass instances for `WriterT`, `Writer` and `EitherT`.

With `just-fp`,

```scala mdoc
import just.fp.syntax._
```
```scala mdoc:fail
// use triple equals from just-fp
1 === "1"
```
```scala mdoc
1 === 1

"a" === "a"

1 !== 1

1 !== 2
```

If it's a `case class` the `equals` of which can be used for equality check, `NatualEqual` can be used.

e.g.)
```scala mdoc:reset-object:fail
final case class Foo(n: Int)

Foo(1) === Foo(1)
```
This can be solved by `NatualEqual.equalA[A]`.

```scala mdoc:reset-object
import just.fp._, syntax._

final case class Foo(n: Int)

object Foo {
  implicit val eqaul: Equal[Foo] = Equal.equalA[Foo]
}

Foo(1) === Foo(1)
// Boolean = true

Foo(1) === Foo(2)
// Boolean = false

Foo(1) !== Foo(1)
// Boolean = false

Foo(1) !== Foo(2)
// Boolean = true
```
