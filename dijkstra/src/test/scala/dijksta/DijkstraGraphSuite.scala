package dijksta

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import common.Graph
import common.GraphCommon._
import java.util.NoSuchElementException
import dijkstra.DijkstraSolver
import dijkstra.DijkstraSolverCommon._
import dijkstra.DijkstraSolver


@RunWith(classOf[JUnitRunner])
class DijkstraGraphSuite extends FunSuite {
  trait Vertexes {
	val s: Vertex = "S"
	val v: Vertex = "V"
	val w: Vertex = "W"
	val t: Vertex = "T"
  }
  
  trait TestGraph extends Vertexes{
    
    val sv:Edge = (s, v, 1)
    val sw:Edge = (s, w, 4)
    val vw:Edge = (v, w, 2)
    val vt:Edge = (v, t, 6)
    val wt:Edge = (w, t, 3)
    
    val graph = new Graph(
    		sv, sw,
    		vw, vt,
    		wt
        )
    val solver = new DijkstraSolver(graph, s, t)
  }
  
  trait TestGraph2 extends Vertexes{
    
    val sv:Edge = (s, v, 2)
    val sw:Edge = (s, w, 4)
    val vw:Edge = (v, w, 2)
    val vt:Edge = (v, t, 4)
    val wt:Edge = (w, t, 3)
    
    val graph = new Graph(
    		sv, sw,
    		vw, vt,
    		wt
        )
    val solver = new DijkstraSolver(graph, s, t)
  }
  
  test("test that graph was initialized correctly") {
    new TestGraph {
      assert(graph.vertexes === Set(s, v, w, t))
      assert(graph.edges === Set(sv, sw, vw, vt, wt))
    }
  }
  
  test("test that vertex S has neighbours V and W") {
    new TestGraph {
      assert(graph.neighboursOf(s) === Set(v, w))
    }    
  }
  
  test("test that vertex S has incident edges SV and SW") {
    new TestGraph {
      assert(graph.incidentEdgesOf(s) === Set(sv, sw))
    }    
  }
  
  test("test that vertex T has no incident edges") {
    new TestGraph {
      assert(graph.incidentEdgesOf(t) === Set())
    }    
  }
  
  test("test that graph contains vertex T") {
    new TestGraph {
      assert(graph.contains(t) === true)
    }    
  }
  
  test("test that graph doesn't contain vertex A") {
    new TestGraph {
      assert(graph.contains("A") === false)
    }    
  }
  
  test("test solver does not initialize when start or end is not in graph") {
    new TestGraph {
      intercept[IllegalArgumentException] {
    	  new DijkstraSolver(graph, "A", s)
      }
      intercept[IllegalArgumentException] {
    	  new DijkstraSolver(graph, w, "Z")
      }
      new DijkstraSolver(graph, w, t)
    }    
  }
  
  test("test that outgoing edges of vertexes (S) is (SV, SW)") {
    new TestGraph {
      assert(solver.getOutgoingEdges(Set(s)) === Set(sv, sw))
    }    
  }
  
  test("test that outgoing edges of vertexes (S, V) is (SW, VT, VW)") {
    new TestGraph {
      assert(solver.getOutgoingEdges(Set(s, v)) === Set(sw, vt, vw))
    }    
  }
  
  test("test that next solution after (S, V) is W") {
    new TestGraph {
      val solutions:Map[Vertex, Solution] = Map(s -> (0, List()), v -> (1, List(sv)))
      assert(solver.findNextSolution(solutions) === (w, (3, List(vw, sv))))
    }    
  }
  
  test("test there is no next available solution") {
    new TestGraph {
      val wood = new Graph(sv, wt)
      val woodSolver = new DijkstraSolver(wood, s, t)
      val solutions:Map[Vertex, Solution] = Map(s -> (0, List()), v -> (1, List(sv)))
      assert(woodSolver.findNextSolution(solutions) === ?)
    }    
  }
  
  test("test that shortest path is (sv, vw, wt) with length 6") {
    new TestGraph {
      solver.solution match {
        case (value, path) => {
          assert(value === 6)
          assert(path === List(sv, vw, wt))
        }
      }
    }   
  }
  
  test("test that shortest path of 2nd graph is (sv, vt) with length 6") {
    new TestGraph2 {
      solver.solution match {
        case (value, path) => {
          assert(value === 6)
          assert(path === List(sv, vt))
        }
      }
    }   
  }  
  
  test("test that there is no path between w and s") {
    new TestGraph {
      assert(new DijkstraSolver(graph, w, s).solution === ?)
    }   
  }
  
  
}
