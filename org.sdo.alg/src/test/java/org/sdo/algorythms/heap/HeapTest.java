package org.sdo.algorythms.heap;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class HeapTest {

	@Test
	public void verify_that_bigger_key_doesnt_affect_root_element() {
		Heap heap = new Heap(new Integer[] { 4, 4, 8, 9 });
		heap.insert(13);
		assertThat(heap.extract(), is(4));
	}

	@Test
	public void verify_that_smallest_key_becomes_new_root_element() {
		Heap heap = new Heap(new Integer[] { 4, 4, 8, 9 });
		heap.insert(1);
		assertThat(heap.size(), is(5));
		assertThat(heap.extract(), is(1));
	}

	@Test
	public void verify_that_heapoaze_unsorted_array_make_it_sorted() {
		// given
		Integer[] unsortedArray = new Integer[] { 5, 8, 13, 5, 21, 6, 3, 7, -2, 4, 8, 12 };
		// when heapoise it
		Heap heap = new Heap();
		heap.heapoize(unsortedArray);
		
		// then exhausting heap will return us sorted sequence
		List<Integer> result = new ArrayList<>(unsortedArray.length);

		result = heap.drain();
				
		assertThat(result, is(Arrays.asList(-2, 3, 4, 5, 5, 6, 7, 8, 8, 12, 13, 21)));
	}

	@Test
	public void verify_that_root_will_use_5_node_for_bubble_down() {
		Heap heap = new Heap(new Integer[] { 4, 7, 5, 8 });
		int candidate = heap.extractBubbleDownCandidateInx(0, 4);
		assertThat(candidate, is(2));
	}

	@Test
	public void verify_that_root_will_use_single_child() {
		Heap heap = new Heap(new Integer[] { 4, 7, 5, 8 });
		int candidate = heap.extractBubbleDownCandidateInx(1, 7);
		assertThat(candidate, is(3));
	}

	@Test
	public void verify_that_no_children_returned_if_they_are_not_exist() {
		Heap heap = new Heap(new Integer[] { 4, 7, 5, 8 });
		int candidate = heap.extractBubbleDownCandidateInx(2, 5);
		assertThat(candidate, is(-1));
	}

	@Test
	public void verify_that_pop_returns_minimal_element() {
		Heap heap = new Heap(new Integer[] { 4, 4, 8, 9, 4, 12, 9, 11, 13 });
		assertThat(heap.extract(), is(4));
		assertThat(heap.size(), is(8));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void verify_that_extract_cannot_be_performed_on_empty_heap() {
		Heap heap = new Heap();
		heap.extract();		
	}

}
