import solutions.y2022.day08.*

class Y2022_Day08_Suite extends munit.FunSuite {
  val input = List(
    "30373",
    "25512",
    "65332",
    "33549",
    "35390",
  ).mkString("\n")
    
  test("Example 1") {
    assertEquals(part1(input), 21)
  }

  test("Example 2") {
    assertEquals(part2(input), 8)
  }
}
