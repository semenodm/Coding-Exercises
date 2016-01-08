package org.sdo.alg.graph


case class TopoGraph(adjList: Map[String, List[String]]) {
  var visited: Set[String] = Set()
  var order: List[String] = Nil

  def topologicalOrder(): List[String] = {
    adjList foreach {
      case (vertex, _) =>
        topologicalOrder(vertex)
    }
    order
  }

  def topologicalOrder(vertex: String): Unit = {
    if (!visited.contains(vertex)) {
      visited += vertex
      adjList.getOrElse(vertex, Nil).foreach { child =>
        topologicalOrder(child)
      }
      order ::= vertex
    }
  }
}
