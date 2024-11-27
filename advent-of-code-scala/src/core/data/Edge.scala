package core.data.edge

import core.text.prettyprinting.Display

trait Edge {
  def u: Int
  def v: Int
  def reversed: Edge
}

case class UEdge(
  u: Int,
  v: Int
) extends Edge {
  override def reversed: UEdge = UEdge(v, u)
}

object UEdge {
  given Display[UEdge] with {
    override def display(e: UEdge) = s"{${e.u}} -> {${e.v}}"
  }
}

case class WEdge(
  u: Int,
  v: Int,
  weight: Double
) extends Edge with math.Ordered[WEdge] {

  override def reversed: WEdge = WEdge(v, u, weight)

  override def compare(that: WEdge): Int = {
    this.weight compare that.weight
  }
}

object WEdge {
  def apply(uv: (Int, Int), weight: Double): WEdge = WEdge(uv._1, uv._2, weight)

  given Display[WEdge] with {
    override def display(e: WEdge) = s"{${e.u}} --(${e.weight})-> {${e.v}}"
  }
}
