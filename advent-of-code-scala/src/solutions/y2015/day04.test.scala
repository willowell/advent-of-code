import solutions.y2015.day04.*

class Y2015_Day04_Suite extends munit.FunSuite {
  test("Example 1") {
    assertEquals(part1("abcdef"), 609043)
    assertEquals(part1("pqrstuv"), 1048970)
  }

  test("Example 2") {
    assertEquals(part2("abcdef"), 6742839)
    assertEquals(part2("pqrstuv"), 25729123)
  }
}
