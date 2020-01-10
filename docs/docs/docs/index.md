---
layout: docs
title: Getting Started
---
# just-fp

just-fp is a small Functional Programming library. This is not meant to be an alternative to Scalaz or Cats. The reason for having this library is that in your project you don't want to have Scalaz or Cats as its dependency and you only need much smaller set of functional programming features than what Scalaz or Cats offers. So the users of your library can choose Scalaz or Cats to be used with your library.

# Getting Started
In `build.sbt`,

```scala
libraryDependencies += "io.kevinlee" %% "just-fp" % "1.3.5"
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
