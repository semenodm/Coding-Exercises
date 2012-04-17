package org.sdo.algorythms.graph;

import static org.hamcrest.Matchers.*
import static org.hamcrest.MatcherAssert.*

import org.junit.Before;
import org.junit.Test;


class StrongComponentsTest {
	def graph
	def alg

	@Before
	void setUp(){
		graph = new Graph();
		graph.parseDirectGraph("./src/test/resources/strongComponents1.txt")
		alg = new DepthFirstSearch(graph: graph )
	}
	@Test
	void verifyThatDirectedGraphLoadedCorrectly(){
		assertThat(graph.nodeSize, is(9))
		assertThat(graph.vertices.get(1).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:7))))
		assertThat(graph.vertices.get(2).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:5))))
		assertThat(graph.vertices.get(3).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:9))))
		assertThat(graph.vertices.get(4).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:1))))
		assertThat(graph.vertices.get(5).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:8))))
		assertThat(graph.vertices.get(6).childVertices, allOf(hasSize(2),hasItems(new Vertex(vertexLabel:8), new Vertex(vertexLabel:3))))
		assertThat(graph.vertices.get(7).childVertices, allOf(hasSize(2),hasItems(new Vertex(vertexLabel:9), new Vertex(vertexLabel:4))))
		assertThat(graph.vertices.get(8).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:2))))
		assertThat(graph.vertices.get(9).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:6))))
	}

	@Test
	void verifyThatDFSFindsAllNodesFromTheFirst(){
		alg.search(1, graph.walkGraph)
		graph.vertices.values().each {
			assertThat(it.visited, is(true))
		}
	}

	@Test
	void verifyThatDFSFindsAllNodesFromTheNinth(){
		alg.search(9, graph.walkGraph)
		[9, 6, 3, 8, 2, 5].each {
			assertThat(graph.vertices.get(it).visited, is(true))
		}
		[1, 7, 4].each {
			assertThat(graph.vertices.get(it).visited, is(false))
		}
	}

	@Test
	void checkFinishedTimesAreCalculated(){
		alg.search(9, graph.walkGraph)
		assertThat(graph.vertices.get(9).finishedTimes, is(6))
		assertThat(graph.vertices.get(6).finishedTimes, is(5))
		assertThat(graph.vertices.get(3).finishedTimes, is(1))
		assertThat(graph.vertices.get(8).finishedTimes, is(4))
		assertThat(graph.vertices.get(2).finishedTimes, is(3))
		assertThat(graph.vertices.get(5).finishedTimes, is(2))
	}

	@Test
	void checkFinishedTimesAreCalculatedForFirstDFSLoop(){
		alg.firstDFSLoop()
		assertThat(graph.vertices.get(9).finishedTimes, is(6))
		assertThat(graph.vertices.get(6).finishedTimes, is(5))
		assertThat(graph.vertices.get(3).finishedTimes, is(1))
		assertThat(graph.vertices.get(8).finishedTimes, is(4))
		assertThat(graph.vertices.get(2).finishedTimes, is(3))
		assertThat(graph.vertices.get(5).finishedTimes, is(2))
		assertThat(graph.vertices.get(1).finishedTimes, is(7))
		assertThat(graph.vertices.get(7).finishedTimes, is(9))
		assertThat(graph.vertices.get(4).finishedTimes, is(8))

		assertThat(graph.reversedVertices.get(1).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:6))))
		assertThat(graph.reversedVertices.get(2).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:2))))
		assertThat(graph.reversedVertices.get(3).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:8))))
		assertThat(graph.reversedVertices.get(4).childVertices, allOf(hasSize(2),hasItems(new Vertex(vertexLabel:6),new Vertex(vertexLabel:5))))
		assertThat(graph.reversedVertices.get(5).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:9))))
		assertThat(graph.reversedVertices.get(6).childVertices, allOf(hasSize(2),hasItems(new Vertex(vertexLabel:7), new Vertex(vertexLabel:3))))
		assertThat(graph.reversedVertices.get(7).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:4))))
		assertThat(graph.reversedVertices.get(8).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:7))))
		assertThat(graph.reversedVertices.get(9).childVertices, allOf(hasSize(1),hasItems(new Vertex(vertexLabel:1))))
	}
	@Test
	void checkCSCAlgorithm(){
		def leaders = alg.runCSCAlg()
		println "leaders are ${leaders.keySet()}"
		assertThat(leaders, is(allOf(
				hasSize(3),
				hasItems(new Vertex(vertexLabel: 7),new Vertex(vertexLabel: 9),new Vertex(vertexLabel: 8))
				)));
	}
	
	@Test
	void doProgrammingExcercise(){
		graph = new Graph();
		graph.parseDirectGraph("./src/test/resources/SCC.txt")
		alg = new DepthFirstSearch(graph: graph )
		def leaders = alg.runCSCAlg()
		println "leaders are ${leaders.keySet()}" 
	}
}

