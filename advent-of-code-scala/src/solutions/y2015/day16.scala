/// Day 16: Aunt Sue
///
///
package solutions.y2015.day16

import solutions.y2015.year

import harness.*

import core.parser.prelude.*

import parsley.*
import parsley.Parsley.*
import parsley.character.*
import parsley.errors.combinator.*
import parsley.combinator.*

given day: Day = Day(16)

object run extends AocDayRunner(part1, part2)

/** Things I Know about Aunt Sue */
type Tikaas = Map[String, Int]

val tiraas: Tikaas = Map(
  "children"    -> 3,
  "cats"        -> 7,
  "samoyeds"    -> 2,
  "pomeranians" -> 3,
  "akitas"      -> 0,
  "vizslas"     -> 0,
  "goldfish"    -> 5,
  "trees"       -> 3,
  "cars"        -> 2,
  "perfumes"    -> 1,
)

val pTikaas: Parsley[Tikaas] = for {
  _ <- string("Sue") <* space <* int32 <* colon <* space
  kvs <- sepEndBy1(for {
    k <- word
    _ <- colon <* space
    v <- int32
  } yield (k, v), comma <* space)
} yield Map(kvs*)

def score(lhs: Tikaas, rhs: Tikaas): Int = {
  val xs = for {
    (kl, vl) <- lhs
    vr <- rhs.get(kl)
  } yield (vr == vl)

  xs.count(identity)
}

def encabulate(lhs: Tikaas, rhs: Tikaas): Int = {
  val xs = for {
    (kl, vl) <- lhs
    vr <- rhs.get(kl)
  } yield (kl match
    case "cats" | "trees"           => vl > vr
    case "goldfish" | "pomeranians" => vl < vr
    case _                          => vl == vr
  )

  xs.count(identity)
}

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): Int = { // => 40
  val res = sepByEol1(pTikaas).parse(input)

  res match {
    case Success(sues) => {
      val xs = sues
        .zipWithIndex
        .map { (sue, i) =>
          val sueScore = score(sue, tiraas)

          (sueScore, i)
        }
        .sortBy(_._1)(Ordering.Int.reverse)

      xs.head._2 + 1
    }
    case Failure(_)  => 0
  }
}

def part2(input: String): Int = { // => 241
  val res = sepByEol1(pTikaas).parse(input)

  res match {
    case Success(sues) => {
      val xs = sues
        .zipWithIndex
        .map { (sue, i) =>
          val sueScore = encabulate(sue, tiraas)

          (sueScore, i)
        }
        .sortBy(_._1)(Ordering.Int.reverse)

      xs.head._2 + 1
    }
    case Failure(_)  => 0
  }
}
