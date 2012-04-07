package org.sdo.algorythms.sort;

import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.Assert;

import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class SortCucumberTestImpl {
	private Integer[] inputArray;
	private MergeSort alg = new MergeSort();
	private List<Object> actualResult;

	@Given("^The input array to sort ([\\d\\s\\-\\,]*)$")
	public void theInputArray(String input) {
		inputArray = convertStringToArray(input);
	}

	@When("^sort array$")
	public void whenRunSortAlgo() {
		actualResult = alg.sort(Arrays.asList(inputArray));
	}

		
	@Then("^The sorted array is ([\\d\\s\\-\\,]*)$")
	public void inputShouldBeSorted(String expected) {
		Integer[] expectedResult = convertStringToArray(expected);
		Assert.assertThat(((List<Integer>)actualResult.get(1)).toArray(new Integer[expectedResult.length]), is(expectedResult));
	}

	@And("^The number of inversions is (\\d*).$")
	public void numberOfInversionsCheck(String num) {
		Long expectedNumberOfInversions = Long.valueOf(num.trim());

	}

	private Integer[] convertStringToArray(String input) {
		String[] split = input.split(",");
		Integer[] out = new Integer[split.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = Integer.valueOf(split[i].trim());
		}
		return out;
	}
}
