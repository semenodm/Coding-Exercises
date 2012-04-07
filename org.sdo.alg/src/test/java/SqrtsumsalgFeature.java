import static org.hamcrest.Matchers.is;

import org.junit.Assert;
import org.sdo.algorythms.sqrtsecomposition.Algorithm;
import org.sdo.algorythms.sqrtsecomposition.SqrtDecompositionSum;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;


public class SqrtsumsalgFeature {	
	private Algorithm alg;
	private int result;

	@Given ("^The input array ([\\d\\s\\-\\,]*)$")	
	public void theInputArray(String input) {
		String[] split = input.split(",");
		int[] arrayInput = new int[split.length]; 
		for (int i = 0; i < arrayInput.length; i++) {
			arrayInput[i] = Integer.valueOf(split[i].trim());
		}
		alg = new SqrtDecompositionSum(arrayInput);
	}
	
	@When ("^The calc sum between ([\\d]*), ([\\d]*)$")	
	public void theCalcSumBetween(int L, int R) {
		result = alg.calcSummBetween(L, R);
		
	}
	
	@Then ("^The summ is ([\\d]*).$")	
	public void theSummIs(int expectedResult) {
		Assert.assertThat(result, is(expectedResult));
	}
}
