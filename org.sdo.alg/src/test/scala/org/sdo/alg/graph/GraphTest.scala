package org.sdo.alg.graph

import org.scalatest.{Matchers, FlatSpec}
import org.scalatest._
import org.sdo.alg.graph.Graph.{Edge, Vertex}
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable

/**
 * Created by dsemenov on 5/30/14.
 */
class GraphTest extends FlatSpec with Matchers {

  it should "be 2 outgoing edges from first vertex" in {
    val graph = Array.fill(4)(new Vertex)
    graph(0) << Edge(graph(1), 1) << Edge(graph(2), 4)
    graph(1) << Edge(graph(2), 2) << Edge(graph(3), 6)
    graph(2) << Edge(graph(3), 3)
    graph(0).edges should be(mutable.Buffer(Edge(graph(1), 1), Edge(graph(2), 4)))
  }

  it should "length 6 from 1 to 4th vertex" in {
    val graph = Array.fill(4)(new Vertex)
    graph(0) << Edge(graph(1), 1) << Edge(graph(2), 4)
    graph(1) << Edge(graph(2), 2) << Edge(graph(3), 6)
    graph(2) << Edge(graph(3), 3)
    Graph.exploredVertices += graph(0) -> 0
    Graph.dijkstra(graph(0), graph(3)) should be(Some(List(graph(0), graph(1), graph(2), graph(3))))
  }

  it should "be edge from 2 to 3d vertex chosen by greedy criteria" in {
    val graph = Array.fill(4)(new Vertex)
    graph(0) << Edge(graph(1), 1) << Edge(graph(2), 4)
    graph(1) << Edge(graph(2), 2) << Edge(graph(3), 6)
    graph(2) << Edge(graph(3), 3)
    Graph.exploredVertices = Map(graph(0) -> 0, graph(1) -> 1)
    Graph.runGreedy should be(Some((Edge(graph(2), 2), 3)))
  }

  it should "be nowhere to go if vertex has no outcomes" in {
    val graph = Array.fill(4)(new Vertex)
    graph(0) << Edge(graph(1), 1) << Edge(graph(2), 4)
    graph(1) << Edge(graph(2), 2) << Edge(graph(3), 6)
    graph(2) << Edge(graph(3), 3)
    Graph.exploredVertices = Map(graph(0) -> 0, graph(1) -> 1, graph(2) -> 3, graph(3) -> 6)
    Graph.runGreedy should be(None)
  }

}
