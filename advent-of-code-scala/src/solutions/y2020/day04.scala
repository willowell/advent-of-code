/// Day 4: Passport Processing
///
///
package solutions.y2020.day04

import solutions.y2020.year
import harness.*

import core.control.combinators.*
import core.data.colour.*
import core.parser.prelude.*

import parsley.*
import parsley.Parsley.*
import parsley.character.*
import parsley.errors.combinator.*
import parsley.combinator.*

given day: Day = Day(4)

object run extends AocDayRunner(part1, part2)

//====================================================================================================================//
// PART 1                                                                                                             //
//====================================================================================================================//

enum PassportField {
  case BirthYearField
  case IssueYearField
  case ExpirationYearField
  case HeightField
  case HairColourField
  case EyeColourField
  case PassportIdField
  case CountryIdField
}

object PassportField {
  val requiredFields = PassportField.values.filter { _ != PassportField.CountryIdField }
}

val pPassportFieldName: Parsley[PassportField] = atomicChoice(
  string("byr") #> PassportField.BirthYearField,
  string("iyr") #> PassportField.IssueYearField,
  string("eyr") #> PassportField.ExpirationYearField,
  string("hgt") #> PassportField.HeightField,
  string("hcl") #> PassportField.HairColourField,
  string("ecl") #> PassportField.EyeColourField,
  string("pid") #> PassportField.PassportIdField,
  string("cid") #> PassportField.CountryIdField,
)

type Passport = Map[PassportField, String]

val pPassportField: Parsley[(PassportField, String)] = for {
  k <- pPassportFieldName
  _ <- colon
  v <- asciiStr
} yield (k, v)

val pPassport: Parsley[Passport] =
  sepEndBy1(pPassportField, whitespace)
    .map { _.toMap }
    .unexpectedWhen { case p if !passportHasRequiredFields(p) => "passport is missing required fields" }

def passportHasRequiredFields(passport: Passport): Boolean = {
  val hasFields = for {
    field <- PassportField.requiredFields
  } yield passport.contains(field)

  hasFields.forall { identity }
}

def part1(input: String): Int = {
  val passports = splitByEol2(input).map { s => pPassport.parse(s) }

  countParseSuccesses(passports)
}

//====================================================================================================================//
// PART 2                                                                                                             //
//====================================================================================================================//

enum EyeColour {
  case Amber, Blue, Brown, Grey, Green, Hazel, Other
}

enum Height {
  case Centimeters(v: Int)
  case Inches(v: Int)
}

enum PassportEnhancedField {
  case BirthYearField(v: Int)
  case IssueYearField(v: Int)
  case ExpirationYearField(v: Int)
  case HeightField(v: Height)
  case HairColourField(v: RGB)
  case EyeColourField(v: EyeColour)
  case PassportIdField(v: String)
  case CountryIdField(v: String)
}

type PassportEnhanced = Set[PassportEnhancedField]

object PassportEnhancedField {
  val pBirthYearField: Parsley[PassportEnhancedField] =
    int32
      .map { PassportEnhancedField.BirthYearField(_) }
      .unexpectedWhen { case PassportEnhancedField.BirthYearField(v) if !isBirthYearValid(v) => "birth year is not in range" }
      ? "birth year"

  val pIssueYearField: Parsley[PassportEnhancedField] =
    int32
      .map { PassportEnhancedField.IssueYearField(_) }
      .unexpectedWhen { case PassportEnhancedField.IssueYearField(v) if !isIssueYearValid(v) => "issue year is not in range" }
      ? "issue year"

  val pExpirationYearField: Parsley[PassportEnhancedField] =
    int32
      .map { PassportEnhancedField.ExpirationYearField(_) }
      .unexpectedWhen { case PassportEnhancedField.ExpirationYearField(v) if !isExpirationYearValid(v) => "expiration year is not in range" }
      ? "expiration year"

  val pHeight: Parsley[Height] = atomicChoice(
    (exactly(3, digit) <* string("cm")).map { xs =>
      val s = xs.mkString

      Height.Centimeters(s.toInt)
    },
    (exactly(2, digit) <* string("in")).map { xs =>
      val s = xs.mkString

      Height.Inches(s.toInt)
    }
  ) ? "height"

  val pHeightField: Parsley[PassportEnhancedField] =
    pHeight
      .map { PassportEnhancedField.HeightField(_) }
      .unexpectedWhen { case PassportEnhancedField.HeightField(v) if !isHeightValid(v) => "height is either too short or too tall" }
      ? "height in cm or in"

  val pHairColourField: Parsley[PassportEnhancedField] =
    pHexRgb
      .map { PassportEnhancedField.HairColourField(_) }
      ? "hair colour"

  val pEyeColour: Parsley[EyeColour] = atomicChoice(
    string("amb") #> EyeColour.Amber,
    string("blu") #> EyeColour.Blue,
    string("brn") #> EyeColour.Brown,
    string("gry") #> EyeColour.Grey,
    string("grn") #> EyeColour.Green,
    string("hzl") #> EyeColour.Hazel,
    string("oth") #> EyeColour.Other,
  ) ? "eye colour"

  val pEyeColourField: Parsley[PassportEnhancedField] =
    pEyeColour
      .map { PassportEnhancedField.EyeColourField(_) }
      ? "eye colour"
  
  val pPassportIdField: Parsley[PassportEnhancedField] =
    exactly(9, digit)
      .map { xs => PassportEnhancedField.PassportIdField(xs.mkString) }
      ? "passport ID"

  val pCountryIdField: Parsley[PassportEnhancedField] =
    anStr
      .map { PassportEnhancedField.CountryIdField(_) }
      ? "country ID"
  
  val pPassportField: Parsley[PassportEnhancedField] = atomicChoice(
    string("byr") *> colon *> pBirthYearField,
    string("iyr") *> colon *> pIssueYearField,
    string("eyr") *> colon *> pExpirationYearField,
    string("hgt") *> colon *> pHeightField,
    string("hcl") *> colon *> pHairColourField,
    string("ecl") *> colon *> pEyeColourField,
    string("pid") *> colon *> pPassportIdField,
    string("cid") *> colon *> pCountryIdField,
  ) ? "passport field"

  val pPassportEnhanced: Parsley[PassportEnhanced] =
    sepEndBy1(pPassportField, whitespace)
      .map { _.toSet }
      .unexpectedWhen { case p if !passportEnhancedHasRequiredFields(p) => "passport is missing required fields" }
      ? "passport"

  def isBirthYearValid(x: Int): Boolean = 1920 <= x && x <= 2002

  def isIssueYearValid(x: Int): Boolean = 2010 <= x && x <= 2020

  def isExpirationYearValid(x: Int): Boolean = 2020 <= x && x <= 2030

  def isHeightValid(x: Height): Boolean = {
    import Height.*

    x match
      case Centimeters(v) => 150 <= v && v <= 193
      case Inches(v) => 59 <= v && v <= 76
  }

  def passportEnhancedHasRequiredFields(passport: PassportEnhanced): Boolean = {
    (passport `diff` passport
      .collect { case x@(_: PassportEnhancedField.CountryIdField) => x }
    ).size == 7
  }
}

def part2(input: String): Int = {
  val passports = splitByEol2(input)
    .map { s => PassportEnhancedField.pPassportEnhanced.parse(s) }

  for passport <- passports do
    println(passport)

  countParseSuccesses(passports)
}
