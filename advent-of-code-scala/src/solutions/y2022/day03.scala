/// Day 3: Rucksack Reorganization
///
/// AKA an unsolicited advertisement for AirTags!
///
package solutions.y2022.day03

import solutions.y2022.year
import harness.*

import core.parser.prelude.*
import core.data.seq.*

import parsley.*

given day: Day = Day(3)

object run extends AocDayRunner(part1, part2)

def toPriority(c: Char): Int = math.floorMod(c.toInt - 96, 58)

def part1(input: String): Int = {
  val res = sepByEol1(word).parse(input)

  res match {
    case Success(xs) => {
      val halves = xs.map { splitHalf }

      val intersections = halves
        .map { (x, y) => x.intersect(y) }
        .map { _.head } // Remove duplicates

      intersections.map { toPriority }.sum
    }
    case Failure(_) => 0
  }
}


def part2(input: String): Int = {
  val res = sepByEol1(word).parse(input)

  res match {
    case Success(xs) => {
      val thirds = xs.grouped(3).toList

      val intersections = thirds
        .map { x =>
          x.reduceRight { (cur, acc) =>
            cur.intersect(acc)
          }
        }

      val priorityChars = intersections
        .map { _.head } // Remove duplicates

      priorityChars.map { toPriority }.sum
    }
    case Failure(_) => 0
  }
}
