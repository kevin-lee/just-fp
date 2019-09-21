# just-fp

[![Build Status](https://semaphoreci.com/api/v1/kevin-lee/just-fp/branches/master/badge.svg)](https://semaphoreci.com/kevin-lee/just-fp)
[![Download](https://api.bintray.com/packages/kevinlee/maven/just-fp/images/download.svg)](https://bintray.com/kevinlee/maven/just-fp/_latestVersion)

# Getting Started
In `build.sbt`,

```sbt
resolvers += "3rd Party Repo" at "https://dl.bintray.com/kevinlee/maven"

libraryDependencies += "kevinlee" %% "just-fp" % "1.2.0"
```
then import

```scala
import just.fp._
import just.fp.syntax._
```
