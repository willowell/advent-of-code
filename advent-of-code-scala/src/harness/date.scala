package harness

/** A `Year`, represented as an `Int`. */
opaque type Year <: Int = Int

object Year {
  /** ***UNSAFE*** Lift an `Int` into a `Year`. */
  def apply(value: Int): Year = value
}

/** A `Day`, represented as an `Int`. */
opaque type Day <: Int = Int

object Day {
  /** ***UNSAFE*** Lift an `Int` into a `Day`. */
  def apply(value: Int): Day = value
}
