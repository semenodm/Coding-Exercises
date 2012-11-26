package common

import GraphCommon._

object GraphCommon {
  type Vertex = String
  type Edge = (Vertex, Vertex, Int)
}

class Graph(adjList: Map[Vertex, Set[Edge]]){
  
  def this(edges: (Edge)*) = {
    this(edges groupBy(e => e._1) map {case(v,e) => v -> e.toSet} withDefaultValue Set())
  }
  
  lazy val edges:Set[Edge] = adjList.values.flatMap(e=>e).toSet  
  lazy val vertexes: Set[Vertex] = adjList.keySet ++ (edges map {case(_,to,_) => to})

  
  def neighboursOf(v: Vertex):Set[Vertex] = adjList(v) map {case(_,to, _) => to}
  
  def incidentEdgesOf(v: Vertex):Set[Edge] = adjList(v)
  
  def contains(v: Vertex): Boolean = vertexes.contains(v)
  
  override def toString = {
	def printAdjEdges(v:Vertex): String = 
	  (adjList(v) map {case(_,to, w) => (to, w)}).mkString("(", ",", ")")
			  
	  (for (v <- vertexes) 
		yield v + " -> " + printAdjEdges(v)
	  ).mkString("\n")    
  }
}
