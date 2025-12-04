/// Day 2: Red-Nosed Reports
///
///
package solutions.y2024.day02

import solutions.y2024.year

import harness.*

import core.parser.prelude.*

import parsley.*
import parsley.character.*
import parsley.combinator.*
import parsley.errors.combinator.*

given day: Day = Day(2)
object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// PARSING                                                                                                            //
//====================================================================================================================//



val report: Parsley[List[Int]] = sepBy1(int32, space)

//====================================================================================================================//
// TEST INPUT                                                                                                         //
//====================================================================================================================//

val safeReport = "7 6 4 2 1"

val unsafeReport = "1 2 7 8 9"

val testInput =
  """7 6 4 2 1
    |1 2 7 8 9
    |9 7 6 2 1
    |1 3 2 4 5
    |8 6 4 4 1
    |1 3 6 7 9""".stripMargin

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Int = {
  0
}

def part2(input: String): Int = {
  0
}
