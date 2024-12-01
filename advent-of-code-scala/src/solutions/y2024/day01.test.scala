import solutions.y2024.day01.*

class Y2024_Day01_Suite extends munit.FunSuite {
  test("Example 1") {
    assertEquals(part1("1 1"), 0)
    assertEquals(part1("1 2"), 1)
    assertEquals(part1("3 9"), 6)
    assertEquals(part1("9 3"), 6)
    assertEquals(part1("1 1\n1 1"), 0)
    assertEquals(part1(testInput), 11)
  }

  test("Example 2") {
    assertEquals(part2(testInput), 31)
  }
}
