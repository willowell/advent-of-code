import solutions.y2022.day05.*

class Y2022_Day05_Suite extends munit.FunSuite {
  val input = List(
    "    [D]    ",
    "[N] [C]    ",
    "[Z] [M] [P]",
    " 1   2   3 ",
    "",
    "move 1 from 2 to 1",
    "move 3 from 1 to 3",
    "move 2 from 2 to 1",
    "move 1 from 1 to 2",
  ).mkString("\n")

  /*
  For the test input, returns stacks "ZN,MCD,P"
  
  For ease of use, we can reverse the stacks:
  1. NZ
  2. DCM
  3. P

  Next step:
  1. DNZ
  2. CM
  3. P

  Next step:
  1. <empty>
  2. CM
  3. ZNDP
  */
    
  test("Example 1") {
    assertEquals(part1(input), "CMZ")
  }

  test("Example 2") {
    assertEquals(part2(input), "MCD")
  }
}
