/// Day 2: Gift Shop
///
///
package solutions.y2025.day02

import solutions.y2025.year

import harness.*

import core.control.combinators.*
import core.data.seq.*
import core.parser.prelude.*

import parsley.*
import parsley.Parsley.*
import parsley.character.*
import parsley.errors.combinator.*
import parsley.combinator.*

import collection.immutable.Range

import util.matching.Regex
import scala.collection.immutable.NumericRange

given day: Day = Day(2)
object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// SETUP                                                                                                              //
//====================================================================================================================//

// (998 to 1012).map { x => val (l, r) = splitHalf(x.toString); if l == r then s"invalid: $x" else s"valid: $x" }.foreach(println)

def invalidsIn[A: math.Integral](r: NumericRange.Inclusive[A]) = r.filter { x =>
  val (l, r) = splitHalf(x.toString)

  l == r
}

//====================================================================================================================//
// PARSING                                                                                                            //
//====================================================================================================================//

val idRange: Parsley[NumericRange.Inclusive[Long]] = around(int64, int64, dash)
  .map { (rangeStart, rangeEnd) => rangeStart to rangeEnd }

val idRanges: Parsley[List[NumericRange.Inclusive[Long]]] = sepEndBy1(idRange, comma)

//====================================================================================================================//
// TEST INPUT                                                                                                         //
//====================================================================================================================//

val testInput = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Long = { // => 30608905813
  val res = idRanges.parse(input)

  println(res)

  res match {
    case Success(ranges) => {
      val invalidIds = ranges.flatMap(invalidsIn)

      invalidIds.sum
    }
    case Failure(_) => 0
  }
}

def part2(input: String): Int = {
  0
}
