import solutions.y2022.day03.*

class Y2022_Day03_Suite extends munit.FunSuite {
  val input = List(
    "vJrwpWtwJgWrhcsFMMfFFhFp",
    "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
    "PmmdzqPrVvPwwTWBwg",
    "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
    "ttgJtRGJQctTZtZT",
    "CrZsJsPPZsGzwwsLwLmpwMDw"
  ).mkString("\n")
    
  test("Example 1") {
    assertEquals(part1(input), 157)
  }

  test("Example 2") {
    assertEquals(part2(input), 70)
  }
}
