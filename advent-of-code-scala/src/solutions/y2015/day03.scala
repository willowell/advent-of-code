/// Day 2: Perfectly Spherical Houses in a Vacuum
///
///
package solutions.y2015.day03

import solutions.y2015.year

import harness.*

import core.control.combinators.*
import core.data.linear.v2.*
import core.parser.prelude.*

import parsley.*
import parsley.character.*
import parsley.errors.combinator.*
import parsley.combinator.*

given day: Day = Day(3)

object run extends AocDayRunner(part1, part2)

val pDirection: Parsley[V2[Int]] = choice(
  char('^') #> V2.up,
  char('>') #> V2.right,
  char('v') #> V2.down,
  char('<') #> V2.left,
)

val pPath: Parsley[List[V2[Int]]] = manyN(1, pDirection)

def part1(input: String): Int = (pPath <* Parsley.eof).parse(input)
  .map { xs => xs.scanLeft(V2.zero[Int]) { (acc, cur) => acc + cur }.toSet.size }
  .getOrElse(0)

def part2(input: String): Int = (pPath <* Parsley.eof).parse(input)
  .map { (xs) => {
    val (santa, roboSanta) = xs.zipWithIndex.partition { _._2 % 2 == 0 }

    val santasPath     = santa.map { _._1 }.scanLeft(V2.zero[Int]) { (acc, cur) => acc + cur }
    val roboSantasPath = roboSanta.map { _._1 }.scanLeft(V2.zero[Int]) { (acc, cur) => acc + cur }

    (santasPath ++ roboSantasPath).toSet.size
  }}
  .getOrElse(0)
