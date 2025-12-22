import solutions.y2015.day18.*

import core.parser.prelude.*

class Y2015_Day18_Suite extends munit.FunSuite {
  val testInput =
    """.#.#.#
      |...##.
      |#....#
      |..#...
      |#.#..#
      |####..""".stripMargin

  test("Example 1") {
    assertEquals(getPopulationWithSteps(testInput, 6), 4)
  }

  test("Example 2") {
    assertEquals(getPopulationWithSteps(testInput, 6, true), 17)
  }
}
