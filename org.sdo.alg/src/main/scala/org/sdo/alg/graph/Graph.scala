package org.sdo.alg.graph

import scala.collection.mutable

/**
 * Created by dsemenov on 5/30/14.
 */
object Graph {

  case class Edge(dest: Vertex, weight: Int)

  class Vertex(val label : Int) {
    val edges = mutable.Buffer[Edge]()

    def <<(edge: Edge) = {
      edges += edge
      this
    }
    override def toString = {label.toString}
  }

  var exploredVertices = Map.empty[Vertex, Int]

  def dijkstra(from: Vertex, to: Vertex): Option[List[Vertex]] = {
    if (from == to) Some(List(to))
    else {
      runGreedy match {
        case None => None
        case Some((vertex, edge, distance)) =>
          exploredVertices += edge.dest -> distance
          dijkstra(edge.dest, to) match {
            case None => None
            case Some(l) => if(edge.dest == l.head) Some(vertex :: l) else Some(l)
          }
      }
    }
  }

  def runGreedy = {
    val r = exploredVertices.flatMap {
      case (vertex, distance) =>
        vertex.edges.filter(edge => !exploredVertices.contains(edge.dest))
          .map(edge => (vertex, edge, edge.weight + distance))
    }
    if (r.isEmpty) None else Some(r.minBy{case (fromVertex, edge, distance) => distance})
  }
}
