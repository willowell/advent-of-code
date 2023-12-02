/// Day 2: Cube Conundrum
///
///
package solutions.y2023.day02

import solutions.y2023.year

import harness.*

import core.parser.prelude.*

import parsley.*
import parsley.character.*
import parsley.combinator.*
import parsley.errors.combinator.*

given day: Day = Day(2)

object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
//                                                                                                                    //
//====================================================================================================================//

val maxs = Map(
  "red" -> 12,
  "green" -> 13,
  "blue" -> 14,
)

case class Game(
  id: Int,
  sets: List[Map[String, Int]]
) {
  val isPossible: Boolean = sets.forall { s =>
    !s.exists { (k, v) => maxs(k) < v }  
  }

  val combinedSet = sets.flatten.groupMapReduce { _._1 } { _._2 } { _ + _ }

  val minimumSet = sets.flatten.groupBy { _._1 }.values.map { _.maxBy { _._2 } }.toMap

  val power = minimumSet.values.product
}

object Game {
  val cubeSet: Parsley[Map[String, Int]] = sepBy1(
    (for n <- int32 <* space; color <- word yield (color -> n)),
    string(", ")
  ).map { _.toMap }

  val p: Parsley[Game] = for {
    _ <- string("Game ")
    id <- int32 <* colon <* space
    sets <- sepBy1(cubeSet, string("; "))
  } yield Game(id, sets)
}

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Int = { // => 2101
  val res = sepByEol1(Game.p).parse(input)

  res.map { games =>
    games.filter { _.isPossible }.map { _.id }.sum
  }.getOrElse(0)
}

def part2(input: String): Int = { // => 58269
  val res = sepByEol1(Game.p).parse(input)

  res.map { games =>
    games.map { _.power }.sum
  }.getOrElse(0)
}
