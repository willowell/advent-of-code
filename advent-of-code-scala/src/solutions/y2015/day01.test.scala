import solutions.y2015.day01.*

class Y2015_Day01_Suite extends munit.FunSuite {
  test("Example 1") {
    assertEquals(part1("()"), 0)
    assertEquals(part1("(())"), 0)
    assertEquals(part1("()()"), 0)
    assertEquals(part1("("), 1)
    assertEquals(part1(")"), -1)
    assertEquals(part1("((("), 3)
    assertEquals(part1("(()(()("), 3)
    assertEquals(part1("))((((("), 3)
    assertEquals(part1("())"), -1)
    assertEquals(part1("))("), -1)
    assertEquals(part1(")))"), -3)
    assertEquals(part1(")())())"), -3)
  }

  test("Example 2") {
    assertEquals(part2(")"), 1)
    assertEquals(part2("()())"), 5)
  }
}
