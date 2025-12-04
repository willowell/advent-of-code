import core.control.combinators.*

class Core_Control_Combinators_Functions_Tests extends munit.FunSuite {
  def f(x: Int): Int = x + 1
  def g(x: Int)(y: Int): Int = x + y

  test("fst") {
    assertEquals( 1 |> f,            2)
    assertEquals( 1 |> { _ + 1 },    2)
    assertEquals((1 |> { g(_) })(1), 2)
  }
}

class Core_Control_Combinators_Tuples_Tests extends munit.FunSuite {
  val tuple = (1, 2)

  test("fst") {
    assertEquals(fst(tuple), 1)
  }

  test("snd") {
    assertEquals(snd(tuple), 2)
  }
}
