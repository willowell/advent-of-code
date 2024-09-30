package core.data.infinitelists

import scala.collection.immutable.LazyList

/** Lazy list of natural numbers starting with 1. */
val naturals: LazyList[Int] = LazyList.from(1)

/** Iterator of natural numbers starting with 1. */
val naturalsIt: Iterator[Int] = Iterator.from(1)

// NB: `fibs` below is `lazy` because its definition is recursive.
// `naturals` and `naturalsIt` do not refer to themselves in their definitions, so this is not needed there.

/** Lazy list of fibonacci numbers starting with (0, 1). */
lazy val fibs: LazyList[Int] = 0 #:: fibs.scanLeft(1) { (prev, curr) => prev + curr }
