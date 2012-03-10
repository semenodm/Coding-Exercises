import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.sdo.algorythms.sort.MergeSort;

import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.I18n.EN.When;

public class MergesortFeature {
	private MergeSort alg;
	private List<Integer> result;
	private List<Integer> arrayInput;

	@Given("^The input array to sort ([\\d\\s\\-\\,]*)$")
	public void theInputArray(String input) {
		arrayInput = convertFromString(input);
		alg = new MergeSort();
	}

	@When("^sort array$")
	public void sortArray() {
		result = alg.sort(arrayInput);
	}

	@Then("^The sorted array is ([\\d\\s\\-\\,]*).$")
	public void theSummIs(String expectedResult) {
		Assert.assertThat(result, is(convertFromString(expectedResult)));
	}

	private List<Integer> convertFromString(String input) {
		String[] split = input.split(",");
		List<Integer> array = new ArrayList<Integer>(split.length);		
		for (int i = 0; i < split.length; i++) {
			array.add(Integer.valueOf(split[i].trim()));
		}
		return array;
	}
}
