---
sidebar_position: 1
id: 'getting-started'
title: 'Getting Started'
slug: '/'
---

## ![](/img/just-fp-logo-64x64.png) just-fp

[![Build Status](https://github.com/Kevin-Lee/just-fp/workflows/Build-All/badge.svg)](https://github.com/Kevin-Lee/just-fp/actions?workflow=Build-All)
[![Release Status](https://github.com/Kevin-Lee/just-fp/workflows/Release/badge.svg)](https://github.com/Kevin-Lee/just-fp/actions?workflow=Release)
[![Coverage Status](https://coveralls.io/repos/github/Kevin-Lee/just-fp/badge.svg?branch=main)](https://coveralls.io/github/Kevin-Lee/just-fp?branch=main)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.kevinlee/just-fp-core_2.13/badge.svg)](https://search.maven.org/artifact/io.kevinlee/just-fp-core_2.13)
[![Latest version](https://index.scala-lang.org/kevin-lee/just-fp/just-fp-core/latest.svg)](https://index.scala-lang.org/kevin-lee/just-fp/just-fp-core)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/io.kevinlee/just-fp-core_2.13?color=lightblue&label=Current%20Snapshot&server=https%3A%2F%2Foss.sonatype.org)


* Supported Scala Versions: @SUPPORTED_SCALA_VERSIONS@

just-fp is a small Functional Programming library. This is not meant to be an alternative to Scalaz or Cats. The reason for having this library is that in your project you don't want to have Scalaz or Cats as its dependency and you only need much smaller set of functional programming features than what Scalaz or Cats offers. So the users of your library can choose Scalaz or Cats to be used with your library.

## Getting Started

In `build.sbt`,

```scala
libraryDependencies += "io.kevinlee" %% "just-fp-core" % "@VERSION@"
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

