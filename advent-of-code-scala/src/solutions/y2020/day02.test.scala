import solutions.y2020.day02.*

class Y2020_Day02_Suite extends munit.FunSuite {
  val input = List(
    "1-3 a: abcde",
    "1-3 b: cdefg",
    "2-9 c: ccccccccc"
  ).mkString("\n")
    
  test("Example 1") {
    assertEquals(part1(input), 2)
  }

  test("Example 2") {
    assertEquals(part2(input), 1)
  }
}
