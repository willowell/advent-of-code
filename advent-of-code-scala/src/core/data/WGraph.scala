package core.data.wgraph

import core.data.edge.{given, *}
import core.data.graph.Graph
import core.text.prettyprinting.{Display, Ops}
import Ops.*

import scala.collection.mutable.{ArrayBuffer, HashMap, PriorityQueue}
import scala.math.Ordered

case class DijkstraNode(
  vertex: Int,
  distance: Double,
) extends Ordered[DijkstraNode] {
  override def compare(that: DijkstraNode): Int = {
    val lhsCost = this.distance
    val rhsCost = that.distance

    (lhsCost - rhsCost).toInt
  }
}

case class DijkstraResult(
  distances: Array[Double],
  pathMap: HashMap[Int, WEdge],
)

class WGraph[V](
  var vertices: ArrayBuffer[V],
  var edges: ArrayBuffer[ArrayBuffer[WEdge]]
) extends Graph[V, WEdge] {
  def weight: Double = {
    getEdgeSet.map({ we => we.weight }).sum
  }

  def addEdge(e: WEdge): Unit = {
    val _ = edges(e.u).addOne(e)
    val _ = edges(e.v).addOne(e.reversed)
  }

  def addEdge(from: V, to: V, weight: Double): Unit = {
    val i = getIndexOf(from)
    val j = getIndexOf(to)

    val e = WEdge(i, j, weight)

    addEdge(e)
  }

  def addEdges(edges: Seq[(V, V, Double)]): Unit = {
    for ((from, to, weight) <- edges) {
      addEdge(from, to, weight)
    }
  }

  def addEdges2(edges: Seq[((V, V), Double)]): Unit = {
    for (((from, to), weight) <- edges) {
      addEdge(from, to, weight)
    }
  }

  def prettyFormatPath(path: List[WEdge]): String = {
    path.foldLeft("") { (acc, cur) =>
      println(cur)
      println(s"U: ${getVertexAt(cur.u)}, V: ${getVertexAt(cur.v)}")
      acc ++ s"${getVertexAt(cur.u)} --(${cur.weight})--> ${getVertexAt(cur.v)}\n"
    }
  }

  def mst(start: Int): Either[String, ArrayBuffer[WEdge]] = {
    import scala.util.boundary, boundary.break

    if (start < 0 || start > (getVertexCount - 1)) {
      return Left("start index is out of range")
    }

    var result: ArrayBuffer[WEdge] = ArrayBuffer.empty

    var pq: PriorityQueue[WEdge] = PriorityQueue.empty(summon[Ordering[WEdge]].reverse)

    var visited = Array.fill(getVertexCount) { false }

    def visit(index: Int): Unit = {
      visited(index) = true

      for (edge <- getEdgesOf(index)) {
        // Add all outgoing edges from here to pq
        if (!visited(edge.v)) {
          pq.enqueue(edge)
        }
      }
    }

    visit(start)

    while (!pq.isEmpty) {
      boundary {
        val edge = pq.dequeue()

        if (visited(edge.v)) {
          break()
        }

        // This is the current smallest, so add it to the solution
        result.addOne(edge)

        // Visit where this connects
        visit(edge.v)
      }
    }

    Right(result)
  }

  def dijkstra(root: V): DijkstraResult = {
    // Find the starting index
    val first = getIndexOf(root)

    // Distances are unknown at first
    val distances = Array.fill( getVertexCount ) { 0.0 }

    val visited = Array.fill( getVertexCount ) { false }

    visited(first) = true

    // How we got to each vertex
    var pathMap = HashMap[Int, WEdge]()
    var pq = PriorityQueue[DijkstraNode]() { summon[Ordering[DijkstraNode]].reverse }

    pq.enqueue(DijkstraNode(first, 0.0))

    while (!pq.isEmpty) {
      // Explore the next closest vertex
      val u = pq.dequeue().vertex
      // Should already have seen it
      val distU = distances(u)

      // Look at every edge/vertex from the vertex in question
      for (we <- getEdgesOf(u)) {

        // The old distance to this vertex
        val distV = distances(we.v)

        // The new distance to this vertex
        val pathWeight = we.weight + distU

        // New vertex or shorter path?
        if (!visited(we.v) || (distV > pathWeight)) {
          visited(we.v) = true

          // Update distance to this vertex
          distances(we.v) = pathWeight

          // Update the edge on the shortest path to this vertex
          val _ = pathMap.put(we.v, we)

          // Explore it in the future
          pq.enqueue(DijkstraNode(we.v, pathWeight))
        }
      }
    }

    DijkstraResult(distances, pathMap)
  }


}

object WGraph {
  def apply[V]: WGraph[V] = WGraph.empty

  def apply[V](vertices: Seq[V]): WGraph[V] = new WGraph[V](
    vertices = ArrayBuffer.from(vertices),
    edges = ArrayBuffer.fill(vertices.length) { ArrayBuffer.empty }
  )

  def empty[V]: WGraph[V] = new WGraph[V](
    vertices = ArrayBuffer.empty,
    edges = ArrayBuffer.empty
  )

  def getTotalWeightOf(path: List[WEdge]): Double = {
    path.map({ _.weight }).sum
  }
}

given displayWGraph[V]: Display[WGraph[V]] with {
  override def display(g: WGraph[V]): String = {
    (0 until g.getVertexCount).foldLeft("") { (acc, cur) =>
      val currentVertexAsStr = g.getVertexAt(cur).toString
      val neighboursAsStr = g.getEdgesOf(cur)
        .map({ we => s"(${g.getVertexAt(we.v)}, ${we.weight})" })
        .mkString("[", ", ", "]")

      acc ++ s"$currentVertexAsStr -> $neighboursAsStr" ++ "\n"
    }
  }
}
