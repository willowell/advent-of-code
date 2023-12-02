package core.data.graph

import core.data.edge.*
import core.text.prettyprinting.Display

import scala.collection.mutable.{ArrayBuffer, HashSet}

trait Graph[V, E <: Edge] {
  var vertices: ArrayBuffer[V]
  var edges: ArrayBuffer[ArrayBuffer[E]]

  def getVertexCount: Int = vertices.length

  def getEdgesCount: Int = edges.map( _.length ).sum

  /**
    * Get the hash set of the edges in the graph; i.e., get a list of all unique edges.
    */
  def getEdgeSet: HashSet[E] = {
    HashSet.from(edges.flatten)
  }

  /**
    * Add a vertex to the graph.
    *
    * @param v Vertex to add
    * @return Number of vertices after adding this vertex
    */
  def addVertex(v: V): Int = {
    vertices.addOne(v)
    
    edges.addOne(ArrayBuffer.empty)

    getVertexCount - 1
  }

  def getVertexAt(index: Int): V = {
    vertices.apply(index)
  }

  def getIndexOf(v: V) = {
    vertices.indexOf(v)
  }

  def getNeighboursOf(index: Int): List[V] = {
    edges(index)
      .map { e => getVertexAt(e.v) }
      .toList
  }

  def getNeighboursOf(v: V): List[V] = {
    getNeighboursOf(getIndexOf(v))
  }

  def getEdgesOf(index: Int): List[E] = {
    edges(index).toList
  }

  def getEdgesOf(v: V): List[E] = {
    getEdgesOf(getIndexOf(v))
  }
}

given displayGraph[V, E <: Edge & Display[E]]: Display[Graph[V, E]] with {
  override def display(g: Graph[V, E]): String = {
    g.edges.map { edge =>
      edge.map { node =>
        node.display
      }.mkString
    }.mkString
  }
}
