package core.data.linear.v3

import monocle.{Getter, Lens}

opaque type V3[@specialized(Int, Double, Boolean) A] = (A, A, A)

import Numeric.Implicits.*

extension [@specialized(Int, Double, Boolean) A](lhs: V3[A])(using num: Numeric[A]) {
  def x: A = lhs._1
  def y: A = lhs._2
  def z: A = lhs._3

  def +(k: A): V3[A]       = V3(lhs.x + k, lhs.y + k, lhs.z + k)
  def +(rhs: V3[A]): V3[A] = V3(lhs.x + rhs.x, lhs.y + rhs.y, lhs.z + rhs.z)

  def neighboursOrdinals: List[V3[A]] = V3.ordinals.map { dir => lhs + dir }
}

object V3 {
  def apply[@specialized(Int, Double, Boolean) A](x: A, y: A, z: A): V3[A] = (x, y, z)

  def zero [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V3[A] = (num.zero, num.zero, num.zero)
  def one  [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V3[A] = (num.one, num.one, num.one)

  def right   [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V3[A] = /* (+1, 0, 0) */ (num.one, num.zero, num.zero)
  def left    [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V3[A] = /* (-1, 0, 0) */ (-num.one, num.zero, num.zero)
  def up      [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V3[A] = /* (0, +1, 0) */ (num.zero, num.one, num.zero)
  def down    [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V3[A] = /* (0, -1, 0) */ (num.zero, -num.one, num.zero)
  def forward [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V3[A] = /* (0, 0, +1) */ (num.zero, num.zero, num.one)
  def back    [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): V3[A] = /* (0, 0, -1) */ (num.zero, num.zero, -num.one)

  def ordinals [@specialized(Int, Double, Boolean) A](using num: Numeric[A]): List[V3[A]] = List(right, left, up, down, forward, back)

  given v3Ordering[A](using num: Numeric[A]): Ordering[V3[A]] = Ordering.by[V3[A], A](_._1).orElseBy(_._2).orElseBy(_._3)

  given v3Ordered[A](using x: V3[A], num: Numeric[A]): scala.math.Ordered[V3[A]] = scala.math.Ordered.orderingToOrdered(x)(v3Ordering)

  object LensSyntax {
    def _x[@specialized(Int, Double, Boolean) A](using num: Numeric[A]) = Lens[V3[A], A]((v: V3[A]) => v.x)(n => (_, vy, vz) => (n, vy, vz))
    def _y[@specialized(Int, Double, Boolean) A](using num: Numeric[A]) = Lens[V3[A], A]((v: V3[A]) => v.y)(n => (vx, _, vz) => (vx, n, vz))
    def _z[@specialized(Int, Double, Boolean) A](using num: Numeric[A]) = Lens[V3[A], A]((v: V3[A]) => v.z)(n => (vx, vy, _) => (vx, vy, n))

    // def upOne    [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _y.modify(_ + num.one)(v) // v & _y +~ 1
    // def rightOne [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _x.modify(_ + num.one)(v) // v & _x +~ 1
    // def downOne  [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _y.modify(_ - num.one)(v) // v & _y -~ 1
    // def leftOne  [@specialized(Int, Double, Boolean) A](v: V2[A])(using num: Numeric[A]): V2[A] = _x.modify(_ - num.one)(v) // v & _x -~ 1

    // def diff[@specialized(Int, Double, Boolean) A](
    //   v: V2[A],
    //   w: V2[A],
    //   f: Getter[V2[A], A]
    // )(using num: Numeric[A]): A = f.get(v) - f.get(w)
  }
}