import core.crypto.md5.MD5

class MD5Tests extends munit.FunSuite {
  test("a, b") {
    assert(MD5.hash("a") == "0cc175b9c0f1b6a831c399e269772661")
    assert(MD5.hash("b") == "92eb5ffee6ae2fec3ad71c777531578f")
  }

  test("foo, bar, foobar") {
    assert(MD5.hash("foo")    == "acbd18db4cc2f85cedef654fccc4a4d8")
    assert(MD5.hash("bar")    == "37b51d194a7513e45b56f6524f2d51f2")
    assert(MD5.hash("foobar") == "3858f62230ac3c915f300c664312c63f")
  }

  test("known to return leading zeros") {
    assert(MD5.hash("363") == "00411460f7c92d2124a67ea0f4cb5f85")
    assert(MD5.hash("jk8ssl") == "0000000018e6137ac2caab16074784a6")
    assert(MD5.hash("j(R1wzR*y[^GxWJ5B>L{-HLETRD") == "00000000000003695b3ae70066f60d42")
  }
}
