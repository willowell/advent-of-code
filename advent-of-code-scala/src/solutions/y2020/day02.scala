/// Day 2: Password Philosophy
///
///
package solutions.y2020.day02

import solutions.y2020.year
import harness.*

import core.control.combinators.*
import core.parser.prelude.*

import parsley.*
import parsley.Parsley.*
import parsley.character.*
import parsley.errors.combinator.*
import parsley.combinator.*

given day: Day = Day(2)

object run extends AocDayRunner(part1, part2)

// from the sled rental place down the street
case class PasswordPolicy(
  min: Int,
  max: Int,
  ch: Char
)

case class OtcasPasswordPolicy(
  firstPos: Int,
  secondPos: Int,
  ch: Char,
)

type Password = String

type PasswordGroup = (PasswordPolicy | OtcasPasswordPolicy, Password)


def isPasswordValid(password: String, policy: PasswordPolicy | OtcasPasswordPolicy): Boolean = policy match {
  case OtcasPasswordPolicy(firstPos, secondPos, ch) => {
    password(firstPos) == ch ^ password(secondPos) == ch
  }
  case PasswordPolicy(min, max, ch) => {
    val desiredChars = password.filter { _ == ch }

    desiredChars.length >= min && desiredChars.length <= max
  }
}


def pPasswordGroup(withOtcasPolicy: Boolean = false): Parsley[PasswordGroup] = {
  val p = for {
    first <- int32
    _ <- dash
    second <- int32
    _ <- whitespace
    ch <- letter
    _ <- char(':') *> whitespace
    password <- word
    
    policy = if withOtcasPolicy
      then OtcasPasswordPolicy(firstPos = first - 1, secondPos = second - 1, ch)
      else PasswordPolicy(min = first, max = second, ch)
  } yield (policy, password)

  p.unexpectedWhen {
    case (policy, password) if !isPasswordValid(password, policy) => policy match {
      case OtcasPasswordPolicy(first, second, ch) => f"expected `${ch}` to appear only once at either position ${first - 1} or ${second - 1} in password"
      case PasswordPolicy(min, max, ch)           => f"expected `${ch}` to appear between ${min} and ${max} times in password"
    }
  }
}

def part1(input: String): Int = {
  val passwordGroups = splitByEol(input)
    .map { s => pPasswordGroup().parse(s) }

  countParseSuccesses(passwordGroups)
}


def part2(input: String): Int = {
  val passwordGroups = splitByEol(input)
    .map { s => pPasswordGroup(withOtcasPolicy = true).parse(s) }

  countParseSuccesses(passwordGroups)
}
