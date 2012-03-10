package org.sdo.algorythms.sort;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class MergeSort {

	public List<Integer> sort(List<Integer> arrayInput) {
		return mergeSort(arrayInput);
	}

	private LinkedList<Integer> mergeSort(List<Integer> array) {
		System.out.println("sorting " + array);
		if (array.size() <= 1)
			return new LinkedList<Integer>(array);
		LinkedList<Integer> left = new LinkedList<Integer>(array.subList(0,
				array.size() / 2));
		LinkedList<Integer> right = new LinkedList<Integer>(array.subList(
				array.size() / 2, array.size()));
		left = mergeSort(left);
		right = mergeSort(right);
		return merge(left, right);
	}

	private LinkedList<Integer> merge(Deque<Integer> left, Deque<Integer> right) {
		System.out.println("merging " + left + " and " + right);
		LinkedList<Integer> result = new LinkedList<Integer>();
		while (!left.isEmpty() || !right.isEmpty()) {
			Integer leftInteger = left.poll();
			Integer rightInteger = right.poll();

			if (leftInteger != null && rightInteger != null) {
				if (leftInteger < rightInteger) {
					result.add(leftInteger);
					right.push(rightInteger);
				} else if (leftInteger > rightInteger) {
					result.add(rightInteger);
					left.push(leftInteger);
				}else{					
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
