/// Day 8: Treetop Tree House
///
///
package solutions.y2022.day08

import solutions.y2022.year
import harness.*

//import core.data.linear.v2.*
import core.parser.prelude.*

import cats.*
import cats.data.*
import cats.implicits.*
import cats.syntax.all.*

import monocle.*
import monocle.syntax.all.*

import parsley.*
import parsley.character.*
import parsley.combinator.*

given day: Day = Day(8)

object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// MY KINGDOM FOR A FOREST                                                                                            //
//====================================================================================================================//

case class Grid2D(
  width: Int,
  height: Int,
  field: Vector[Vector[Int]]
) {
  def perimeter: Int = 2 * (width + height)
  def perimeterUnique: Int = perimeter - 4
  def area: Int = width * height

  def allCoords = for {
    y <- field.indices
    x <- field(y).indices
  } yield (x, y)

  def gridPoint(x: Int, y: Int) =
    field
      .get(y)
      .flatMap { _.get(x) }

  def adjacents(x: Int, y: Int) = for {
    dx <- -1 to 1
    dy <- -1 to 1
  } yield gridPoint(x + dx, y + dy)

  def adjacentsOrdinal(x: Int, y: Int) = for {
    dx <- -1 to 1
    dy <- -1 to 1
    if (math.abs(dx + dy) == 1)
  } yield gridPoint(x + dx, y + dy)

  def ray(sx: Int, sy: Int, dx: Int, dy: Int) = {
    def go(x: Int, y: Int): List[Int] = gridPoint(x, y) match {
      case None    => List()
      case Some(v) => v +: go(x + dx, y + dy)
    }
    
    go(sx, sy)
  }

  def rightRay (x: Int, y: Int): List[Int] = ray(x, y, 1, 0).tail
  def leftRay  (x: Int, y: Int): List[Int] = ray(x, y, -1, 0).tail
  def downRay  (x: Int, y: Int): List[Int] = ray(x, y, 0, 1).tail
  def upRay    (x: Int, y: Int): List[Int] = ray(x, y, 0, -1).tail

  def ordinalRays(x: Int, y: Int): List[List[Int]] = List(rightRay(x, y), leftRay(x, y), downRay(x, y), upRay(x, y))

  def isPointVisible(x: Int, y: Int): Option[Boolean] = for {
    h <- gridPoint(x, y)

    rays = ordinalRays(x, y)
  } yield (rays.exists { _.forall { _ < h } })

  def getScenicScoreOfPoint(x: Int, y: Int): Option[Int] = for {
    h <- gridPoint(x, y)

    rays = ordinalRays(x, y)

    raysScores = rays.map { Grid2D.getScenicScoreOfRay(_, h) }
  } yield raysScores.product

  def countVisiblePoints: Int = allCoords.count { (x, y) => isPointVisible(x, y).getOrElse { false } }

  def mostScenicPointScore: Int = allCoords.map { (x, y) => getScenicScoreOfPoint(x, y).getOrElse(0) }.max
}

object Grid2D {
  def apply(xss: List[List[Int]]): Grid2D = {
    val height = xss.length
    val width = xss(0).length

    val field = xss.map(_.toVector).toVector

    Grid2D(width, height, field)
  }

  def getScenicScoreOfRay(xs: List[Int], height: Int): Int = {
    val shorter = xs.takeWhile { _ < height }

    if shorter.length == xs.length
      then shorter.length
      else shorter.length + 1
  }

  object LensSyntax {
    def _field = Lens[Grid2D, Vector[Vector[Int]]]((g => g.field))(f => g => Grid2D(g.width, g.height, f))

    def _gridPoint(g: Grid2D, x: Int, y: Int) = _field
      .index(y)
      .index(x)
      .getOption(g)
  }
}

val exampleGrid = List(
  List(3, 0, 3, 7, 3),
  List(2, 5, 5, 1, 2),
  List(6, 5, 3, 3, 2),
  List(3, 3, 5, 4, 9),
  List(3, 5, 3, 9, 0),
)

val testGrid = List(
  List(1, 2, 3),
  List(4, 5, 6),
  List(7, 8, 9),
)

val testGrid2 = List(
  List( 1,  2,  3,  4,  5),
  List( 6,  7,  8,  9, 10),
  List(11, 12, 13, 14, 15),
  List(16, 17, 18, 19, 20),
  List(21, 22, 23, 24, 25),
)

val testGrid3 = List(
  List(7, 1, 3, 1, 7),
  List(1, 9, 1, 9, 1),
  List(3, 1, 5, 1, 3),
  List(1, 9, 1, 9, 1),
  List(7, 1, 3, 1, 7),
)

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Int = { // => 1776
  val res = sepByEol1(Parsley.some(digit.map { _.toString.toInt })).parse(input)

  res match {
    case Success(field) => {
      val g = Grid2D(field)

      g.countVisiblePoints
    }
    case Failure(err) => 0
  }
}

def part2(input: String): Int = { // => 234416
  val res = sepByEol1(Parsley.some(digit.map { _.toString.toInt })).parse(input)

  res match {
    case Success(field) => {
      val g = Grid2D(field)

      g.mostScenicPointScore
    }
    case Failure(err) => 0
  }
}
