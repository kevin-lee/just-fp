---
layout: docs
title: "Monoid"
---
# Monoid
`Monoid` is a `SemiGroup` with identity element which guarantees that 
`Monoid` complies with left identity law and right identity law.

## Left Identity
```scala
// Left identity
Monoid[A].append(Monoid[A].zero, A) // is just A
```

```scala mdoc
import just.fp._
import just.fp.syntax._

// So
Monoid[List[Int]].append(Monoid[List[Int]].zero, List(1, 2, 3))
// The same as
Monoid[List[Int]].append(List.empty, List(1, 2, 3))

Monoid[Int].append(Monoid[Int].zero, 999)
// The same as
Monoid[Int].append(0, 999)

Monoid[String].zero |+| "abc"
// The same as
"" |+| "abc"

Monoid[Option[Int]].zero.mappend(123.some)
// The same as
none[Int].mappend(123.some)
```


## Right Identity
```scala
// Right identity
Monoid[A].append(A, Monoid[A].zero) // is just A
```
```scala mdoc
import just.fp._
import just.fp.syntax._

// So
Monoid[List[Int]].append(List(1, 2, 3), Monoid[List[Int]].zero)
// The same as
Monoid[List[Int]].append(List(1, 2, 3), List.empty)

Monoid[Int].append(999, Monoid[Int].zero)
// The same as
Monoid[Int].append(999, 0)

"abc" |+| Monoid[String].zero
// The same as
"abc" |+| ""

123.some.mappend(Monoid[Option[Int]].zero)
// The same as
123.some.mappend(none[Int])
```

## Examples
Example use of `Monoid`
```scala mdoc
def fold[A : Monoid](as: List[A]): A =
  as.foldLeft(Monoid[A].zero)(_ |+| _)

fold(List(1, 2, 3, 4, 5))

fold(List("abc", "def", "ghi"))

fold(List(1.some, 2.some, none, 4.some, 5.some, none))
```
