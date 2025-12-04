import solutions.y2022.day04.*

class Y2022_Day04_Suite extends munit.FunSuite {
  val input = List(
    "2-4,6-8",
    "2-3,4-5",
    "5-7,7-9", // these two overlap at 7.
    "2-8,3-7", // the first elf's assignment wholy contains the second's.
    "6-6,4-6",
    "2-6,4-8"
  ).mkString("\n")
    
  test("Example 1") {
    assertEquals(part1(input), 2)
  }

  test("Example 2") {
    assertEquals(part2(input), 4)
  }
}
