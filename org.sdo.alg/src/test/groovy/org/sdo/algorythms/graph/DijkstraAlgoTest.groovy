package org.sdo.algorythms.graph

import static org.hamcrest.CoreMatchers.*
import static org.hamcrest.MatcherAssert.*

import org.junit.Test;


class DijkstraAlgoTest {

	@Test
	void we_expect_that_simplest_graph_has_path_6_length(){
		//given a graph with 4 nodes
		def graph = new Graph(
			[
				['1', ['2', 1], ['3', 4]],
				['2', ['3', 2], ['4', 6]],
				['3', ['4', 3]],
				['4']
			]
		)
		//when
		def pathLength = graph.runDijkstra('1', '4')
		//then
		assertThat(pathLength, is(6))		
	}
    
    @Test
    void we_expect_that_direct_edge_with_high_weight_will_not_break_algo(){
        //given a graph with 4 nodes
        def graph = new Graph(
            [
                ['1', ['2', 1], ['3', 4], ['4', 20]],
                ['2', ['3', 2], ['4', 6]],
                ['3', ['4', 3]],
                ['4']
            ]
        )
        //when
        def pathLength = graph.runDijkstra('1', '4')
        //then
        assertThat(pathLength, is(6))
    }
	
	@Test
	void we_expect_that_another_graph_has_path_4_length(){
		//given a graph with 4 nodes
		def graph = new Graph(
			[
				['1', ['2', 1], ['3', 4], ['5', 20]],
				['2', ['3', 2], ['4', 6], ['5', 3]],
				['3', ['4', 3]],
				['4', ['5', 4]],
				['5']
			]
		)
		//when
		def pathLength = graph.runDijkstra('1', '5')
		//then
		assertThat(pathLength, is(4))
	}
	
	@Test
	void we_init_graph_correctly(){
		def graph = new Graph(
			[
				["1", ['2', 1], ['3', 4], ['5', 20]],
				['2', ['3', 2], ['4', 6], ['5', 3]],
				["3", ['4', 3]],
				["4", ['5', 4]],
				["5"]
			]
		)			
		assertThat('we expect 5 vertices', graph.verticeLength, is(5))
		assertThat("we expect 8 edges", graph.edgeLength, is(8))
		assertThat("vertex 2 should have references to 3,4,5 vertices", graph.vertixReferes('2'), hasItems("3","4","5"))
		assertThat("graph has edges (2,3), (2,4), (2,5)", graph.containsEdges([from : '2', to : '3', weight:2],[from:'2', to:'4', weight:6], [from:'2', to:'5', weight: 3]), is(true))
	}
	
	@Test
	void we_have_splitted_graph_and_want_to_find_all_edges_from_one_part_to_another(){
		def graph = new Graph(
			[
				['1', ['2', 1], ['3', 4]],
				['2', ['3', 2], ['4', 6]],
				['3', ['4', 3]],
				['4']
			]
		)
				
		def cutEdges = graph.extractCutEdges(['1' : 0, '2' : 0])
		
		
		assertThat('we expect 5 vertices', graph.verticeLength, is(4))
		assertThat("we expect 8 edges", graph.edgeLength, is(5))
		assertThat(cutEdges, hasItems([from:'1', to:'3', weight: 4], [from:'2', to:'3', weight: 2], [from:'2', to:'4', weight: 6]))
		assertThat(cutEdges.min{it.weight}, is([from:'2', to:'3', weight: 2]))
	}
	@Test
	void we_should_not_be_able_calc_path_to_unreachable_vertex(){
		def graph = new Graph(
			[
				['1', ['2', 1], ['5', 6]],
				['2'],
				['5', ['2', 3]],
				['4']
			]
		)
		
		//when
		def pathLength = graph.runDijkstra('1', '4')
		//then
		assertThat(pathLength, is(-1))
	}
	
	@Test
	void we_will_get_zero_path_on_self(){
		def graph = new Graph(
			[
				['1', ['2', 1], ['5', 6]],
				['2'],
				['5', ['2', 3]],
				['4']
			]
		)
		
		//when
		def pathLength = graph.runDijkstra('4', '4')
		//then
		assertThat(pathLength, is(0))
		
		//when
		pathLength = graph.runDijkstra('5', '5')
		//then
		assertThat(pathLength, is(0))
	}
	
	@Test
	void we_are_not_able_to_calc_path_from_vertex_without_outcoming_edges(){
		def graph = new Graph(
			[
				['1', ['2', 1], ['5', 6]],
				['2'],
				['5', ['2', 3]],
				['4']
			]
		)
		
		//when
		def pathLength = graph.runDijkstra('2', '1')
		//then
		assertThat(pathLength, is(-1))
	}
	
	@Test
	void sum_of_3_and_5_multipliers_below_1000(){
		def sum = 0 as Long		
		1000.times {
			if(!(it%3) || !(it%5)){
				println it
				sum+=it
			}
			
		}
		println sum
	}
}
