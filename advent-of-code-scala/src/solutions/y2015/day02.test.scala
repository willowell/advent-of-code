import solutions.y2015.day02.*

class Y2015_Day02_Suite extends munit.FunSuite {
  test("Example 1") {
    assertEquals(part1("0x0x0"), 0)
    assertEquals(part1("2x3x4"), 58)
    assertEquals(part1("1x1x10"), 43)
  }

  test("Example 2") {
    assertEquals(part2("0x0x0"), 0)
    assertEquals(part2("2x3x4"), 34)
    assertEquals(part2("1x1x10"), 14)
  }
}
