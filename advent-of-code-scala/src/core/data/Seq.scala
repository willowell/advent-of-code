package core.data.seq

extension [A](xs: Seq[A]) {
  infix def overlaps(ys: Seq[A]): Boolean = !xs.intersect(ys).isEmpty

  infix def eitherWholyContains(ys: Seq[A]): Boolean = xs.containsSlice(ys) || ys.containsSlice(xs)
}

extension [A](xss: Seq[Seq[A]]) {
  /**
    * Rotate this 2D sequence clockwise by 90 degrees.
    * 
    * @example
    * ```
    * > val xss = List(
    *   List(1, 2, 3)
    *   List(4, 5, 6)
    *   List(7, 8, 9)
    * )
    * 
    * > xss.rotate90
    *
    * List(
    *   List(7, 4, 1)
    *   List(8, 5, 2)
    *   List(9, 6, 3)
    * )
    * ```
    */
  def rotate90: Seq[Seq[A]] = xss.reverse.transpose
}

extension (xss: Seq[String]) {
  /**
   * Rotate this 2D sequence of characters clockwise by 90 degrees.
   * 
   * @example
   * ```
   * > val xss = List(
   *   List('a', 'b', 'c')
   *   List('d', 'e', 'f')
   *   List('g', 'h', 'i')
   * )
   * 
   * > xss.rotate90
   *
   * List(
   *   List('g', 'd', 'a')
   *   List('h', 'e', 'b')
   *   List('i', 'f', 'c')
   * )
   * ```
  */
  @annotation.targetName("rotate90seqString") def rotate90 = xss.reverse.transpose
}

def splitHalf[A](xs: Seq[A]): (Seq[A], Seq[A]) = {
  val half = xs.length / 2

  xs.splitAt(half)
}
