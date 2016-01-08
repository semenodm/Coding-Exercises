package org.sdo.alg.graph

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dsemenov
  * Date: 12/23/15.
  */
class TopologicalOrderTest extends FlatSpec with Matchers {

  it should "traverse independent nodes in any order" in {
    val topoGraph = TopoGraph(Map("A" -> Nil))
    topoGraph.topologicalOrder() should contain("A")
  }

  it should "visit parent vertex and it's child" in {
    val topoGraph = TopoGraph(Map("A" -> List("B")))
    topoGraph.topologicalOrder() shouldBe List("A", "B")

  }

  it should "visit parent vertex and it's child1" in {
    val topoGraph = TopoGraph(Map("A" -> List("B"), "B" -> List("C")))
    topoGraph.topologicalOrder() shouldBe List("A", "B", "C")

  }

  it should "visit parent vertex and it's multiple children" in {
    val topoGraph = TopoGraph(
      Map("A" -> List("B", "C"))
    )
    topoGraph.topologicalOrder() shouldBe List("A", "C", "B")
  }


  it should "visit parent vertex and it's multiple children1" in {
    val topoGraph = TopoGraph(
      Map(
        "A" -> List("B", "C", "F"),
        "B" -> List("F"),
        "C" -> List("F"),
        "E" -> List("A", "B"),
        "G" -> List("D")
      )
    )
    topoGraph.topologicalOrder() shouldBe List("G", "D", "E", "A", "C", "B", "F")
  }

}
