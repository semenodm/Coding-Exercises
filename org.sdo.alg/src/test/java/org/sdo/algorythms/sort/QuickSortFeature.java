package org.sdo.algorythms.sort;

import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.sdo.algorythms.sort.QuickSort.PivotRule;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

public class QuickSortFeature extends BasicCucumberSortTestImpl {
	Integer[] inputArray;
	private QuickSort quickSort = new QuickSort();
	private List<Integer> actualResult;

	@Given("^The input array to quick sort ([\\d\\s\\-\\,]*)$")
	public void theInputArray(String input) {
		inputArray = convertStringToArray(input);
	}

	@When("^sort array with (\\w*) pivot$")
	public void whenRunSortAlgo(PivotRule rule) {
		actualResult = Arrays.asList(inputArray);
		quickSort.sort(actualResult, rule);
	}

	@Then("^The quick sorted array is ([\\d\\s\\-\\,]*).$")
	public void inputShouldBeSorted(String expected) {
		Integer[] expectedResult = convertStringToArray(expected);
		Assert.assertThat(inputArray, is(expectedResult));
	}
}
