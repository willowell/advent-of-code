///
///
/// https://alvinalexander.com/source-code/scala-method-create-md5-hash-of-string/
///
package core.crypto.md5

object MD5 {
  /** returns a 32-character MD5 hash version of the input string
    * 
    *
    * @param s
    * @return
    */
  def hash(s: String): String = {
    import java.math.BigInteger
    import java.security.MessageDigest

    val md = MessageDigest.getInstance("MD5")

    val digest: Array[Byte] = md.nn.digest(s.getBytes("UTF-8").nn).nn

    val bigInt = new BigInteger(1, digest)

    val hashed = bigInt.toString(16).trim.nn

    prependWithZeros(hashed)
  }

  /**
   * This uses a little magic in that the string I start with is a
   * “format specifier,” and it states that the string it returns
   * should be prepended with blank spaces as needed to make the
   * string length equal to 32. Then I replace those blank spaces
   * with the character `0`.
   */
  private def prependWithZeros(pwd: String): String = "%1$32s".format(pwd).replace(' ', '0').nn
}
