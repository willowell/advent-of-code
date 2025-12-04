import solutions.y2024.day03.*

class Y2024_Day3_Suite extends munit.FunSuite {
  test("Parser Tests") {
    assertEquals(
      processInstructions(parseInput("mul(44,46)")), 2024
    )
    assertEquals(
      processInstructions(parseInput("mul(123,4)")), 492
    )
    assertEquals(
      processInstructions(parseInput("mul(2,2)mul(2,2)")), 8
    )
  }
  test("Example 1") {
    assertEquals(part1(testInput), 161)
  }

  test("Example 2") {
    assertEquals(part2(testInput2), 48)
    assertEquals(part2(testInput2 ++ "\n" ++ testInput2), 96)
  }
}
