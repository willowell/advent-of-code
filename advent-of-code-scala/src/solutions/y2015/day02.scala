/// Day 2: I Was Told There Would Be No Math
///
///
package solutions.y2015.day02

import solutions.y2015.year

import harness.*
import common.*

given day: Day = Day(2)

object run extends AocDayRunner(part1, part2)

case class Present(l: Int, w: Int, h: Int) {
  val sideA = l * w
  val sideB = w * h
  val sideC = h * l

  def volume: Int = l * w * h

  def surfaceArea: Int = 2 * sideA + 2 * sideB + 2 * sideC

  def shortestPerimeter: Int = allDimensions.sorted.take(2).map { x => x + x }.sum

  def slack: Int = allSides.min

  def allDimensions: List[Int] = List(l, w, h)

  def allSides: List[Int] = List(sideA, sideB, sideC)
}

object Present {
  val parser: Parsley[Present] = plift.lift3(Present.apply, int32 <* char('x'), int32 <* char('x'), int32)
}

def part1(input: String): Int = sepByEol1(Present.parser).parse(input)
  .map { xs => xs.map { x => x.surfaceArea + x.slack }.sum }
  .getOrElse(0)

def part2(input: String): Int = sepByEol1(Present.parser).parse(input)
  .map { xs => xs.map { x => x.volume + x.shortestPerimeter }.sum }
  .getOrElse(0)
