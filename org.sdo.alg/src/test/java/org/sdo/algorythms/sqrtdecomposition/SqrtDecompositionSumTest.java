package org.sdo.algorythms.sqrtdecomposition;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.sdo.algorythms.sqrtdecomposition.SqrtDecompositionSum;

public class SqrtDecompositionSumTest {

	@Test
	public void proveTheConcept() {
		// given an array
		int[] input = new int[] { 5, 10, -3, 17, 12, 1, -2, 13, -12 };

		// when calculate local sums
		SqrtDecompositionSum alg = new SqrtDecompositionSum(input);
		int[] localSums = alg.calcLocalSums();

		// then
		assertThat(localSums.length, is(3));
		assertThat(localSums[0], is(12));
		assertThat(localSums[1], is(42));
		assertThat(localSums[2], is(41));
		assertThat(alg.calcSummBetween(2, 5), is(27));
		assertTrue("Complexity is: " + alg.getComplexity(), alg.getComplexity() <= 5);	
		
		assertThat(alg.calcSummBetween(4, 7), is(24));
		assertTrue("Complexity is: " + alg.getComplexity(), alg.getComplexity() <= 5);
		assertThat(alg.calcSummBetween(3, 8), is(29));
		assertTrue("Complexity is: " + alg.getComplexity(), alg.getComplexity() <= 6);
		alg.set(3, 12);
		assertThat(alg.calcSummBetween(2, 5), is(22));		
	}

	@Test
	public void proveTheConcept2() {
		// given an array
		int[] input = new int[] { 5, 8, 13, 5, 21, 6, 3, 7, -2, 4, 8, 12 };

		// when calculate local sums
		SqrtDecompositionSum alg = new SqrtDecompositionSum(input);
		int[] localSums = alg.calcLocalSums();

		// then
		assertThat(localSums.length, is(3));
		assertThat(localSums[0], is(26));
		assertThat(localSums[1], is(58));
		assertThat(localSums[2], is(66));		
		assertThat(alg.calcSummBetween(3, 10), is(52));
		System.out.println("Complexity is: " + alg.getComplexity());
		assertTrue("Complexity is: " + alg.getComplexity(), alg.getComplexity()<7);
	}

}
