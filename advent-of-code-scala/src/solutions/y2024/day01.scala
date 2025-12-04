/// Day 01: Historian Hysteria
///
///
package solutions.y2024.day01

import solutions.y2024.year
import harness.*

import core.data.freqmap.{*, given}
import core.parser.prelude.*

import util.matching.Regex

given day: Day = Day(1)
object run extends AocDayRunner(part1, part2WithFreqMap)

//====================================================================================================================//
// TEST INPUT                                                                                                         //
//====================================================================================================================//

val testInput =
  """3   4
    |4   3
    |2   5
    |1   3
    |3   9
    |3   3""".stripMargin

//====================================================================================================================//
// PARSING                                                                                                            //
//====================================================================================================================//

val numberPairRegex = """(\d+)\s+(\d+)""".r

def parseInput(input: String): (List[Int], List[Int]) =
  numberPairRegex.findAllMatchIn(input)
    .toList
    .map { grp => (grp.group(1).toInt, grp.group(2).toInt) }
    .unzip

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Int = { // => 1646452
  val (ls, rs) = parseInput(input)
  
  val recombined = ls.sorted.zip(rs.sorted)
  
  recombined.foldLeft(0) { case (acc, (x, y)) =>
    acc + math.abs(x - y)
  }
}

def part2(input: String): Int = { // => 23609874
  val (ls, rs) = parseInput(input)

  ls.foldLeft(0) { (acc, cur) => 
    acc + (cur * rs.count { _ == cur })
  }
}

def part2WithFreqMap(input: String): Int = { // => 23609874
  val (ls, rs) = parseInput(input)

  // FreqMap internally performs similar `rs.count { _ == cur }`.
  val fm = rs.toFreqMap

  ls.foldLeft(0) { (acc, cur) => 
    acc + (cur * fm.value.getOrElse(cur, 0))
  }
}
