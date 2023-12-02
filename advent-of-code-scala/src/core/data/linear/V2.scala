package core.data.linear.v2

import monocle.{Getter, Lens}

opaque type V2[@specialized(Int, Double, Boolean) A] = (A, A)

import Numeric.Implicits.*

extension [@specialized(Int, Double, Boolean) A](lhs: V2[A])(using num: Numeric[A]) {
  def x: A = lhs._1
  def y: A = lhs._2

  def +(k: A): V2[A]       = V2(lhs.x + k, lhs.y + k)
  def +(rhs: V2[A]): V2[A] = V2(lhs.x + rhs.x, lhs.y + rhs.y)

  def *(k: A): V2[A]   = V2(lhs.x * k, lhs.y * k)
  def *(rhs: V2[A]): A = dot(rhs)

  infix def dot(rhs: V2[A]): A = (lhs.x * rhs.x) + (lhs.y * rhs.y)

  infix def euclideanDistanceTo(rhs: V2[A]): Double = {
    val dx = (rhs.x - lhs.x).toDouble
    val dy = (rhs.y - lhs.y).toDouble

    math.sqrt(math.pow(dx, 2) + math.pow(dy, 2))
  }

  infix def manhattanDistanceTo(rhs: V2[A]): Int = {
    val dx = (lhs.x - rhs.x).toInt
    val dy = (lhs.y - rhs.y).toInt

    dx + dy
  }

  def l1norm: Double = math.abs(lhs.x.toDouble) + math.abs(lhs.y.toDouble)

  def l2norm: Double = math.sqrt(math.pow(lhs.x.toDouble, 2) + math.pow(lhs.y.toDouble, 2))

  def magnitude: Double = lhs.l2norm

  def cossim(rhs: V2[A]): Double = (lhs * rhs).toDouble / (lhs.magnitude * rhs.magnitude)

  def radians(rhs: V2[A]): Double = math.acos(lhs.cossim(V2.right))

  infix def radiansBetween(rhs: V2[A]): Double = math.acos(lhs.cossim(rhs))

  def upOne: V2[A]    = lhs + V2.up
  def neOne: V2[A]    = lhs + V2.ne
  def rightOne: V2[A] = lhs + V2.right
  def seOne: V2[A]    = lhs + V2.se
  def downOne: V2[A]  = lhs + V2.down
  def swOne: V2[A]    = lhs + V2.sw
  def leftOne: V2[A]  = lhs + V2.left
  def nwOne: V2[A]    = lhs + V2.nw

  def neighboursOrdinals: List[V2[A]] = V2.ordinals.map { dir => lhs + dir }
  def neighboursAllDirections: List[V2[A]] = V2.allDirections.map { dir => lhs + dir }
}

object V2 {
  def apply[@specialized(Int, Double, Boolean) A](x: A, y: A): V2[A] = (x, y)

  def zero [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (num.zero, num.zero)
  def one  [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (num.one, num.one)

  def up    [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (num.zero, num.one)
  def ne    [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (num.one, num.one)
  def right [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (num.one, num.zero)
  def se    [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (num.one, -num.one)
  def down  [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (num.zero, -num.one)
  def sw    [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (-num.one, -num.one)
  def left  [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (-num.one, num.zero)
  def nw    [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V2[A] = (-num.one, num.one)

  def ordinals      [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): List[V2[A]] = List(up, right, down, left)
  def diagonals     [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): List[V2[A]] = List(ne, se, sw, nw)
  def allDirections [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): List[V2[A]] = ordinals ++ diagonals

  object LensSyntax {
    def _x[@specialized(Int, Double, Boolean) A](using num: Numeric[A]) = Lens[V2[A], A]((v: V2[A]) => v.x)(n => (_, vy) => (n, vy))
    def _y[@specialized(Int, Double, Boolean) A](using num: Numeric[A]) = Lens[V2[A], A]((v: V2[A]) => v.y)(n => (vx, _) => (vx, n))

    def upOne    [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _y.modify(_ + num.one)(v) // v & _y +~ 1
    def rightOne [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _x.modify(_ + num.one)(v) // v & _x +~ 1
    def downOne  [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _y.modify(_ - num.one)(v) // v & _y -~ 1
    def leftOne  [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _x.modify(_ - num.one)(v) // v & _x -~ 1

    def diff[@specialized(Int, Double, Boolean) A](
      v: V2[A],
      w: V2[A],
      f: Getter[V2[A], A]
    )(using num: Numeric[A]): A = f.get(v) - f.get(w)
  }
}
