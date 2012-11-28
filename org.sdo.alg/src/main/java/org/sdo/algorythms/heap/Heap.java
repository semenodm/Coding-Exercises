package org.sdo.algorythms.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Heap {
	private List<Integer> storage;

	public Heap(Integer[] integers) {
		this.storage = new ArrayList<>(Arrays.asList(integers));
	}

	public Heap() {
		this.storage = new ArrayList<>();
	}

	private int parentIndex(int i) {
		return (i + 1) / 2 - 1;
	}

	private int firstChildIndex(int i) {
		return secondChildIndex(i) - 1;
	}

	private int secondChildIndex(int i) {
		return (i + 1) * 2;
	}

	public Integer extract() {
		Integer root = rootElement();
		if (heapHasMoreThenOneElement()) {
			swap(0, lastIndex());
			storage.remove(lastIndex());
			bubbleDown(0, rootElement());
		}
		else {
			storage.remove(0);
		}
		return root;
	}

	private void bubbleDown(int rInx, Integer root) {
		int childInx = extractBubbleDownCandidateInx(rInx, root);
		if (childInx != -1 && heapPropertyViolated(storage.get(childInx), root)) {
			swap(childInx, rInx);
			bubbleDown(childInx, root);
		}

	}

	private boolean childExist(int inx) {
		return inx < storage.size();
	}

	private int lastIndex() {
		return storage.size() - 1;
	}

	private Integer rootElement() {
		if (storage.isEmpty())
			throw new UnsupportedOperationException("Root element cannot be extracted on empty heap");
		return storage.get(0);
	}

	public Integer size() {
		return storage.size();
	}

	public void insert(Integer newValue) {
		storage.add(newValue);
		if (heapHasMoreThenOneElement()) {
			int vInx = lastIndex();
			int pInx = parentIndex(vInx);
			Integer parentValue = storage.get(pInx);

			bubbleUp(vInx, newValue, pInx, parentValue);
		}
	}

	private void bubbleUp(int vInx, Integer newValue, int pInx, Integer parentValue) {
		if (heapPropertyViolated(newValue, parentValue)) {
			swap(vInx, pInx);
			if (!isRoot(pInx)) {
				int renuedVInx = pInx;
				int renuedPInx = parentIndex(renuedVInx);
				Integer renuedParentValue = storage.get(renuedPInx);
				bubbleUp(renuedVInx, newValue, renuedPInx, renuedParentValue);
			}
		}
	}

	private boolean isRoot(int pInx) {
		return pInx == 0;
	}

	private void swap(int vInx, int pInx) {
		Integer parentValue = storage.get(pInx);
		Integer value = storage.set(vInx, parentValue);
		storage.set(pInx, value);
	}

	private boolean heapPropertyViolated(Integer newValue, Integer parentValue) {

		return newValue.compareTo(parentValue) < 0;
	}

	private boolean heapHasMoreThenOneElement() {
		return storage.size() > 1;
	}

	public int extractBubbleDownCandidateInx(int rootInx, Integer root) {

		int firstChildIndex = firstChildIndex(rootInx);
		int secondChildIndex = secondChildIndex(rootInx);
		int[] children = new int[2];
		if (childExist(firstChildIndex)) {
			children[0] = firstChildIndex;
		}
		else {
			// bad practice
			return -1;
		}

		if (childExist(secondChildIndex)) {
			children[1] = secondChildIndex;
		}
		else {
			return children[0];
		}

		return storage.get(children[0]) > storage.get(children[1]) ? children[1] : children[0];
	}

	public void heapoize(Integer[] unsortedArray) {
		for (Integer integer : unsortedArray) {
			insert(integer);
		}		
	}

	public List<Integer> drain() {
		List<Integer> result = new ArrayList<>(size());
		while (size() > 0) {
			result.add(extract());
		}
		return result;
	}

}
