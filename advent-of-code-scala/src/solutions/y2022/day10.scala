/// Day 10: Cathode-Ray Tube
///
///
package solutions.y2022.day10

import solutions.y2022.year
import harness.*

import core.parser.prelude.*

import parsley.*
import parsley.character.*
import parsley.combinator.*
import parsley.errors.combinator.*

given day: Day = Day(10)

object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// CRT PROGRAM                                                                                                        //
//====================================================================================================================//

enum Instruction {
  case Noop
  case Addx(value: Int)

  def toInt: Int = this match {
    case Noop    => 0
    case Addx(x) => x
  }

  def preprendNoops: List[Instruction] = this match {
    case Noop    => List(Noop)
    case Addx(x) => List(Noop, Addx(x))
  }
}

object Instruction {
  val p: Parsley[Instruction] = choice(
    string("noop") #> Noop,
    (string("addx") *> space *> int32).map { Addx(_) }
  )
}

val pProgram: Parsley[List[Instruction]] = sepByEol1(Instruction.p)

def runProgram(xs: List[Instruction]): List[Int] = {
  import Instruction.*

  def addToRegister(acc: Int, inst: Instruction): Int = inst match {
      case Noop    => acc
      case Addx(x) => acc + x
  }
  
  xs.scanLeft(1) { addToRegister(_, _) }
}

//====================================================================================================================//
// LOCATING INTERESTING SIGNALS                                                                                       //
//====================================================================================================================//

def signalStrength(n: Int, xs: List[Int]): Int = {
  val x = xs(n - 1)

  x * n
}

def interestingSignals(xs: List[Int]): Int = {
  List(20, 60, 100, 140, 180, 220)
    .map { signalStrength(_, xs) }
    .sum
}

//====================================================================================================================//
// RENDERING THE CRT SCREEN                                                                                           //
//====================================================================================================================//

def renderScreen(xs: List[Int]): String = {
  def toPixel(addr: Int, pos: Int): Char =
    if math.abs((addr % 40) - pos) <= 1 then '#' else '.'

  xs
    .zipWithIndex
    .map { (i, x) => toPixel(x, i) }
    .grouped(40)
    .map { _.mkString }
    .mkString("\n")
}

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

// => 14240
def part1(input: String): Int = {
  val res = pProgram.parse(input)

  res match {
    case Success(program) => {
      val prog = program.map { _.preprendNoops }.flatten

      val finalState = runProgram(prog)

      interestingSignals(finalState)
    }
    case Failure(err) => 0
  }
}

// => "PLULKBZH"
def part2(input: String): Unit = {
  val res = pProgram.parse(input)

  res match {
    case Success(program) => {
      val prog = program.flatMap { _.preprendNoops }

      val finalState = runProgram(prog)

      val screen = renderScreen(finalState)

      println(screen)
    }
    case Failure(err) => ()
  }
}

/*
###..#....#..#.#....#..#.###..####.#..#.
#..#.#....#..#.#....#.#..#..#....#.#..#.
#..#.#....#..#.#....##...###....#..####.
###..#....#..#.#....#.#..#..#..#...#..#.
#....#....#..#.#....#.#..#..#.#....#..#.
#....####..##..####.#..#.###..####.#..#.
*/
