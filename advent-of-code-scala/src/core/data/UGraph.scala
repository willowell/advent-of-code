package core.data.ugraph

import core.data.edge.{given, *}
import core.data.graph.Graph
import core.text.prettyprinting.{Display, Ops}
import Ops.*

import scala.collection.mutable.ArrayBuffer

class UGraph[V](
  var vertices: ArrayBuffer[V],
  var edges: ArrayBuffer[ArrayBuffer[UEdge]]
) extends Graph[V, UEdge] {
  def addEdge(e: UEdge): Unit = {
    val _ = edges(e.u).addOne(e)
    val _ = edges(e.v).addOne(e.reversed)
  }

  def addEdge(from: V, to: V): Unit = {
    val i = getIndexOf(from)
    val j = getIndexOf(to)

    val e = UEdge(i, j)

    addEdge(e)
  }

  def addEdges(edges: Seq[(V, V)]): Unit = {
    for ((from, to) <- edges) {
      addEdge(from, to)
    }
  }
}

object UGraph {
  def apply[V]: UGraph[V] = UGraph.empty

  def apply[V](vertices: Seq[V]): UGraph[V] = new UGraph[V](
    vertices = ArrayBuffer.from(vertices),
    edges = ArrayBuffer.fill(vertices.length) { ArrayBuffer.empty }
  )

  def empty[V]: UGraph[V] = new UGraph[V](
    vertices = ArrayBuffer.empty,
    edges = ArrayBuffer.empty
  )
}

given displayUGraph[V]: Display[UGraph[V]] with {
  override def display(g: UGraph[V]): String = {
    (0 until g.getVertexCount).foldLeft("") { (acc, cur) =>
      val currentVertexAsStr = g.getVertexAt(cur).toString
      val neighboursAsStr = g.getNeighboursOf(cur).mkString("[", ", ", "]")

      acc ++ s"$currentVertexAsStr -> $neighboursAsStr" ++ "\n"
    }
  }
}
