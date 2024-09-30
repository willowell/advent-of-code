package harness

import cats.data.Validated
import com.monovore.decline.Opts

enum Part {
  case A
  case B
}

/** Day part option. May be a, b, or absent. */
val part: Opts[Option[Part]] = Opts.option[String]("part", "a or b", "p")
  .mapValidated {
    case "a" => Validated.valid(Part.A)
    case "b" => Validated.valid(Part.B)
    case _   => Validated.invalidNel("Must be a or b")
  }
  .orNone


/** Year option. Must be in closed range [2015, 2025]. */
val yearOpt: Opts[Year] = Opts.option[Int]("year", "", "y")
  .validate("Year must be between 2015 and 2025 inclusive") { x => x >= 2015 && x <= 2025 }
  .map { Year(_) }

/** Day option. Must be in closed range [1, 25]. */
val dayOpt: Opts[Day] = Opts.option[Int]("day", "", "d")
  .validate("Day must be between 1 and 25 inclusive") { x => x >= 1 && x <= 25 }
  .map { Day(_) }
