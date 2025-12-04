package core.data.freqmap

import collection.immutable.Map

final case class FreqMap[A](
  value: Map[A, Int]
) {
  def mostCommon: A = value.maxBy { _._2 }._1
  def leastCommon: A = value.minBy { _._2 }._1

  def apply(x: A): Int = value(x)
}

object FreqMap {
  def apply[A](xs: Seq[A]): FreqMap[A] = {
    val freqmap = xs.groupMapReduce { identity } { _ => 1 } { _ + _ }

    FreqMap(freqmap)
  }
}

extension [A](xs: Seq[A]) {
  def toFreqMap: FreqMap[A] = FreqMap(xs)
}
