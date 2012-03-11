package org.sdo.algorythms.sort;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MergeSort {

	public <T extends Comparable<? super T>> List<T> sort(List<T> arrayInput) {
		return mergeSort(arrayInput, new Comparator<T>() {
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		});
	}

	public <T> List<T> sort(List<T> arrayInput, Comparator<? super T> comparator) {
		return mergeSort(arrayInput, comparator);
	}
	
	private <T> LinkedList<T> mergeSort(List<T> array, Comparator<? super T> comparator) {
		System.out.println("sorting " + array);
		if (array.size() <= 1)
			return new LinkedList<T>(array);
		LinkedList<T> left = new LinkedList<T>(array.subList(0, array.size() / 2));
		LinkedList<T> right = new LinkedList<T>(array.subList(array.size() / 2, array.size()));
		left = mergeSort(left, comparator);
		right = mergeSort(right, comparator);
		return merge(left, right, comparator);
	}
	
	private <T> LinkedList<T> merge(Deque<T> left, Deque<T> right, Comparator<? super T> comparator) {
		System.out.println("merging " + left + " and " + right);
		LinkedList<T> result = new LinkedList<T>();
		while (!left.isEmpty() || !right.isEmpty()) {
			T leftInteger = left.poll();
			T rightInteger = right.poll();
			if (leftInteger != null && rightInteger != null) {
				int comparisionResult = comparator.compare(leftInteger, rightInteger);
				if (comparisionResult < 0) {
					result.add(leftInteger);
					right.push(rightInteger);
				} else if (comparisionResult > 0) {
					result.add(rightInteger);
					left.push(leftInteger);
				} else {
					result.add(rightInteger);
					result.add(leftInteger);
				}
			}

			if (leftInteger != null && rightInteger == null) {
				result.add(leftInteger);
			}
			if (leftInteger == null && rightInteger != null) {
				result.add(rightInteger);
			}
		}
		System.out.println("merged " + result);
		return result;
	}
}
