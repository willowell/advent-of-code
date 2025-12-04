/// Day 1: Calorie Counting
///
///
package solutions.y2022.day01

import solutions.y2022.year
import harness.*

import core.parser.prelude.*

import parsley.*

given day: Day = Day(1)

object run extends AocDayRunner(part1, part2)


def part1(input: String): Int = sepByEol2(int32).parse(input)
  .map { xss => xss.map { _.sum }.max }
  .getOrElse(0)


def part2(input: String): Int = sepByEol2(int32).parse(input)
  .map { (xss) => xss
    .map { _.sum }
    .sorted(Ordering.Int.reverse)
    .take(3)
    .sum
  }.getOrElse(0)
