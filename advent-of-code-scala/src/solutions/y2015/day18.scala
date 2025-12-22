/// Day 18: Like a GIF For Your Yard
///
///
package solutions.y2015.day18

import solutions.y2015.year

import harness.*

import core.data.linear.v2.*
import core.parser.prelude.*

import fs2.{Pure, Stream}

import parsley.*

import scala.concurrent.duration.{FiniteDuration, *}

given day: Day = Day(18)

object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// I CAN'T BELIEVE IT'S NOT CELLULAR AUTOMATA!                                                                        //
//====================================================================================================================//

enum Condition {
  case Alive
  case Dead
  case Immortal

  def isAlive: Boolean = this != Dead

  override def toString: String = this match {
    case Alive    => "#"
    case Immortal => "*"
    case Dead     => "."
  }
}

case class World(
  cells: Map[V2[Int], Condition],
  width: Int,
  height: Int,
) {
  def withImmortalCorners: World = {
    val corners = List(V2(0, 0), V2(width - 1, 0), V2(0, height - 1), V2(width - 1, height - 1))

    val newCells = corners.foldLeft(cells) { (acc, cur) => acc.updated(cur, Condition.Immortal) }

    World(newCells, width, height)
  }

  def next: World = {
    val newCells = cells.map { (pos: V2[Int], cell: Condition) =>
      if cell == Condition.Immortal
        then (pos -> cell)
        else if World.survives(pos, cells)
          then (pos -> Condition.Alive)
          else (pos -> Condition.Dead)
    }

    World(newCells, width, height)
  }

  def run(iterations: Int = 50, delay: FiniteDuration = 100.millis): Unit = {
    val action = { (board: World) =>
      println(board)
      println("=".repeat(board.width))
      Thread.sleep(delay.toMillis)
      World.next(board)
    }

    val _ = LazyList.iterate(this)(action).take(iterations).force
  }

  override def toString: String = cells.toSeq
    .sortWith((a: (V2[Int], Condition), b: (V2[Int], Condition)) => a._1.x.compareTo(b._1.x.nn) < 0)
    .sortWith((a: (V2[Int], Condition), b: (V2[Int], Condition)) => a._1.y.compareTo(b._1.y.nn) < 0)
    .map(_._2.toString)
    .mkString
    .grouped(width)
    .mkString("\n")
}

object World {
  def apply(percentage: Int = 30, width: Int = 50, height: Int = 30): World = {
    val elements = for {
      y <- 0 until height
      x <- 0 until width
    } yield {
      if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
        (V2[Int](x, y) -> Condition.Dead)
      } else {
        V2[Int](x, y) -> (if (Math.random() <= percentage / 100.0) Condition.Alive else Condition.Dead)
      }
    }

    World(elements.toMap, width, height)
  }

  def apply(xss: Seq[Seq[Condition]]): World = {
    val height = xss.length
    val width = xss.head.length

    val elements = xss.zipWithIndex.flatMap { (xs, j) =>
      xs.zipWithIndex.map { (x, i) =>
        (V2(i, j) -> x)
      }
    }

    World(elements.toMap, width, height)
  }

  @annotation.targetName("applyFromSeqSeqChar")
  def apply(css: List[List[Char]]): World = {
    val world = css.map { _.map {
      case '#' => Condition.Alive
      case '*' => Condition.Immortal
      case _   => Condition.Dead
    } }

    World(world)
  }

  def neighboursAlive(neighbours: Seq[V2[Int]], cells: Map[V2[Int], Condition]): Int = {
    neighbours.map { neighbour =>
      cells.getOrElse(neighbour, Condition.Dead /* there is only death beyond the void */)
    }.count { _.isAlive }
  }

  def isBorderOfWorld(width: Int, height: Int, pos: V2[Int]) = {
    pos.x == 0 || pos.x == width - 1 || pos.y == 0 || pos.y == height - 1
  }

  def survives(pos: V2[Int], cells: Map[V2[Int], Condition]) = {
    val currentCell = cells(pos)

    if (currentCell != Condition.Immortal) {
      val aliveNeighbours = neighboursAlive(pos.neighboursAllDirections, cells)

      (currentCell == Condition.Alive && (aliveNeighbours == 2 || aliveNeighbours == 3)) || aliveNeighbours == 3
    } else {
      true
    }
  }

  def next(current: World) = {
    val newCells = current.cells.map { (pos: V2[Int], cell: Condition) =>
      if cell == Condition.Immortal
        then (pos -> cell)
        else if survives(pos, current.cells)
          then (pos -> Condition.Alive)
          else (pos -> Condition.Dead)
    }

    current.copy(cells = newCells)
  }
}

def worldStream(init: World): Stream[Pure, World] = {
  lazy val states: Stream[Pure, World] = Stream.iterate(init) { _.next }

  states
}

def getPopulationWithSteps(input: String, steps: Int, areCornersImmortal: Boolean = false): Int = {
  val xss = splitByEol(input).map { _.toList }

  worldStream(if !areCornersImmortal then World(xss) else World(xss).withImmortalCorners)
    .take(steps)
    .toList
    .map { _.cells.count { (_, v) => v.isAlive } }
    .last
}

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

val STEPS = 101

def part1(input: String): Int = getPopulationWithSteps(input, STEPS) // => 814

def part2(input: String): Int = getPopulationWithSteps(input, STEPS, areCornersImmortal = true) // => 924
