package org.sdo.algorythms.sort;
import static org.hamcrest.Matchers.is
import static org.junit.Assert.*

import org.junit.Test;

import sun.misc.Resource

class MergeIntersectionsTest {
	def mergeSort
	public MergeIntersectionsTest() {
		super();
		this.mergeSort = new MergeSort()
	}


	@Test
	void testIntesectionsForArray(){
		def arrayInput = []

		
		
		new File("./src/test/resources/IntegerArray.txt").eachLine {
			arrayInput << Integer.valueOf(it)
		}

		def result = mergeSort.sort(arrayInput);
		assertThat((Long)result.get(0), is(2407905288L));
	}
}
