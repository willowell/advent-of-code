package core.data.infinitelists

import scala.collection.immutable.LazyList

lazy val naturals: LazyList[Int] = LazyList.from(1)

val naturalsIt: Iterator[Int] = Iterator.from(1)

lazy val fibs: LazyList[Int] = 0 #:: fibs.scanLeft(1) { (prev, curr) => prev + curr }
