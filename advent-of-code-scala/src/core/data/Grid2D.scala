package core.data.grid2d

import core.data.linear.v2.*

import cats.implicits.*

import monocle.*

/**
  * A 2D Grid of `width` x `height` size over some field of elements represented by `field`.
  *
  * @param width
  * @param height
  * @param field
  */
case class Grid2D[A](
  width: Int,
  height: Int,
  field: Vector[Vector[A]]
) {
  /** Perimeter of `this` 2D Grid. */
  def perimeter: Int = 2 * (width + height)
  def perimeterUnique: Int = perimeter - 4
  /** Area of `this` 2D Grid. */
  def area: Int = width * height

  /** Generate all possible coordinate positions based on indices in `field`. */
  def allCoords = for {
    y <- field.indices
    x <- field(y).indices
  } yield (x, y)

  /** Get the value at a point on `this` grid. */
  def gridPoint(x: Int, y: Int): Option[A] = field
    .get(y)
    .flatMap { _.get(x) }

  /** ***UNSAFE*** Get the value at a point on `this` grid. */
  def gridPointUnsafe(x: Int, y: Int): A = field
    .get(y)
    .flatMap { _.get(x) }.get

  def adjacents(x: Int, y: Int, rx: Int = 1, ry: Int = 1) = for {
    dx <- -rx to rx
    dy <- -ry to ry
  } yield gridPoint(x + dx, y + dy)

  def adjacentsOrdinal(x: Int, y: Int) = for {
    dx <- -1 to 1
    dy <- -1 to 1
    if (math.abs(dx + dy) == 1)
  } yield gridPoint(x + dx, y + dy)

  /** Cast a ray from (sx, sy) by (dx, dy) until the ray reaches the end of the 2D Grid, collecting hit points. */
  def ray(sx: Int, sy: Int, dx: Int, dy: Int): List[A] = {
    def go(x: Int, y: Int): List[A] = gridPoint(x, y) match {
      case None    => List()
      case Some(v) => v +: go(x + dx, y + dy)
    }
    
    go(sx, sy)
  }

  def rightRay (x: Int, y: Int): List[A] = ray(x, y, 1, 0).tail
  def leftRay  (x: Int, y: Int): List[A] = ray(x, y, -1, 0).tail
  def downRay  (x: Int, y: Int): List[A] = ray(x, y, 0, 1).tail
  def upRay    (x: Int, y: Int): List[A] = ray(x, y, 0, -1).tail

  def ordinalRays(x: Int, y: Int): List[List[A]] = List(rightRay(x, y), leftRay(x, y), downRay(x, y), upRay(x, y))
}

object Grid2D {
  /** Create a 2D Grid over `xss`. */
  def apply[A](xss: List[List[A]]): Grid2D[A] = {
    val height = xss.length
    val width = xss(0).length

    val field = xss.map(_.toVector).toVector

    Grid2D[A](width, height, field)
  }

  def bounding[A](topLeft: V2[Int], bottomRight: V2[Int]): Grid2D[A] = {
    val width = (bottomRight dx topLeft).abs
    val height = (bottomRight dy topLeft).abs

    Grid2D[A](width, height, Vector.empty)
  }

  object LensSyntax {
    def _field[A] = Lens[Grid2D[A], Vector[Vector[A]]](get = g => g.field)(replace = f => g => Grid2D(g.width, g.height, f))

    def _gridPoint[A](g: Grid2D[A], x: Int, y: Int) = _field
      .index(y)
      .index(x)
      .getOption(g)
  }
}
