package org.sdo.algorythms.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuickSort {

	public static enum PivotRule {
		FIRST, LAST, MEDIAN, RANDOM
	}

	protected long comparisions;
	protected PivotRule pivotRule;

	public void sort(List<Integer> arrayInput, PivotRule pivotRule) {
		this.pivotRule = pivotRule;
		comparisions = quickSort(arrayInput);
		System.out.println("Number of comparisions is: " + comparisions);
	}

	private long quickSort(List<Integer> arrayInput) {
		if (arrayInput.size() <= 1)
			return 0;
		int pivotIndex = partition(arrayInput);
		long compLess = quickSort(arrayInput.subList(0, pivotIndex));
		long compMore = quickSort(arrayInput.subList(pivotIndex + 1, arrayInput.size()));
		return compLess + compMore + arrayInput.size() - 1;
	}

	protected int partition(List<Integer> subList) {
		switch (pivotRule) {
		case FIRST:
			break;
		case LAST:
			swap(subList, 0, subList.size() - 1);
			break;
		case MEDIAN:
			int median = ((subList.size() - 1) >>> 1);
			List<Integer> list = Arrays.asList(subList.get(0), subList.get(median), subList.get(subList.size() - 1));

			Collections.sort(list);
			Integer integer = list.get(1);
			if (integer.equals(subList.get(median))) {
				swap(subList, 0, median);
			} else if (integer.equals(subList.get(subList.size() - 1))) {
				swap(subList, 0, subList.size() - 1);
			}
			break;
		default:
			throw new UnsupportedOperationException("" + pivotRule + " is not supported yet");
		}
		
		Integer pivot = subList.get(0);
		int i = 1;
		
		for (int j = 1; j < subList.size(); j++) {
			if (subList.get(j) < pivot) {
				swap(subList, i, j);
				i++;
			}
		}
		swap(subList, 0, i - 1);
		return i - 1;
	}

	private void swap(List<Integer> subList, int i, int j) {
		Integer temp = subList.get(i);
		subList.set(i, subList.get(j));
		subList.set(j, temp);
	}
}