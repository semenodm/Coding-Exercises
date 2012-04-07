package org.sdo.algorythms.graph;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


class GraphTest {
	def graph
	@Before
	void setUp(){
		graph = new Graph();
		graph.parsGraph("./src/test/resources/simpleData.txt")
	}

	@Test
	void testGraphInited(){

		//graph.graphContainer.each { println it.dump() }
		assertThat(graph.graphContainer.keySet(), is(allOf(
				hasSize(10),
				hasItems(new Pair(1, 2), new Pair(1, 3), new Pair(2, 1), new Pair(2, 3), new Pair(2, 4), new Pair(3, 1), new Pair(3, 2), new Pair(3, 4), new Pair(4, 2), new Pair(4, 3))
				)));

		assertThat(graph.graphContainer.values(), is(allOf(
				hasSize(10),
				everyItem(is(1)))
				));
	}

	@Test
	void testBreakLink(){
		def alg = new MinCut(graph)
		alg.breakLink(new Pair(1,3))

		graph.graphContainer.each { println it.dump() }

		assertThat(graph.graphContainer.keySet(), is(allOf(
				hasSize(6),
				hasItems(new Pair(1, 2), new Pair(1, 4), new Pair(2, 1), new Pair(2, 4), new Pair(4, 1), new Pair(4, 2))
				)));

		assertThat(graph.graphContainer.values() as ArrayList, is(allOf(
				hasSize(6),
				is([1, 1, 2, 2, 1, 1]))
				));
	}

	@Test
	void testBreakLinkSecondStep(){
		def alg = new MinCut(graph)
		alg.breakLink(new Pair(1,3))
		alg.breakLink(new Pair(1,2))

		graph.graphContainer.each { println it.dump() }

		assertThat(graph.graphContainer.keySet(), is(allOf(
				hasSize(2),
				hasItems(new Pair(1, 4), new Pair(4, 1))
				)));

		assertThat(graph.graphContainer.values() as ArrayList, is(allOf(
				hasSize(2),
				is([2, 2]))
				));
	}
	@Test
	void testBreakLinkSecondStep2312(){
		def alg = new MinCut(graph)
		alg.breakLink(new Pair(2,3))
		alg.breakLink(new Pair(1,2))

		graph.graphContainer.each { println it.dump() }

		assertThat(graph.graphContainer.keySet(), is(allOf(
				hasSize(2),
				hasItems(new Pair(1, 4), new Pair(4, 1))
				)));

		assertThat(graph.graphContainer.values() as ArrayList, is(allOf(
				hasSize(2),
				is([2, 2]))
				));
	}
	@Test
	void testBreakLinkSecondStepAnother(){
		def alg = new MinCut(graph)
		alg.breakLink(new Pair(1,3))
		alg.breakLink(new Pair(2,4))

		graph.graphContainer.each { println it.dump() }

		assertThat(graph.graphContainer.keySet(), is(allOf(
				hasSize(2),
				hasItems(new Pair(1, 2), new Pair(2, 1))
				)));

		assertThat(graph.graphContainer.values() as ArrayList, is(allOf(
				hasSize(2),
				is([3, 3]))
				));
	}

	@Test
	void testEntireAlgorithm(){
		def numberOfAttempts = (4 * 4 * Math.log(4)) as Integer
		def minCut = MinCut.calcMinCutN("./src/test/resources/simpleData.txt", numberOfAttempts)


		assertThat(minCut, is(2))
	}
	
	@Test
	void testEntireAlgorithmOnRealData(){
		def numberOfAttempts = (40 * 40 * Math.log(40)) as Integer
		def minCut = MinCut.calcMinCutN("./src/test/resources/kargerAdj.txt", numberOfAttempts)


		assertThat(minCut, is(3))
	}
}
