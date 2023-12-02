package core.control.combinators

extension [A](a: A) {
  /**
    * Pipe a value into a function `f`.
    *
    * @param B
    * @param f
    * @return
    */
  def |>[B](f: A => B): B = f(a)
}

def fst[A, B](tp: Product2[A, B]) = tp._1

def snd[A, B](tp: Product2[A, B]) = tp._2
