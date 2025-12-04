package solutions.y2022.day01

class Y2022_Day01_Suite extends munit.FunSuite {
  val input: String = (
    """1000
      |2000
      |3000
      |
      |4000
      |
      |5000
      |6000
      |
      |7000
      |8000
      |9000
      |
      |10000""".stripMargin
   )
    
  test("Example 1") {
    assertEquals(part1(input), 24000)
  }

  test("Example 2") {
    assertEquals(part2(input), 45000)
  }
}
