package org.sdo.algorythms.graph;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DijkstraShortestPathTest {
	
	private DijkstraGraph graph;

	@Before
	public void setUp(){
		graph = generateGraph();
	}

	@Test
	public void we_expect_that_simplest_graph_has_path_6_length(){		
		//when
		int pathLength = graph.runDijkstra("1", "4");
		//then
		assertThat(pathLength, is(6));		
	}

	@Test
	public void we_expect_that_way_not_found(){		
		//when
		Integer pathLength = graph.runDijkstra("2", "1");
		//then
		assertThat(pathLength, nullValue());		
	}

	@Test
	public void testGraphInitialization() {
		DijkstraGraph graph = generateGraph();
		
		assertThat(graph.getVertixes().size(), is(4));
		assertThat(graph.getEdges().size(), is(5));
	}

	
	@Test
	public void test_that_S_vertex_has_two_outgoing_edges(){
		Set<Edge> edges_from_one = graph.getEdgesFromVertex("1");
		Edge e1 = new Edge("1", "2", 1);
		Edge e2 = new Edge("1", "3", 4);
		assertThat(edges_from_one.size(), is(2));
		assertThat(edges_from_one.contains(e1), is(true));
		assertThat(edges_from_one.contains(e2), is(true));
	}
	
	@Test
	public void test_that_explored_verteces_has_three_edges(){
		HashSet<String> X = new HashSet<String>(Arrays.asList("1", "2"));
		Set<Edge> outgoingEdges = graph.getOutgoingEdsgesFrom(X);
		Edge e1 = new Edge("2", "3", 2);
		Edge e2 = new Edge("1", "3", 4);
		Edge e3 = new Edge("2", "4", 6);
		
		assertThat(outgoingEdges.size(), is(3));
		assertThat(outgoingEdges.contains(e1), is(true));
		assertThat(outgoingEdges.contains(e2), is(true));
		assertThat(outgoingEdges.contains(e3), is(true));
		
	}
	
	@Test
	public void test_greedy_criteria(){
		HashMap<String, Integer> X = new HashMap<String,Integer>();
		X.put("1", 0);
		X.put("2", 1);
		Edge criteria = graph.runGreedy(X);
		Edge expectedCriteria = new Edge("2", "3", 2);
		assertThat(criteria, is(expectedCriteria));
	}
	
	private DijkstraGraph generateGraph() {
		Edge[] adjList = new Edge[]{
				new Edge("1", "2", 1), new Edge("1", "3", 4), 
				new Edge("2", "3", 2), new Edge("2", "4", 6),
				new Edge("3", "4", 3)			
		};
		
		DijkstraGraph graph = new DijkstraGraph(adjList);
		return graph;
	}	

}
