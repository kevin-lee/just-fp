# just-fp

[![Build Status](https://semaphoreci.com/api/v1/kevin-lee/just-fp/branches/master/badge.svg)](https://semaphoreci.com/kevin-lee/just-fp)
[![Download](https://api.bintray.com/packages/kevinlee/maven/just-fp/images/download.svg)](https://bintray.com/kevinlee/maven/just-fp/_latestVersion)

[![Coverage Status](https://coveralls.io/repos/github/Kevin-Lee/just-fp/badge.svg?branch=master)](https://coveralls.io/github/Kevin-Lee/just-fp?branch=master)

A small Functional Programming library. This is not meant to be an alternative to Scalaz or Cats. The reason for having this library is that in your project you don't want to have Scalaz or Cats as its dependency and you only need much smaller set of functional programming features than what Scalaz or Cats offers. So the users of your library can choose Scalaz or Cats to be used with your library.

# Getting Started
In `build.sbt`,

```sbt
resolvers += "Just Repo" at "https://dl.bintray.com/kevinlee/maven"

libraryDependencies += "kevinlee" %% "just-fp" % "1.3.1"
```
then import

```scala
import just.fp._
import just.fp.syntax._
```
or 
```scala
import just.fp._, syntax._
```
In all the example code using `just-fp` below, I assume that you've already imported `just-fp` at the top.

# Either

## Right-Biased `Either`
`Either` in Scala prior to 2.12 is not right-biased meaning that you have to call `Either.right` all the time if you want to use it with `for-comprehension`.

e.g.) Before 2.12
```scala
for {
  b <- methodReturningEither(a).right
  c <- anotherReturningEither(b).right
} yield c
```
If you use `just-fp`, it becomes
```scala
for {
  b <- methodReturningEither(a)
  c <- anotherReturningEither(b)
} yield c
```
Of course, you don't need to do it if you use Scala 2.12 or higher.

## Either Constructors
In normal ways, if you want to create `Left` or `Right`, you just use the `apply` methods of their companion objects (i.e. `Left()` `Right()`) A problem with this is that what these return is not `Either` but its data, `Left` or `Right`.

You also need to specify not only type parameter for `Left` but also the one for `Right` when creating `Right`.

e.g.) Without type parameters,
```scala
Right(1)
// Right[Nothing, Int] = Right(1)
``` 
You don't want to have `Nothing` there. So do it with type parameters,
```scala
Right[String, Int](1)
// Right[String, Int] = Right(1)
```
So it becomes unnecessarily verbose. Right should be inferred as the compiler knows it already yet to specify the left one, you have to put both left and right parameters.

`Left`, of course, has the same problem.

```scala
Left("error")
// Left[String, Nothing] = Left("error")
```
```scala
Left[String, Int]("error")
// Left[String, Int] = Left("error")
```

Now with `just-fp`, it's simpler. You can use use `left` and `right` constructors as extension methods to the actual data values with only missing type info specified.

e.g.)
```scala
1.right[String] // Now you only need to specify
                // the missing type info only
                // that is Left type parameter.
// Either[String, Int] = Right(1)
```
For `Left`,
```scala
"error".left[Int]
// Either[String, Int] = Left("error")
```

## `leftMap` and `leftFlatMap`
So if you Scala 2.12 or higher or `just-fp` with the older Scala, `Either` is right-biassed. Then what about the `Left` case? Can I ever use `Left` for something useful like transforming the `Left` value to something else?

For that, `just-fp` has added `leftMap` and `leftFlatMap` to `Either`.
e.g.)
```scala
for {
  b <- f1(a).leftMap(AppError.calculatioError)
  c <- f2(b).leftMap(AppError.fromComputeError)
} yield c
// f1 returns Either[String, Int]
// f2 returns Either[ComputeError, Int]
// The result is Either[AppError, Int]
```

# Option
## Option Constructors
Similar to `Either`, creating `Option` can expose its data instead of type.

e.g.) The following code returns `Some[Int]` not `Option[Int]`.
```scala
Some(1)
// Some[Int] = Some(1)
```

```scala
None
// None.type = None
// Also None is None not Option[A] 
```

With `just-fp`,
```scala
1.some
// Option[Int] = Some(1)

none[String]
// Option[String] = None
```

# Type-safe Equal
`==` in Scala is not type safe so the following code can never be `true` as their types are different but it has no compile-time error.

```scala
1 == "1" // always false, no compile-time error
```

`just-fp` has `Equal` typeclass with typeclass instances for value types (`Byte`, `Short`, `Int`, `Char`, `Long`, `Float` and `Double`) as well as `String`, `BigInt` and `BigDecimal`.

It also has `Equal` typeclass instances for `WriterT`, `Writer` and `EitherT`.

With `just-fp`,
```scala
// use triple equals from just-fp
1 === "1"   // compile-time error
1 === 1     // true
"a" === "a" // true
1 !== 1     // false
1 !== 2     // true
```

If it's a `case class` the `equals` of which can be used for equality check, `NatualEqual` can be used.

e.g.)
```scala
case class Foo(n: Int)
```
```scala
Foo(1) === Foo(1)
       ^
error: value === is not a member of Foo
```
This can be solved by `NatualEqual`.

```scala
case class Foo(n: Int)

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

# Semi-Group
A semi-group is a typeclass which can apply associative binary operation. So if a type is a semi-group, its binary operation `append` can be applied. It's associative so `SemiGroup[A].append(SemiGroup[A].append(a, b), c)` is equal to `SemiGroup[A].append(a, SemiGroup[A].append(b, c))`

e.g.)
```scala
def foo[A](x: Int, y: Int, f: Int => A)(implicit S: SemiGroup[A]): A =
  S.append(f(x), f(y))

// or with context bound
def foo[A: SemiGroup](x: Int, y: Int, f: Int => A): A =
  implicitly[SemiGroup[A]].append(f(x), f(y))
```

If there is a typeclass instance of `SemiGroup` for a type `A`, `mappend` method or a convenient `|+|` infix operator can be used like this.

e.g.) There is already a SemiGroup typeclass instance for `Int` in `just-fp` so you can do
```scala
1.mappend(2)
// Int = 3

1 |+| 2
// Int = 3
``` 

Typeclass instances for the following typeclasses are available in `just-fp`.
* `SemiGroup[List[A]]`
  ```scala
  List(1, 2, 3) |+| List(4, 5, 6)
  // List[Int] = List(1, 2, 3, 4, 5, 6)
  
  List(1, 2, 3).mappend(List(4, 5, 6))
  // List[Int] = List(1, 2, 3, 4, 5, 6)
  ```
* `SemiGroup[Vector[A]]`
  ```scala
  Vector(1, 2, 3) |+| Vector(4, 5, 6)
  // Vector[Int] = Vector(1, 2, 3, 4, 5, 6)
  
  Vector(1, 2, 3).mappend(Vector(4, 5, 6))
  // Vector[Int] = Vector(1, 2, 3, 4, 5, 6)
  ```
* `SemiGroup[String]`
  ```scala
  "abc" |+| "def"
  // String = "abcdef"
  
  "abc".mappend("def")
  // String = "abcdef"
  ```
* `SemiGroup[Byte]`
  ```scala
  1.toByte |+| 2.toByte
  // Byte = 3
  
  1.toByte.mappend(2.toByte)
  // Byte = 3
  ```
* `SemiGroup[Short]`
  ```scala
  1.toShort |+| 2.toShort
  // Short = 3
  
  1.toShort.mappend(2.toShort)
  // Short = 3
  ```
* `SemiGroup[Char]`
  ```scala
  'A' |+| '1'
  // Char = 'r'

  'A'.mappend('1')
  // Char = 'r'
  ```
* `SemiGroup[Int]`
  ```scala
  1 |+| 2
  // Int = 3
  
  1.mappend(2)
  // Int = 3
  ```
* `SemiGroup[Long]`
  ```scala
  1L |+| 2L
  // Long = 3L
  
  1L.mappend(2L)
  // Long = 3L
  ```
* `SemiGroup[BigInt]`
  ```scala
  BigInt(1) |+| BigInt(2)
  // BigInt = 3
  
  BigInt(1).mappend(BigInt(2))
  // BigInt = 3
  ```
* `SemiGroup[BigDecimal]`
  ```scala
  BigDecimal(1) |+| BigDecimal(2)
  // BigDecimal = 3
  
  BigDecimal(1).mappend(BigDecimal(2))
  // BigDecimal = 3
  ```
* `SemiGroup[Option[A]]` if there is a typeclass instance of `SemiGroup[A]`.
  ```scala
  1.some |+| 2.some
  // Option[Int] = Some(3)
  
  1.some.mappend(2.some)
  // Option[Int] = Some(3)
  ```
  NOTE: There might be an associativity issue with `BigDecimal` if it's created with other `MathContext` than `MathContext.UNLIMITED` and the number is big enough in Scala 2.13. More about the issue please read [this blog](https://blog.kevinlee.io/2019/09/29/be-careful-when-using-bigdecimal-in-scala-2.13).

# Monoid
// To be updated ...

# Functor
// To be updated ...

# Applicative
// To be updated ...

# Monad
// To be updated ...

# WriterT / Writer
// To be updated ...

# EitherT
// To be updated ...
