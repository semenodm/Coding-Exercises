package org.sdo.algorythms.heap;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class HeapTest {
	@Test
	public void verify_that_when_one_element_is_removed_then_tree_is_reordered() {
		Heap<Integer> heap = new Heap<>(new Integer[] { 4, 4, 8, 9, 4, 12, 9, 11, 13 });
		Integer poppedVal = heap.extract();
		assertThat(heap.extract(), is(4));
	}

	@Test
	public void verify_that_candidate_chosen_correctly_when_both_child_exist(){
		Heap<Integer> heap = new Heap<>(new Integer[] { 4, 7, 8 });
		assertThat(heap.getCandidateInx(0), is(1));
	}
	
	@Test
	public void verify_that_candidate_chosen_correctly_when_only_left_child_exist(){
		Heap<Integer> heap = new Heap<>(new Integer[] { 4, 7 });
		assertThat(heap.getCandidateInx(0), is(1));
	}
	
	@Test
	public void verify_that_candidate_chosen_correctly_when_no_child_exist(){
		Heap<Integer> heap = new Heap<>(new Integer[] { 4 });
		assertThat(heap.getCandidateInx(0), is(-1));
	}	
	
	@Test
	public void verify_that_bigger_key_doesnt_affect_root_element() {
		Heap<Integer> heap = new Heap<>(new Integer[] { 4, 4, 8, 9 });
		heap.insert(13);
		assertThat(heap.extract(), is(4));
	}

	@Test
	public void verify_that_extract_root_element_from_heap_with_root_only_turns_heap_into_empty() {
		Heap<Integer> heap = new Heap<>(new Integer[] { 4 });
		assertThat(heap.extract(), is(4));
		assertThat(heap.isEmpty(), is(true));
	}
	
	@Test
	public void verify_that_extract_root_element_reduce_size_of_heap_and_affects_root_element() {
		Heap<Integer> heap = new Heap<>(new Integer[] { 4, 8, 7, 9 });
	
		assertThat(heap.extract(), is(4));
		assertThat(heap.size(), is(3));
		assertThat(heap.root(), is(7));
	}

	@Test
	public void verify_that_smaller_key_becomes_new_root_element() {
		Heap<Integer> heap = new Heap<>(new Integer[] { 4, 4, 8, 9 });
		heap.insert(1);
		assertThat(heap.extract(), is(1));
	}

	@Test
	public void verify_that_pop_returns_minimal_element() {
		Heap<Integer> heap = new Heap<>(new Integer[] { 4, 4, 8, 9, 4, 12, 9, 11, 13 });
		assertThat(heap.extract(), is(4));
		assertThat(heap.size(), is(8));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void verify_that_pop_throws_exception_on_empty_array() {
		Heap<Integer> heap = new Heap<>(new Integer[] { });
		heap.extract();

	}

	@Test
	public void check_that_heapoised_random_array_become_sorted(){
		//given a random array
		Integer[] input = new Integer[] {5, 8, 13, 5, 21, 6, 3, 7, -2, 4, 8, 12};
		Heap<Integer> heap = new Heap<>();
		//when heapoise array
		heap.heapify(input);
		//draining the heap gives sorted array
		assertThat(heap.drain(), is(new Integer[]{-2, 3, 4, 5, 5, 6, 7, 8, 8, 12, 13, 21}));
		
	}

}
