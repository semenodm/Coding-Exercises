package org.sdo.alg.graph

import scala.collection.mutable

/**
 * Created by dsemenov on 5/30/14.
 */
object Graph {

  case class Edge(dest: Vertex, weight: Int)

  class Vertex {
    val edges = mutable.Buffer[Edge]()

    def <<(edge: Edge) = {
      edges += edge
      this
    }
  }

  var exploredVertices = Map.empty[Vertex, Int]

  def dijkstra(from: Vertex, to: Vertex): Option[List[Vertex]] = {
    if (from == to) Some(List(to))
    else {
      runGreedy match {
        case None => None
        case Some((edge, distance)) =>
          exploredVertices += edge.dest -> distance
          dijkstra(edge.dest, to) match {
            case None => None
            case Some(l) => Some(from :: l)
          }
      }
    }
  }

  def runGreedy = {
    val r = exploredVertices.flatMap {
      case (vertex, distance) =>
        vertex.edges.filter(edge => !exploredVertices.contains(edge.dest))
          .map(edge => (edge, edge.weight + distance))
    }
    if (r.isEmpty) None else Some(r.minBy(_._2))
  }
}
