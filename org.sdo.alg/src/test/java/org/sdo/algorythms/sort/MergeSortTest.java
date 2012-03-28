package org.sdo.algorythms.sort;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class MergeSortTest {
	@Test
	public void testIntersectionCalculation(){
		MergeSort mergeSort = new MergeSort();
		
		List<Integer> arrayInput = Arrays.asList(1,3,5,2,4,6);
		List result = mergeSort.sort(arrayInput);
		System.out.println();
		assertThat((Long)result.get(0), is(3L)); 
	}
}
