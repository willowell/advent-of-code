/// Day 9: Rope Bridge
///
///
package solutions.y2022.day09

import solutions.y2022.year
import harness.*

import core.data.linear.v2.*
import core.parser.prelude.*

import parsley.*
import parsley.character.*
import parsley.combinator.*
import parsley.errors.combinator.*

given day: Day = Day(9)

object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// A SNAKE BY ANY OTHER NAME                                                                                          //
//====================================================================================================================//

enum Direction {
  case Right, Down, Left, Up
}

type Position = V2[Int]

type Rope = List[Position]

extension (lhs: V2[Int]) {
  def dx(rhs: V2[Int]): Int = lhs.x - rhs.x

  def dy(rhs: V2[Int]): Int = lhs.y - rhs.y

  def moveXTowards(rhs: V2[Int]): Int = rhs.x + math.signum(lhs.dx(rhs))

  def moveYTowards(rhs: V2[Int]): Int = rhs.y + math.signum(lhs.dy(rhs))

  def isAdjacent(rhs: V2[Int]): Boolean = math.abs(lhs.dx(rhs)) <= 1 && math.abs(lhs.dy(rhs)) <= 1
}

val pDir: Parsley[Direction] = choice(
  char('U') #> Direction.Up,
  char('R') #> Direction.Right,
  char('D') #> Direction.Down,
  char('L') #> Direction.Left,
)

val pMove: Parsley[List[Direction]] = for {
  dir <- pDir
  _ <- space
  len <- int32
} yield (List.fill(len) { dir })

//====================================================================================================================//
// MOVING THE ROPE                                                                                                    //
//====================================================================================================================//

def startingState(length: Int): Rope = List.fill(length) { V2.zero }

def uniquePositions(ropes: List[Rope]): Int = ropes.map(_.last).toSet.size

def moveHead(rope: Rope, dir: Direction): Rope = {
  import Direction.*

  (rope, dir) match {
    case (Nil, _)          => List()
    case (hd :: tl, Up)    => (hd.upOne) :: tl
    case (hd :: tl, Down)  => (hd.downOne) :: tl
    case (hd :: tl, Left)  => (hd.leftOne) :: tl
    case (hd :: tl, Right) => (hd.rightOne) :: tl
  }
}

def moveTail(rope: Rope): Rope = {
  rope match {
    case Nil            => List()
    case hd :: Nil      => List(hd)
    case hd :: nt :: tl => {
      val newX = hd.moveXTowards(nt)
      val newY = hd.moveYTowards(nt)
      val newTail = V2(newX, newY)

      if hd.isAdjacent(nt)
        then hd :: moveTail(nt :: tl)
        else hd :: moveTail(newTail :: tl)
    }
  }
}

import cats.*
import cats.data.*
import cats.implicits.*
import cats.syntax.all.*

def move(dir: Direction): State[Rope, Rope] = for {
  r <- State.get[Rope]

  r2 = moveHead(r, dir)

  r3 = moveTail(r2)

  _ <- State.set(r3)
} yield r3

def runSimulation(n: Int, dirs: List[Direction]): List[Rope] = {
  val s = startingState(n)

  // The answer is always traverse.
  val r = dirs.traverse { move }

  r.run(s).value._2
}

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Int = { // => 5779
  val res = sepByEol1(pMove).parse(input)

  res match {
    case Success(moves) => {
      val finalState = runSimulation(2, moves.flatten)

      uniquePositions(finalState)
    }
    case Failure(err) => 0
  }
}

def part2(input: String): Int = { // => 2331
  val res = sepByEol1(pMove).parse(input)

  res match {
    case Success(moves) => {
      val finalState = runSimulation(10, moves.flatten)

      uniquePositions(finalState)
    }
    case Failure(err) => 0
  }
}
