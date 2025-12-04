import solutions.y2022.day09.*

class Y2022_Day09_Suite extends munit.FunSuite {
  val input = List(
    "R 4",
    "U 4",
    "L 3",
    "D 1",
    "R 4",
    "D 1",
    "L 5",
    "R 2"
  ).mkString("\n")
    
  test("Example 1") {
    assertEquals(part1(input), 13)
  }

  test("Example 2") {
    assertEquals(part2(input), 1)
  }
}
