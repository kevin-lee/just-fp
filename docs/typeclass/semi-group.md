---
id: 'semi-group'
title: "Semi-Group"
---

# Semi-Group
A semi-group is a typeclass which can apply associative binary operation. 
So if a type is a semi-group, its binary operation `append` can be applied. 
It's associative so `SemiGroup[A].append(SemiGroup[A].append(a, b), c)` is 
equal to `SemiGroup[A].append(a, SemiGroup[A].append(b, c))`

e.g.)
```scala
import just.fp._

def foo[A](x: Int, y: Int, f: Int => A)(implicit S: SemiGroup[A]): A =
  S.append(f(x), f(y))

// or with context bound
def foo[A: SemiGroup](x: Int, y: Int, f: Int => A): A =
  SemiGroup[A].append(f(x), f(y))
```

If there is a typeclass instance of `SemiGroup` for a type `A`, 
`mappend` method or a convenient `|+|` infix operator can be used like this.

e.g.) There is already a SemiGroup typeclass instance for `Int` in `just-fp` 
so you can do
```scala mdoc
import just.fp.syntax._
```
```scala mdoc
1.mappend(2)

1 |+| 2
``` 

Typeclass instances for the following typeclasses are available in `just-fp`.
* `SemiGroup[List[A]]`  

```scala mdoc
List(1, 2, 3) |+| List(4, 5, 6)

List(1, 2, 3).mappend(List(4, 5, 6))
```

* `SemiGroup[Vector[A]]`

```scala mdoc
Vector(1, 2, 3) |+| Vector(4, 5, 6)

Vector(1, 2, 3).mappend(Vector(4, 5, 6))
```

* `SemiGroup[String]`

```scala mdoc
"abc" |+| "def"

"abc".mappend("def")
```

* `SemiGroup[Byte]`

```scala mdoc
1.toByte |+| 2.toByte

1.toByte.mappend(2.toByte)
```

* `SemiGroup[Short]`

```scala mdoc
1.toShort |+| 2.toShort

1.toShort.mappend(2.toShort)
```

* `SemiGroup[Char]`

```scala mdoc
'A' |+| '1'

'A'.mappend('1')
```

* `SemiGroup[Int]`

```scala mdoc
1 |+| 2

1.mappend(2)
```

* `SemiGroup[Long]`

```scala mdoc
1L |+| 2L

1L.mappend(2L)
```

* `SemiGroup[BigInt]`

```scala mdoc
BigInt(1) |+| BigInt(2)

BigInt(1).mappend(BigInt(2))
```

* `SemiGroup[BigDecimal]`

```scala mdoc
BigDecimal(1) |+| BigDecimal(2)

BigDecimal(1).mappend(BigDecimal(2))
```

* `SemiGroup[Option[A]]` if there is a typeclass instance of `SemiGroup[A]`.

```scala mdoc
1.some |+| 2.some

1.some.mappend(2.some)
```

NOTE: There might be an associativity issue with `BigDecimal` 
if it's created with other `MathContext` than `MathContext.UNLIMITED` and 
the number is big enough in Scala 2.13. 

More about the issue, please read [this blog](https://blog.kevinlee.io/2019/09/29/be-careful-when-using-bigdecimal-in-scala-2.13).
