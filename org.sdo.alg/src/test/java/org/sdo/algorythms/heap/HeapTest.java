package org.sdo.algorythms.heap;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class HeapTest {
	@Test
	public void verify_that_when_one_element_is_removed_then_tree_is_reordered() {
		Heap heap = new Heap(new Integer[] { 4, 4, 8, 9, 4, 12, 9, 11, 13 });
		Integer poppedVal = heap.extract();
		assertThat(heap.extract(), is(4));
	}

	@Test
	public void verify_that_bigger_key_doesnt_affect_root_element() {
		Heap heap = new Heap(new Integer[] { 4, 4, 8, 9 });
		heap.insert(13);
		assertThat(heap.extract(), is(4));
	}

	@Test
	public void verify_that_smaller_key_becomes_new_root_element() {
		Heap heap = new Heap(new Integer[] { 4, 4, 8, 9 });
		heap.insert(1);
		assertThat(heap.extract(), is(1));
	}

	@Test
	public void verify_that_pop_returns_minimal_element() {
		Heap heap = new Heap(new Integer[] { 4, 4, 8, 9, 4, 12, 9, 11, 13 });
		assertThat(heap.extract(), is(4));
		assertThat(heap.size(), is(8));
	}

	@Test
	public void verify_that_we_can_store_heap_in_array() {
		// given a heap
		Heap heap = new Heap(new Integer[] { 4, 4, 8, 9, 4, 12, 9, 11, 13 });
		assertThat(heap.parent(3), is(4));
		assertThat(heap.parent(2), is(4));

		assertThat(heap.children(2)[0], is(12));
		assertThat(heap.children(2)[1], is(9));

		assertThat(heap.children(3)[0], is(11));
		assertThat(heap.children(3)[1], is(13));
	}

}
