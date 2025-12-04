package core.data.moduloring

import cats.*
import cats.implicits.*
import cats.syntax.all.*

import spire.*
import spire.math.*
import spire.implicits.*
import spire.syntax.*

final case class ModN[I](value: I, modulus: I)(using ev: Integral[I]) {
  val normalized: I = {
    val rem = Integral[I].emod(value, modulus)

    if rem < 0  then ev.plus(rem, modulus) else rem
  }

  override def toString: String = s"ModN($normalized, $modulus)"
}

object ModN {
  def apply[I](value: I, modulus: I)(using ev: Integral[I]): Option[ModN[I]] =
    if (ev.gt(modulus, Integral[I].zero)) Some(new ModN(value, modulus))
    else None

  given eqModN[I: Integral]: Eq[ModN[I]] = Eq.instance { (a, b) =>
    a.modulus == b.modulus && a.normalized == b.normalized
  }

  given showModN[I: Integral]: Show[ModN[I]] = Show.fromToString

  def monoidModN[I: Integral](modulus: I): Monoid[ModN[I]] = new Monoid[ModN[I]] {
    override final def empty: ModN[I] = ModN(Integral[I].zero, modulus).get
    override final def combine(a: ModN[I], b: ModN[I]): ModN[I] =
      ModN(Integral[I].plus(a.normalized, b.normalized), modulus).get
  }

  // Group instance for subtraction
  def groupModN[I: Integral](modulus: I): Group[ModN[I]] = new Group[ModN[I]] {
    override final def combine(a: ModN[I], b: ModN[I]): ModN[I] =
      monoidModN(modulus).combine(a, b)
    override final def empty: ModN[I] = monoidModN(modulus).empty
    override final def inverse(a: ModN[I]): ModN[I] =
      ModN(Integral[I].minus(modulus, a.normalized), modulus).get
  }

  // Implicit class for operator syntax
  extension [I: Integral](a: ModN[I]) {
    def +(b: ModN[I])(using ev: Monoid[ModN[I]]): ModN[I] = ev.combine(a, b)

    def -(b: ModN[I])(using ev: Group[ModN[I]]): ModN[I] = ev.combine(a, ev.inverse(b))
  }

  given monoidInstance[I: Integral](using modulus: I): Monoid[ModN[I]] = monoidModN(modulus)

  given groupInstance[I: Integral](using modulus: I): Group[ModN[I]] = groupModN(modulus)
}

given intAdditionMonoid: Monoid[Int] = new Monoid[Int] {
  override final def empty: Int = 0
  override final def combine(x: Int, y: Int): Int = x + y
}

import ModN.{given}

given mod5: Int = 5

val mod5a = ModN(7, 5).get

val mod5b = ModN(4, 5).get

val sum: ModN[Int] = mod5a + mod5b
