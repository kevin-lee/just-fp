---
layout: docs
title: Either
---

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

```scala mdoc
import just.fp.syntax._
```
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
```scala mdoc
Right(1)
``` 
You don't want to have `Nothing` there. So do it with type parameters,
```scala mdoc
Right[String, Int](1)
```
So it becomes unnecessarily verbose. Right should be inferred as the compiler knows it already yet to specify the left one, you have to put both left and right parameters.

`Left`, of course, has the same problem.

```scala mdoc
Left("error")
```
```scala mdoc
Left[String, Int]("error")
```

Now with `just-fp`, it's simpler. You can use use `left` and `right` constructors as extension methods to the actual data values with only missing type info specified.

e.g.)
```scala mdoc
1.right[String] // Now you only need to specify
                // the missing type info only
                // that is Left type parameter.
```
For `Left`,
```scala mdoc
"error".left[Int]
```

## `leftMap` and `leftFlatMap`
So if you Scala 2.12 or higher or `just-fp` with the older Scala, `Either` is right-biassed. Then what about the `Left` case? Can I ever use `Left` for something useful like transforming the `Left` value to something else?

For that, `just-fp` has added `leftMap` and `leftFlatMap` to `Either`.
e.g.)
```scala mdoc:reset-object
import just.fp.syntax._

final case class ComputeError(error: String)

sealed trait AppError
object AppError {
  final case class InvalidNumberError(error: String) extends AppError
  final case class ComputationError(computeError: ComputeError) extends AppError

  def invalidNumberError(error: String): AppError =
    InvalidNumberError(error)
  def fromComputeError(computeError: ComputeError): AppError =
    ComputationError(computeError)
}

def f1(n: Int): Either[String, Int] =
  if (n < 0)
    Left(s"The number must be non-negative integer - n: $n")
  else
    Right(n)

def f2(x: Int, y: Int): Either[ComputeError, Int] = {
  val z = x + y
  if (x >= 0 && y >= 0 && z < 0)
    Left(ComputeError(s"Numbers are too big - x: $x, y: $y"))
  else
    Right(x + y)
}  
```
```scala mdoc
for {
  b <- f1(10).leftMap(AppError.invalidNumberError)
  c <- f2(123, b).leftMap(AppError.fromComputeError)
} yield c
```
```scala mdoc
for {
  b <- f1(-1).leftMap(AppError.invalidNumberError)
  c <- f2(123, b).leftMap(AppError.fromComputeError)
} yield c
```
```scala mdoc
for {
  b <- f1(Int.MaxValue).leftMap(AppError.invalidNumberError)
  c <- f2(1, b).leftMap(AppError.fromComputeError)
} yield c
```
