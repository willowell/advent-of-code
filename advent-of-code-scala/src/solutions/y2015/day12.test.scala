import solutions.y2015.day12.*

class Y2015_Day12_Suite extends munit.FunSuite {

  test("Example 1 - with Regex") {
    assertEquals(part1WithRegex("[]"), 0)
    assertEquals(part1WithRegex("{}"), 0)
    assertEquals(part1WithRegex("[[[3]]]"), 3)
    assertEquals(part1WithRegex("""{"a":{"b":4},"c":-1""""), 3)
    assertEquals(part1WithRegex("""{"a":[-1,1]}"""), 0)
    assertEquals(part1WithRegex("""[-1,{"a":1}]"""), 0)
    assertEquals(part1WithRegex("""[1,2,3]"""), 6)
    assertEquals(part1WithRegex("""{"a":2,"b":4}"""), 6)
  }

  test("Example 1 - with Circe") {
    assertEquals(part1WithCirce("[]"), 0)
    assertEquals(part1WithCirce("{}"), 0)
    assertEquals(part1WithCirce("[[[3]]]"), 3)
    assertEquals(part1WithCirce("""{"a":{"b":4},"c":-1}"""), 3)
    assertEquals(part1WithCirce("""{"a":[-1,1]}"""), 0)
    assertEquals(part1WithCirce("""[-1,{"a":1}]"""), 0)
    assertEquals(part1WithCirce("""[1,2,3]"""), 6)
    assertEquals(part1WithCirce("""{"a":2,"b":4}"""), 6)
  }

  test("Example 2 - Simple List") {
    assertEquals(part2("[1,2,3]"), 6)
  }

  test("Example 2 - Array including an object with a kv-pair including red") {
    assertEquals(part2("""[1,{"c":"red","b":2},3]"""), 4)
  }

  test("Example 2 - Object including a kv-pair including red") {
    assertEquals(part2("""{"d":"red","e":[1,2,3,4],"f":5}"""), 0)
  }

  test("Example 2 - Array including the string red") {
    assertEquals(part2("""[1,"red",5]"""), 6)
  }
}
