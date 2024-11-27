package core.data.linear.v2

import monocle.{Getter, Lens}

/** 2D Vector */
opaque type V2[@specialized(Int, Double, Boolean) A] = (A, A)

import Numeric.Implicits.*

extension [@specialized(Int, Double, Boolean) A](lhs: V2[A])(using num: Numeric[A]) {
  def x: A = lhs._1
  def y: A = lhs._2

  /** Scalar addition: add `k` to each element in `V2[A]`. */
  def +(k: A): V2[A] = V2(lhs.x + k, lhs.y + k)

  /** Vector addition between this `V2[A]` and `rhs`. */
  def +(rhs: V2[A]): V2[A] = V2(lhs.x + rhs.x, lhs.y + rhs.y)

  /** Compute x delta between `this` and `rhs`. */
  def diffX(rhs: V2[A]): A = rhs.x - lhs.x

  /** Compute y delta between `this` and `rhs`. */
  def diffY(rhs: V2[A]): A = rhs.y - lhs.y

  /** Compute x delta between `this` and `rhs`. */
  infix def dx(rhs: V2[A]): A = diffX(rhs)

  /** Compute y delta between `this` and `rhs`. */
  infix def dy(rhs: V2[A]): A = diffY(rhs)

  /** Scalar multiplication: multiply each element in `V2[A]` by `k`. */
  def *(k: A): V2[A] = V2(lhs.x * k, lhs.y * k)

  /** Vector dot product between this `V2[A]` and `rhs`. */
  def *(rhs: V2[A]): A = dot(rhs)

  /** Dot product between `this` and `rhs`. */
  infix def dot(rhs: V2[A]): A = (lhs.x * rhs.x) + (lhs.y * rhs.y)

  /** Euclidean distance from `this` to `rhs`. */
  infix def euclideanDistanceTo(rhs: V2[A]): Double = {
    math.sqrt(
      math.pow(diffX(rhs).toDouble, 2) + math.pow(diffY(rhs).toDouble, 2),
    )
  }

  /** Manhattan distance from `this` to `rhs`. */
  infix def manhattanDistanceTo(rhs: V2[A]): Int = {
    diffX(rhs).abs.toInt + diffY(rhs).abs.toInt
  }

  /** Compute the L1 norm of `this` 2D Vector. */
  def l1norm: Double = math.abs(lhs.x.toDouble) + math.abs(lhs.y.toDouble)

  /** Compute the L2 norm of `this` 2D Vector. */
  def l2norm: Double = math.sqrt(math.pow(lhs.x.toDouble, 2) + math.pow(lhs.y.toDouble, 2))

  /** Compute the magnitude of `this` 2D Vector. AKA `l2norm`. */
  def magnitude: Double = lhs.l2norm

  /** Compute cosine similarity between `this` and `rhs`. */
  def cossim(rhs: V2[A]): Double = (lhs * rhs).toDouble / (lhs.magnitude * rhs.magnitude)

  /** Compute radians between `this` and `rhs`. */
  infix def radiansBetween(rhs: V2[A]): Double = math.acos(lhs.cossim(rhs))

  /** Compute radians between `this` and `V2.right[A]`. */
  def radians: Double = lhs.radiansBetween(V2.right)

  /** Get 2D Vector (0, +1) */
  def upOne: V2[A] = lhs + V2.up
  /** Get 2D Vector (+1, +1) */
  def neOne: V2[A] = lhs + V2.ne
  /** Get 2D Vector (+1, 0) */
  def rightOne: V2[A] = lhs + V2.right
  /** Get 2D Vector (+1, -1) */
  def seOne: V2[A] = lhs + V2.se
  /** Get 2D Vector (0, -1) */
  def downOne: V2[A] = lhs + V2.down
  /** Get 2D Vector (-1, -1) */
  def swOne: V2[A] = lhs + V2.sw
  /** Get 2D Vector (-1, 0) */
  def leftOne: V2[A] = lhs + V2.left
  /** Get 2D Vector (-1, +1) */
  def nwOne: V2[A] = lhs + V2.nw

  /** Get 2D Vector neighbours in ordinal directions. */
  def neighboursOrdinals: List[V2[A]] = V2.ordinals.map { dir => lhs + dir }

  /** Get 2D Vector neighbours in all 8 directions. */
  def neighboursAllDirections: List[V2[A]] = V2.allDirections.map { dir => lhs + dir }
}

extension (lhs: V2[Int]) {
  def adjacents(rx: Int = 1, ry: Int = 1) = for {
    dx <- -rx to rx
    dy <- -ry to ry
  } yield V2[Int](lhs.x + dx, lhs.y + dy)
}

object V2 {
  def apply[@specialized(Int, Double, Boolean) A](x: A, y: A): V2[A] = (x, y)
  def apply[@specialized(Int, Double, Boolean) A](tp: (A, A)): V2[A] = (tp._1, tp._2)

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

  given v2Ordering[A](using num: Numeric[A]): Ordering[V2[A]] = Ordering.by[V2[A], A](_._1).orElseBy(_._2)

  given v2Ordered[A](using x: V2[A], num: Numeric[A]): math.Ordered[V2[A]] = math.Ordered.orderingToOrdered(x)(v2Ordering)

  /** Lens Syntax */
  object LensSyntax {
    def _x[@specialized(Int, Double, Boolean) A](using num: Numeric[A]) = Lens[V2[A], A](get = (v: V2[A]) => v.x)(replace = n => (_, vy) => (n, vy))
    def _y[@specialized(Int, Double, Boolean) A](using num: Numeric[A]) = Lens[V2[A], A](get = (v: V2[A]) => v.y)(replace = n => (vx, _) => (vx, n))

    def upOne    [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _y.modify(_ + num.one)(v) // v & _y +~ 1
    def rightOne [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _x.modify(_ + num.one)(v) // v & _x +~ 1
    def downOne  [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _y.modify(_ - num.one)(v) // v & _y -~ 1
    def leftOne  [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _x.modify(_ - num.one)(v) // v & _x -~ 1

    /** Diff Lens on two 2D Vectors `v` and `w`. */
    def diff[@specialized(Int, Double, Boolean) A](
      v: V2[A],
      w: V2[A],
      f: Getter[V2[A], A]
    )(using num: Numeric[A]): A = f.get(v) - f.get(w)
  }
}
