package org.sdo.algorythms.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Heap {
	private List<Integer> storage;
	public Heap(Integer[] integers) {
		this.storage = new ArrayList<Integer>(Arrays.asList(integers));
	}
	public Integer parent(int i) {		
		return storage.get((i-1)/2);
	}
	public Integer[] children(int i) {		
		return new Integer[]{storage.get((i+1)*2 -1 ),storage.get((i+1)*2)};
	}
	public Integer extract() {
		return storage.remove(0);
	}
	public Integer size() {
		return storage.size();
	}
	public void insert(Integer value) {
		storage.add(value);
		
	}

}

