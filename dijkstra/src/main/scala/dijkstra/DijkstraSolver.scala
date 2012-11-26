package dijkstra

import common._

import common.Graph
import common.GraphCommon._

object DijkstraSolverCommon {
  type Path = List[Edge]
  type Solution = (Int, Path)
  
  val ? : Null = null
  
  def dijkstraGreedyCriteria(edges: Set[Edge], exploredSolutions: Map[Vertex, Solution]): Edge =
    if(edges.isEmpty) ? 
    else edges minBy{case (from, _, w) => exploredSolutions(from)._1 + w}
}

import DijkstraSolverCommon._

class DijkstraSolver(g:Graph, start:Vertex, end:Vertex) {
  require(g.contains(start), "Start vertex must be present in graph")
  require(g.contains(end), "End vertex must be present in graph")
  
  def getOutgoingEdges(vertexes: Set[Vertex]): Set[Edge] =
    for {
      v <- vertexes
      edge <- g.incidentEdgesOf(v)
      if !vertexes(edge._2)
    } yield edge
  
  def findNextSolution(solutions: Map[Vertex, Solution], selectEdge: (Set[Edge], Map[Vertex, Solution]) => Edge): (Vertex, Solution) = {
      selectEdge(getOutgoingEdges(solutions.keySet), solutions) match {
        case ?  => ? 
        case (from, to, weight) => {
          val edge = (from, to, weight) 
          val (estimate, path) = solutions(from)
          (to, (estimate + weight, edge :: path))         
        }
      }
    }
    
  def findNextSolution(solutions: Map[Vertex, Solution]): (Vertex, Solution) = 
    findNextSolution(solutions, dijkstraGreedyCriteria)
  
  private def findSolution(solutions: Map[Vertex, Solution]): Solution = {
    if(solutions.contains(end)) solutions(end)
    else {
      findNextSolution(solutions) match {
        case ?  => ? 
        case (vertex, solution) => findSolution(solutions + (vertex -> solution))
      }
    }
  }
  
  private val initState: Map[Vertex, Solution] = Map(start -> (0, List()))
  
  lazy val solution: Solution = findSolution(initState) match {
    case ?  => ? 
    case (value, path) => (value, path.reverse)
  }
}