package org.sdo.algorythms.sort;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.sdo.algorythms.sort.QuickSort.PivotRule;

public class QuickSortTest {	

	@Test
	public void testPartition() {
		QuickSort quickSort = new QuickSort();
		quickSort.pivotRule = PivotRule.FIRST;
		List<Integer> arrayInput = Arrays.asList(3, 8, 2, 5, 1, 4, 7, 6);
		quickSort.partition(arrayInput);
		System.out.println("Partitioned is " + arrayInput);
		assertThat(arrayInput, is(Arrays.asList(1, 2, 3, 5, 8, 4, 7, 6)));
	}
}
