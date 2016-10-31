package org.sdo.algorythms.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Heap<T extends Comparable<T>> {
	private static final int ROOT_INDEX = 0;

	private List<T> storage;

	public Heap(T[] integers) {
		this.storage = new ArrayList<T>(Arrays.<T>asList(integers));
	}

	public Heap() {
		this.storage = new ArrayList<T>();
	}

	private int parentIndex(int inx) {
		return (inx - 1) / 2;
	}

	private int getRightChildIndex(int parentIndex) {
		return (parentIndex + 1) * 2;
	}

	private int getLeftChildIndex(int parentIndex) {
		return getRightChildIndex(parentIndex) - 1;
	}

	public T extract() {
		if (isEmpty()) {
			throw new UnsupportedOperationException("Can not extract from empty heap");
		}
		T last = storage.remove(lastIndex());
		if (isEmpty()) {
			return last;
		}
		T result = storage.get(0);
		storage.set(0, last);
		bubbleDown(ROOT_INDEX);
		return result;
	}

	public boolean isEmpty() {
		return storage.isEmpty();
	}

	private void bubbleDown(int rootIndex) {
		int candidateInx = getCandidateInx(rootIndex);
		if (candidateInx != -1 && violateHeapRule(candidateInx, rootIndex)) {
			swap(rootIndex, candidateInx);
			bubbleDown(candidateInx);
		}
	}

	int getCandidateInx(int rootIndex) {
		int rightIndex = getRightChildIndex(rootIndex);
		int leftIndex = getLeftChildIndex(rootIndex);
		int candidateInx = -1;
		if (rightIndex <= lastIndex()) {
			candidateInx = storage.get(leftIndex).compareTo(storage.get(rightIndex)) > 0 ? rightIndex : leftIndex;
		}
		else if (leftIndex <= lastIndex()) {
			candidateInx = leftIndex;
		}
		return candidateInx;
	}

	private int lastIndex() {
		return size() - 1;
	}

	public Integer size() {
		return storage.size();
	}

	public void insert(T value) {
		storage.add(value);
		bubbleUp(storage.size() - 1, value);
	}

	private void bubbleUp(int currentInx, T current) {
		int parentInx = parentIndex(currentInx);
		if (!isRoot(currentInx) && violateHeapRule(currentInx, parentInx)) {
			swap(currentInx, parentInx);
			bubbleUp(parentInx, current);
		}
	}

	private boolean isRoot(int currentInx) {

		return currentInx == 0;
	}

	private void swap(int currentInx, int parentInx) {

		T temp = storage.get(currentInx);
		storage.set(currentInx, storage.get(parentInx));
		storage.set(parentInx, temp);

	}

	private boolean violateHeapRule(int currentInx, int parentInx) {
		return storage.get(parentInx).compareTo(storage.get(currentInx)) > 0;
	}

	public T root() {
		return storage.get(0);
	}

	public void heapify(T[] input) {
		for (T i : input) {
			insert(i);
		}
	}

	public T[] drain() {
		T[] drainedHeap = (T[]) new Object[size()];
		for (int i = 0; i < drainedHeap.length; i++) {
			drainedHeap[i] = extract();
		}
		return drainedHeap;
	}

}
