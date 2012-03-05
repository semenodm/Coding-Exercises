package org.sdo.algorythms.mostcommonsum;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.sdo.algorythms.mostcommonsum.Algorithm;
import org.sdo.algorythms.mostcommonsum.TreeMapHashAlgorithm;

@RunWith(Parameterized.class)
public class MostCommonIntegerSumAlgTest {
	private int output;
	private int[] input;
	private Algorithm alg;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] {
						{ new int[] { 2, 4, 5, 6, 4 }, 8 }, // 4*2
						{ new int[] { 1, 2, 1, 3, 1 }, 3 }, // 3*1
						{ new int[] { 1, 200000, 1, 3, 1 }, 3 }, // 3*1
						{ new int[] { 1, 2, 3, 4, 5 }, 1 }, // 3*1
						{ new int[] { 1, 0, 1, 0, 1 }, 3 }, // 3*1
						{ new int[] { 1, 1, 2, 3, 4, 5, 6, 7, 8 }, 2 }, // 3*1
						{
								new int[] { 1, 4, 6, 3, 4, 8, 6, 5, 4, 7, 8, 3,
										2, 6, 8 }, 12 }, // 8*3
				});
	}

	@Before
	public void setupAlgorithm() {
		//alg = new FastMajorityVoteAlgorithm();
		alg = new TreeMapHashAlgorithm();
	}

	public MostCommonIntegerSumAlgTest(int[] input, int output) {
		super();
		this.output = output;
		this.input = input;
	}

	@Test
	public void testApp() {
		// given
		int[] input = this.input;
		// when
		int actual = alg.extractCommonSum(input);
		// then
		assertThat(actual, is(this.output));
	}
}
