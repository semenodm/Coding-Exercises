package org.sdo.algorythms.sort;
import static org.hamcrest.Matchers.is
import static org.junit.Assert.*

import org.junit.Test;
import org.sdo.algorythms.sort.QuickSort.PivotRule;

class QuickSortComparisionTest {
	def quickSort
	
	public QuickSortComparisionTest() {
		super();
		this.quickSort = new QuickSort()
	}


	@Test
	void testComparisionsFIRSTForArray(){
		def arrayInput = []
				
		new File("./src/test/resources/QuickSort.txt").eachLine {
			arrayInput << Integer.valueOf(it)
		}
		
		quickSort.sort(arrayInput, PivotRule.FIRST);
		
		assertThat(quickSort.comparisions, is(162085L));
	}
	
	@Test
	void testComparisionsLASTForArray(){
		def arrayInput = []
				
		new File("./src/test/resources/QuickSort.txt").eachLine {
			arrayInput << Integer.valueOf(it)
		}
		
		quickSort.sort(arrayInput, PivotRule.LAST);
		
		assertThat(quickSort.comparisions, is(164123L));
	}
	
	@Test
	void testComparisionsMEDIANorArray(){
		def arrayInput = []
				
		new File("./src/test/resources/QuickSort.txt").eachLine {
			arrayInput << Integer.valueOf(it)
		}
		
		quickSort.sort(arrayInput, PivotRule.MEDIAN);
		
		assertThat(quickSort.comparisions, is(138382L));
	}
}
