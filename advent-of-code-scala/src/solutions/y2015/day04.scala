/// Day 4: The Ideal Stocking Stuffer
///
///
package solutions.y2015.day04

import solutions.y2015.year

import harness.*

import core.crypto.md5.MD5
import core.data.infinitelists.naturalsIt

given day: Day = Day(4)

object run extends AocDayRunner(part1, part2)

def getFirstNaturalWithHashPrefix(s: String, prefix: String): Option[Int] = naturalsIt.find { n =>
  val hash = MD5.hash(s"$s$n")

  hash.startsWith(prefix)
}

def part1(input: String): Int = {
  val secretKey = input.trim.nn

  getFirstNaturalWithHashPrefix(secretKey, "00000").getOrElse { 0 }
}

def part2(input: String): Int = {
  val secretKey = input.trim.nn

  getFirstNaturalWithHashPrefix(secretKey, "000000").getOrElse { 0 }
}
