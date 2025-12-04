/// Day 1: Secret Entrance
///
///
package solutions.y2025.day01

import solutions.y2025.year

import harness.*

import core.control.combinators.*
import core.data.moduloring.*
import core.parser.prelude.*

import parsley.*
import parsley.Parsley.*
import parsley.character.*
import parsley.errors.combinator.*
import parsley.combinator.*

given day: Day = Day(1)
object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// SETUP                                                                                                              //
//====================================================================================================================//



//====================================================================================================================//
// PARSING                                                                                                            //
//====================================================================================================================//

val pDialRotation: Parsley[Int] = choice(
  string("L") *> int32.map(-_),
  string("R") *> int32,
)

val pDialRotations: Parsley[List[Int]] = sepByEol1(pDialRotation)

//====================================================================================================================//
// TEST INPUT                                                                                                         //
//====================================================================================================================//

val testInput =
  """L68
    |L30
    |R48
    |L5
    |R60
    |L55
    |L1
    |L99
    |R14
    |L82""".stripMargin

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Int = { // => 1097
  given mod99: Int = 100

  val res = pDialRotations.parse(input)

  res match {
    case Success(rotations) => {
      val scanned = rotations
        .map(ModN(_, mod99).get)
        .scanLeft(ModN(50, mod99).get)(_ + _)

      scanned.count { _.normalized == 0 }
    }
    case Failure(_) => 0
  }
}

def part2(input: String): Int = {
  given mod99: Int = 100

  val res = pDialRotations.parse(input)

  res match {
    case Success(rotations) => {
      println("Parsed dial rotations:")
      rotations.foreach(println)
      println("================")

      val scanned = rotations
        .map(ModN(_, mod99).get)
        .scanLeft(ModN(50, mod99).get)(_ + _)
      
      println("Scanned dial rotations using ModN:")
      scanned.foreach(println)
      println("================")

      scanned.count { _.normalized == 0 }
    }
    case Failure(_) => 0
  }

  0
}
