/// Day 1: A Template Problem
///
///
package solutions.y0000.day01

import solutions.y0000.year

import harness.*

given day: Day = Day(1)

/** Solution runner. */
object run extends AocDayRunner(part1, part2)

/** Determine the meaning of life, the universe, and everything. */
def part1(input: String): Int = 42

/** Determine the sum of all the positive numbers leading up to the meaning of life, the universe, and everything. */
def part2(input: String): Int = (1 to 42).sum
