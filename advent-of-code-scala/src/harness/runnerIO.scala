package harness

import cats.effect.*
import com.monovore.decline.{time as _, *}
import com.monovore.decline.effect.CommandIOApp

/** ## Simple Run Part (IO)
  * 
  *
  * @param runPart
  * @param year ***implicit*** Current year.
  * @param day ***implicit*** Current day.
  */
def simpleRunPartIO[A](runPart: String => A)(using year: Year, day: Day): IO[Unit] = for {
  input <- IO { loadInput()(using year, day) }

  answer <- IO { time { runPart(input) } }

  _ <- IO.println(s"The solution is $answer")
} yield (())


open class AocDayRunnerIO[A, B](
  part1: String => A,
  part2: String => B
)(
  using
  year: Year,
  day: Day
) extends CommandIOApp(
  name = "aoc",
  header = f"🎄 Advent of Code 🌟 Year $year%04d 🌟 Day $day%02d 🎄",
) {
  override def main: Opts[IO[ExitCode]] = part.map {
    case Some(Part.A) => for {
      _ <- IO.println(f"🎄 Advent of Code 🌟 Year $year%04d 🌟 Day $day%02d 🎄")
      _ <- simpleRunPartIO(part1)
    } yield (ExitCode.Success)
    case Some(Part.B) => for {
      _ <- IO.println(f"🎄 Advent of Code 🌟 Year $year%04d 🌟 Day $day%02d 🎄")
      _ <- simpleRunPartIO(part2)
    } yield (ExitCode.Success)
    case None         => for {
      _ <- IO.println(f"🎄 Advent of Code 🌟 Year $year%04d 🌟 Day $day%02d 🎄")
      _ <- simpleRunPartIO(part1)
      _ <- simpleRunPartIO(part2)
    } yield (ExitCode.Success)
  }
}
