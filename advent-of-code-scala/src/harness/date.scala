package harness

opaque type Year <: Int = Int

object Year {
  def apply(value: Int): Year = value
}

opaque type Day <: Int = Int

object Day {
  def apply(value: Int): Day = value
}
