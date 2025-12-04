/// Day 3: Mull It Over
///
///
package solutions.y2024.day03

import solutions.y2024.year

import harness.*

import util.matching.Regex

given day: Day = Day(3)
object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// PARSING                                                                                                            //
//====================================================================================================================//

val mulInstructionRegex = """mul\((\d+),(\d+)\)""".r

val ignoreRegex = """don't\(\).*?(?=do\(\)|\Z)""".r

def parseInput(input: String, ignoreStuffBetweenDontAndDo: Boolean = false): List[(Int, Int)] =
  mulInstructionRegex.findAllMatchIn(
    if !ignoreStuffBetweenDontAndDo
      then input
      else ignoreRegex.replaceAllIn(input.linesIterator.mkString, "IGNORED")
  )
    .toList
    .map { grp => (grp.group(1).toInt, grp.group(2).toInt) }


def processInstructions(xs: List[(Int, Int)]): Int = {
  xs.foldLeft(0) { case (acc, (x, y)) =>
    acc + (x * y)
  }
}

//====================================================================================================================//
// TEST INPUT                                                                                                         //
//====================================================================================================================//

val testInput =
  """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"""

val testInput2 =
  """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Int = { // => 157621318
  processInstructions(parseInput(input))
}

def part2(input: String): Int = { // => 79845780
  processInstructions(parseInput(input, ignoreStuffBetweenDontAndDo = true))
}
