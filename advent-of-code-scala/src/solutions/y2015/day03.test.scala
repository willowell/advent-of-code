import solutions.y2015.day03.*

class Y2015_Day03_Suite extends munit.FunSuite {
  test("Example 1") {
    assertEquals(part1(">"), 2)
    assertEquals(part1("^>v<"), 4)
    assertEquals(part1("^v^v^v^v^v"), 2)
  }

  test("Example 2") {
    assertEquals(part2("^v"), 3)
    assertEquals(part2("^>v<"), 3)
    assertEquals(part2("^v^v^v^v^v"), 11)
  }
}
