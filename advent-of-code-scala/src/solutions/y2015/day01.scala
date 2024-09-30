/// Day 1: Not Quite Lisp
///
///
package solutions.y2015.day01

import solutions.y2015.year

import harness.*

given day: Day = Day(1)

object run extends AocDayRunner(part1, part2)

/** Find the final floor Santa reaches in the tower. */
def part1(input: String): Int = input
  .foldLeft(0) { (acc, cur) => acc + (if cur == '(' then 1 else -1) }

/** Find the position of the character indicating the first time Santa enters the basement (floor number -1). */
def part2(input: String): Int = input
  // Histomorphism to the rescue!
  .scanLeft(0) { (acc, cur) => acc + (if cur == '(' then 1 else -1) }
  .zipWithIndex
  .find { (x, i) => x == -1 } match {
    case None         => 0
    case Some((_, i)) => i
  }
