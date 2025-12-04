/// Day 1: Report Repair
///
///
package solutions.y2020.day01

import solutions.y2020.year

import harness.*

given day: Day = Day(1)

object run extends AocDayRunner(part1, part2)

def part1(input: String): Int = {
  val xs = input.linesIterator.map(_.toInt).toList

  val res = for {
    a <- xs
    b <- xs
    if (a + b) == 2020
  } yield a * b

  res(0)
}


def part2(input: String): Int = {
  val xs = input.linesIterator.map(_.toInt).toList

  val res = for {
    a <- xs
    b <- xs
    c <- xs
    if (a + b + c) == 2020
  } yield a * b * c

  res(0)
}
