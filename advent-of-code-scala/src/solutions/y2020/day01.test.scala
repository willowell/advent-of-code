import solutions.y2020.day01.*

class Y2020_Day01_Suite extends munit.FunSuite {
  val input = (
    """1721
      |979
      |366
      |299
      |675
      |1456""".stripMargin
  )

  test("Example 1") {
    assertEquals(part1(input), 514579)
  }

  test("Example 2") {
    assertEquals(part2(input), 241861950)
  }
}
