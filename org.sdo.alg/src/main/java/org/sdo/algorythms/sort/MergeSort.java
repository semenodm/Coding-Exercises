package org.sdo.algorythms.sort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MergeSort {

	public <T extends Comparable<? super T>> List<Object> sort(List<T> arrayInput) {
		return mergeSort(arrayInput, new Comparator<T>() {
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		});
	}

	public <T> List<Object> sort(List<T> arrayInput, Comparator<? super T> comparator) {
		return mergeSort(arrayInput, comparator);
	}

	private <T> List<Object> mergeSort(List<T> array, Comparator<? super T> comparator) {
		//System.out.println("sorting " + array);
		if (array.size() <= 1) {
			return Arrays.<Object> asList(Long.valueOf(0), new LinkedList<T>(array));
		}

		int median = array.size() >>> 1;
		LinkedList<T> left = new LinkedList<T>(array.subList(0, median));
		LinkedList<T> right = new LinkedList<T>(array.subList(median, array.size()));
		List<Object> leftWithIntersections = mergeSort(left, comparator);
		List<Object> rightWithIntersections = mergeSort(right, comparator);
		List<Object> merged = merge((Deque<T>)leftWithIntersections.get(1), (Deque<T>)rightWithIntersections.get(1), comparator);
		long intesections = (Long) leftWithIntersections.get(0) + (Long) rightWithIntersections.get(0) + (Long) merged.get(0);
		merged.set(0, Long.valueOf(intesections));
		return merged;
	}

	private <T> List<Object> merge(Deque<T> left, Deque<T> right, Comparator<? super T> comparator) {
		//System.out.println("merging " + left + " and " + right);
		LinkedList<T> result = new LinkedList<T>();
		long intersections = 0L;
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
					//System.out.println("Number of intesections is: " + intersections);
					intersections += left.size();
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
		//System.out.println("merged " + result);
		return Arrays.<Object>asList(Long.valueOf(intersections), result);
	}
}
