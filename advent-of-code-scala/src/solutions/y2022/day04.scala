/// Day 4: Camp Cleanup
///
///
package solutions.y2022.day04

import solutions.y2022.year
import harness.*

import core.parser.prelude.*
import core.data.seq.*

import parsley.*

given day: Day = Day(4)

object run extends AocDayRunner(part1, part2)


type Assignment = List[Int]

val pAssignment: Parsley[Assignment] = around(int32, int32, dash).map { (f, s) => List.range(f, s + 1) }

val pAssignmentPair: Parsley[(Assignment, Assignment)] = around(pAssignment, pAssignment, comma)

val pAssignmentPairs: Parsley[List[(Assignment, Assignment)]] = sepByEol1(pAssignmentPair)


def part1(input: String): Int = {
  val res = pAssignmentPairs.parse(input)

  res match {
    case Success(xs) => xs.filter { (f, s) => f eitherWholyContains s }.length
    case Failure(err) => 0
  }
}


def part2(input: String): Int = {
  val res = pAssignmentPairs.parse(input)

  res match {
    case Success(xs) => xs.filter { (f, s) => f overlaps s }.length
    case Failure(err) => 0
  }
}
