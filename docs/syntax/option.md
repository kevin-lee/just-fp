---
id: 'option'
title: "Option"
---

## Option
## Option Constructors
Similar to `Either`, creating `Option` can expose its data instead of type.

e.g.) The following code returns `Some[Int]` not `Option[Int]`.
```scala mdoc
Some(1)
```

Also None is None not `Option[A]`.
```scala mdoc
None
```

With `just-fp`,

```scala mdoc
import just.fp.syntax._
```
```scala mdoc
1.some
```
```scala mdoc
none[String]
```
