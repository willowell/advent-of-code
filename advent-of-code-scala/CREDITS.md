# Credits

## General

### The overall structure of this repository

I created the overall structure of this repository inspired by the structure for [the repository for the "Functional Programming in Scala" book](https://github.com/fpinscala/fpinscala).

### The Harness Module

I took inspiration for [the Harness module](./src/harness/) from [Solonarv's Haskell module of the same name in their Advent of Code Haskell project](https://github.com/Solonarv/adventofcode-2021). In particular, I liked the general architecture of abstracting running the solutions out into its own module and using command line arguments to determine which one to run.

However, since this project is in Scala and Scala allows specifying more than one main class, I used [bbstilson's `Problem` class from their `advent-of-code-data` project](https://github.com/bbstilson/advent-of-code-data/blob/main/aocd/src/aocd/Problem.scala) as the basis for `AocDayRunner`, allowing each day to have its own main class via `AocDayRunner` rather than integrating them all into a top-level `Main.scala`.

I also used `Problem` as the basis for `AocDayRunner` so that I could focus it on ONLY running solutions against input and facilitate future integration with Cats Effect.

Whereas bbstilson's `Problem` class additionally handles downloading data, I chose to move this functionality to [a separate `download` object](./src/tools/download.scala), allowing one to call the underlying `Aocd.downloadToInputFile(year, day)` through the Scala REPL.

Also, I just prefer knowing `AocDayRunner` will not make a network call!

I extracted [`time` from bbstilson's `Problem` class](https://github.com/bbstilson/advent-of-code-data/blob/main/aocd/src/aocd/Problem.scala#L26) and moved it to [`stopwatch.scala` as well](./src/harness/stopwatch.scala).

Additionally, [`harness/aocd.scala`](./src/harness/aocd.scala) contains verbatim code from [bbstilson's `Api` class](https://github.com/bbstilson/advent-of-code-data/blob/main/aocd/src/aocd/Api.scala), [bbstilson's `Problem` object](https://github.com/bbstilson/advent-of-code-data/blob/main/aocd/src/aocd/Problem.scala#L46), and [bbstilson's `TokenNotFound` case class and object](https://github.com/bbstilson/advent-of-code-data/blob/main/aocd/src/aocd/TokenNotFound.scala).

### The Core Module

[The Core module](./src/core/) is mostly copied verbatim from [my "Classic Computer Science Problems in Scala" repository](https://github.com/willowell/ClassicComputerScienceProblemsInScala), which is a port of David Kopec's ["Classic Computer Science Problems in Java" repository](https://github.com/davecom/ClassicComputerScienceProblemsInJava), with some additional inspiration from his "Classic Computer Science Problems in Swift" book as well.

As Advent of Code covers a wide range of topics, you will notice that this Core module contains more stuff than the one in the "Classic Computer Science Problems in Scala" repository.

#### Control

##### `Free`, `IO`, `Monad`, and `NatTrans`

I created these following along [Daniel Ciocîrlan's "Free Monad in Scala" article](https://blog.rockthejvm.com/free-monad/).

#### Crypto

##### `MD5`

I created `core.crypto.md5.MD5` following [Alvin Alexander's "A Scala method to create an MD5 hash of a string" tutorial](https://alvinalexander.com/source-code/scala-method-create-md5-hash-of-string/).

#### Data

#### `Aviary`

I created the `core.data.aviary` module inspired by the [`data-aviary` Haskell library](https://hackage.haskell.org/package/data-aviary-0.4.0), which implements the bird combinators in Haskell.

##### `FreqMap`

I created `FreqMap` following [this Baeldung tutorial: "Generate Frequency Map of Strings in Scala"](https://www.baeldung.com/scala/strings-frequency-map). I took the name from [Solonarv's `FreqMap` type](https://github.com/Solonarv/adventofcode-2021/blob/main/haskell/util/Util.hs#L47) because it sounded cool.

##### `Grid2D`

I created `Grid2D` as a Scala translation of [Solonarv's `Grid2D` data type](https://github.com/Solonarv/adventofcode-2021/blob/main/haskell/util/Grid2D.hs), except without the `indexed-traversable` stuff.

##### `V2` and `V3`

I created these 2D- and 3D-vector classes as translations of `V2` and `V3` from Edward Kmett's [`linear`](https://hackage.haskell.org/package/linear) library, using [SLASH's `Vec` newtype](https://github.com/dragonfly-ai/slash/blob/main/slash/shared/src/main/scala/slash/vector/package.scala) as a basis for how to implement this in Scala. In some sense, my `V2` and `V3` are really just simplified versions of SLASH's `Vec`.

### When I got stuck

When I got stuck, I referred to the following for guidance.

* [github.com/maneatingape/advent-of-code-scala](https://github.com/maneatingape/advent-of-code-scala)

## 2015

### Day 18: Like a GIF For Your Yard

[github.com/L7R7/game-of-life-scala](https://github.com/L7R7/game-of-life-scala/tree/master)

I also use a modified version of this code snippet from this repository for printing other 2D-point-keyed maps in other solutions:

(formatting adjusted)

```scala
override lazy val toString: String = cells.toSeq
  .sortWith((a: (Pos, Cell), b: (Pos, Cell)) => a._1.x.compareTo(b._1.x) < 0)
  .sortWith((a: (Pos, Cell), b: (Pos, Cell)) => a._1.y.compareTo(b._1.y) < 0)
  .map(_._2.toString)
  .mkString(" ")
  .grouped(2 * width)
  .mkString(LineSeparator)
```

## 2016

## 2017

## 2018

## 2019

## 2020

## 2021

## 2022

## 2023

## 2024

## 2025

## License Info

Since this Scala version (and the Haskell version!) both derive code from [Solonarv's Advent of Code Haskell project](https://github.com/Solonarv/adventofcode-2021), which they have licensed under GPL v3, I have chosen to apply the AGPL v3 license here. Since it is possible to compile Scala code to JavaScript via Scala.js, I suppose that it is possible one could use this project as part of a web service.
