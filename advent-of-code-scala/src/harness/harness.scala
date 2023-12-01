package harness

import scala.io.Source

import com.monovore.decline.CommandApp

/** ## Load input file for the day from resources directory.
  * 
  *
  * @param year ***implicit*** Current year.
  * @param day ***implicit*** Current day.
  * @return
  */
def loadInput()(using year: Year, day: Day): String = {
  val padDay = f"${day}%02d"

  val path = f"input/${year}/day${padDay}.txt"

  Source.fromResource(path).mkString
}


/** ## Download input for the day.
  * 
  *
  * @param year ***implicit*** Current year.
  * @param day ***implicit*** Current day.
  * @return
  */
def downloadInput()(using year: Year, day: Day): Unit = ???


/** ## Simple Run Part
  * 
  *
  * @param runPart
  * @param year ***implicit*** Current year.
  * @param day ***implicit*** Current day.
  */
def simpleRunPart[A](runPart: String => A)(using year: Year, day: Day): Unit = {
  val input = loadInput()(using year, day)

  val answer: A = time {
    runPart(input)
  }

  println(s"The solution is $answer")
}

/** ## Advent of Code Day Runner
  * 
  *
  * @param part1 Part 1 solution function.
  * @param part2 Part 2 solution function.
  * @param year ***implicit*** Current year.
  * @param day ***implicit*** Current day.
  */
open class AocDayRunner[A, B](
  part1: String => A,
  part2: String => B
)(
  using
  year: Year,
  day: Day
) extends CommandApp(
  name = "aoc",
  header = f"🎄 Advent of Code 🌟 Year $year%04d 🌟 Day $day%02d 🎄",
  main = part.map {
    case Some(Part.A) => {
      println(f"🎄 Advent of Code 🌟 Year $year%04d 🌟 Day $day%02d 🎄")
      simpleRunPart(part1)
    }
    case Some(Part.B) => {
      println(f"🎄 Advent of Code 🌟 Year $year%04d 🌟 Day $day%02d 🎄")
      simpleRunPart(part2)
    }
    case None         => {
      println(f"🎄 Advent of Code 🌟 Year $year%04d 🌟 Day $day%02d 🎄")
      simpleRunPart(part1)
      simpleRunPart(part2)
    }
  }
)
